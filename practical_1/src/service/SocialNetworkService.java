package service;

import domain.Post;
import repository.SocialRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SocialNetworkService implements SocialSubject {
    private SocialRepository repository;
    private List<SocialObserver> observers = new ArrayList<>();

    public SocialNetworkService(SocialRepository repository) {
        this.repository = repository;
    }

    // --- Core Functionality Used by Controller ---

    public void publishPost(String user, String text) throws Exception {
        if (text.length() < 3) {
            throw new Exception("Post must be at least 3 characters long.");
        }

        // Create post with current time
        // Note: ID 0 is a placeholder; DB assigns the real ID.
        Post post = new Post(0, text, LocalDateTime.now(), user);

        repository.savePost(post);

        notifyObservers(post);
    }

    // Add inside SocialNetworkService class

    public void updatePost(Post post, String newText) {
        // 1. Update the database
        repository.updatePostText(post.getId(), newText);

        // 2. Notify ALL observers to refresh
        // We do not filter by "isRelevant" here because if a tag was removed,
        // we need the observers to refresh to REMOVE the post from their view.
        for (SocialObserver observer : observers) {
            observer.refreshFeed();
        }
    }

    public void subscribe(String user, String topic) {
        repository.addSubscription(user, topic);
    }

    public List<String> searchTopics(String query) {
        return repository.findTopics(query);
    }

    public List<Post> getUserHistory(String user) {
        return repository.getUserPosts(user);
    }

    public List<Post> getFeed(String user) {
        return repository.getFeedForUser(user);
    }

    // Added this because UserController calls service.getSubscriptions(userName)
    public List<String> getSubscriptions(String user) {
        return repository.getSubscriptions(user);
    }

    // --- Observer Pattern Implementation ---

    @Override
    public void attach(SocialObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(SocialObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Post post) {
        // Requirement 4.a & 4.b: Notify all active windows that a change occurred.
        // We iterate through all observers (UserWindows).
        // The Service calculates relevance based on the *post content* vs *user interests*.
        // If relevant, we tell the window to REFRESH its data from the DB.

        for (SocialObserver observer : observers) {
            if (isRelevant(post, observer.getUserName(), observer.getSubscribedTopics())) {
                // Pass the post for potential notifications, but the UI should pull fresh data.
                observer.refreshFeed();
            }
        }
    }

    // --- Helper Logic for Relevance ---
    // Used to decide WHO gets the notification signal.
    private boolean isRelevant(Post post, String userName, List<String> userTopics) {
        String text = post.getText();

        if (text.contains("@" + userName)) {
            return true;
        }

        for (String topic : userTopics) {
            if (text.contains("#" + topic)) {
                return true;
            }
        }

        return false;
    }
}