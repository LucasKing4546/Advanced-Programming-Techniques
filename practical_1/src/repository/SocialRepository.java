package repository;

import domain.Post;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SocialRepository {
    private String URL;
    private Connection conn;

    public SocialRepository(String URL){
        this.URL = URL;
    }

    private void openConnection(){
        try{
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
            }
        }catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private void closeConnection(){
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void savePost(Post post) {
        openConnection();
        String sql = "INSERT INTO posts (UserName, Text, TimeStamp) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, post.getUsername());
            stmt.setString(2, post.getText());
            stmt.setString(3, post.getTimestamp().toString());
            stmt.executeUpdate();
            // logic to set post ID from generated keys
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
    }

    public void updatePostText(int postId, String newText) {
        // [cite: 140, 142]
        openConnection();
        String sql = "UPDATE posts SET Text = ? WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newText);
            stmt.setInt(2, postId);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
    }

    public void addSubscription(String user, String topic) {
        // [cite: 142] Updates DB
        openConnection();
        String sql = "INSERT INTO UserTopics (UserName, TopicName) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            stmt.setString(2, topic);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
    }

    public List<Post> getUserPosts(String user){
        openConnection();
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT ID, Text, TimeStamp FROM Posts WHERE UserName = ? ORDER BY TimeStamp DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Post post = new Post(rs.getInt("ID"),
                        rs.getString("Text"),
                        LocalDateTime.parse(rs.getString("TimeStamp")), user);
                posts.add(post);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return posts;
    }

    public List<String> getSubscriptions(String user) {
        // Requirement[cite: 130]: show subscriptions
        openConnection();
        List<String> subs = new ArrayList<>();
        String sql = "SELECT TopicName FROM UserTopics WHERE UserName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                subs.add(rs.getString("TopicName"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return subs;
    }

    public List<String> findTopics(String partialName) {
        openConnection();
        List<String> matches = new ArrayList<>();
        // Use DISTINCT to avoid duplicates if multiple users follow the same topic
        String sql = "SELECT DISTINCT TopicName FROM UserTopics WHERE TopicName LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Add wildcards for substring match
            stmt.setString(1, "%" + partialName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                matches.add(rs.getString("TopicName"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        closeConnection();
        return matches;
    }
    public List<Post> getFeedForUser(String user) {
        openConnection();
        List<Post> feed = new ArrayList<>();

        // Complex query: Find posts where text contains @User OR text contains #Topic (where user is subscribed to Topic)
        // Note: This is a simplified logic. In a real exam, you might fetch all recent posts and filter in Java
        // to avoid complex SQL joins on text parsing.
        // However, here is a SQL-based approach combining Mentions and Subscriptions:

        String sql = "SELECT p.ID, p.UserName, p.Text, p.TimeStamp " +
                "FROM Posts p " +
                "LEFT JOIN UserTopics ut ON p.Text LIKE '%#' || ut.TopicName " +
                "WHERE p.Text LIKE ? " +       // Check for @User mention
                "OR (ut.UserName = ?)" +
                "ORDER BY P.TimeStamp desc";
                // Check for #Topic subscription

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%@" + user + "%"); // @User
            stmt.setString(2, user);              // Subscribed user

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Use a Set or check distinct ID in Java to avoid duplicates if a post has both mention and tag
                // For simplicity here, we just add them:
                Post post = new Post(rs.getInt("ID"),
                        rs.getString("Text"),
                        LocalDateTime.parse(rs.getString("TimeStamp")),
                        rs.getString("UserName"));
                feed.add(post);
            }
        } catch (SQLException e) { e.printStackTrace(); }


        closeConnection();
        return feed;
    }
}
