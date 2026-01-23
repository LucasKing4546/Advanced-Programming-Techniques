package repository;

import domain.Flight;
import domain.Ticket;
import domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private String URL;
    private Connection conn;

    public Repository(String URL) {
        this.URL = URL;
    }

    private void openConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public List<User> getUsers(){
        openConnection();
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Email"));
                users.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return users;
    }

    public List<Ticket> getTickets(){
        openConnection();
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Tickets";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket(rs.getInt("UserID"),
                        rs.getInt("FlightID"),
                        rs.getInt("SeatNumber"),
                        rs.getString("PurchaseTime"));
                tickets.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return tickets;
    }

    public List<Flight> getFlights(){
        openConnection();
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM Flights";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Flight f = new Flight(rs.getInt("ID"),
                        rs.getString("Destination"),
                        rs.getString("DepartureTime"),
                        rs.getInt("AvailableSeats"),
                        rs.getInt("TicketPrice"));
                flights.add(f);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return flights;
    }

    public List<Ticket> getUserTicket(int user_id){
        openConnection();
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Tickets WHERE UserID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket(rs.getInt("UserID"),
                        rs.getInt("FlightID"),
                        rs.getInt("SeatNumber"),
                        rs.getString("PurchaseTime"));
                tickets.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return tickets;
    }

    public void buyTicket(int userID, int flightID, int seatNumber){
        openConnection();
        String sql = "INSERT INTO Tickets(UserID, FlightID, SeatNumber, PurchaseTime) values (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, flightID);
            stmt.setInt(3, seatNumber);
            stmt.setString(4, String.valueOf(java.time.LocalDateTime.now()));
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }

        sql = "UPDATE Flights SET AvailableSeats = AvailableSeats - 1 WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, flightID);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
    }

    public void cancelTicket(int userID, int flightID){
        openConnection();
        String sql = "DELETE FROM Tickets WHERE UserID = ? AND FlightID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, flightID);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }

        sql = "UPDATE Flights SET AvailableSeats = AvailableSeats + 1 WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, flightID);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
    }

}
