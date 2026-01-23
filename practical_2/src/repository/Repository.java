package repository;

import domain.Booking;
import domain.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private String URL;
    private Connection conn;

    public Repository(String URL){
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

    public void addBooking(int client, int room, String start, String end){
        openConnection();
        String sql = "INSERT INTO Bookings (ClientId, RoomNumber, StartDate, EndDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, client);
            stmt.setInt(2, room);
            stmt.setString(3, start);
            stmt.setString(4, end);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
    }

    public List<Room> getRooms(){
        openConnection();
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Rooms";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room(rs.getInt("Number"),
                        rs.getString("Type"),
                        rs.getInt("PricePerNight"),
                        rs.getString("Description"));
                rooms.add(room);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return rooms;
    }

    public List<Booking> getBookings(Room room){
        openConnection();
        String sql = "";
        List<Booking> bookings = new ArrayList<>();
        if (room != null) {
            sql = "SELECT * FROM Bookings WHERE RoomNumber = ? ORDER BY StartDate";
        }
        else{
            sql = "SELECT * FROM Bookings";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (room != null) {
                stmt.setInt(1, room.getNumber());
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking(rs.getInt("ClientId"),
                        rs.getInt("RoomNumber"),
                        rs.getString("StartDate"),
                        rs.getString("EndDate"));
                bookings.add(booking);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return bookings;
    }

    public boolean isRoomAvailable(int roomNumber, String newStart, String newEnd) {
        openConnection();
        String sql = "SELECT COUNT(*) FROM Bookings " +
                "WHERE RoomNumber = ? " +
                "AND StartDate < ? " +   // Overlap Logic Part 1
                "AND EndDate > ?";       // Overlap Logic Part 2

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomNumber);
            stmt.setString(2, newEnd);    // Note: Compare NewEnd vs ExistingStart
            stmt.setString(3, newStart);  // Note: Compare NewStart vs ExistingEnd

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0; // If count is 0, no overlaps, room is free
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

}
