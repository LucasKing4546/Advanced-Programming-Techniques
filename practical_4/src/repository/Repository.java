package repository;

import domain.Car;
import domain.Client;
import domain.Rental;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<Car> getCars(){
        openConnection();
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM Cars";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Car c = new Car(rs.getInt("ID"),
                        rs.getString("Model"),
                        rs.getString("Category"),
                        rs.getInt("Price"),
                        rs.getBoolean("Status"));
                cars.add(c);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return cars;
    }

    public List<String> getCategories(){
        openConnection();
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT Category FROM Cars";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("Category"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return categories;
    }

    public List<Rental> getRentals(){
        openConnection();
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM Rentals";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Rental r = new Rental(rs.getInt("ClientId"),
                        rs.getInt("CarId"),
                        rs.getString("StartDate"),
                        rs.getString("EndDate"),
                        rs.getInt("TotalCost"));
                rentals.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return rentals;
    }

    public void rentCar(int client, int car, String endDate, int totalCost){
        openConnection();
        String sql = "UPDATE Cars SET Status= ? WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, false);
            stmt.setInt(2, car);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }

        sql = "INSERT into Rentals(ClientId, CarId, StartDate, EndDate, TotalCost) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, client);
            stmt.setInt(2, car);
            stmt.setString(3, String.valueOf(LocalDate.now()));
            stmt.setString(4, endDate);
            stmt.setInt(5, totalCost);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
    }

    public void returnCar(int client, int car){
        openConnection();
        String sql = "UPDATE Cars SET Status= ? WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, true);
            stmt.setInt(2, car);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }

        sql = "DELETE FROM Rentals where ClientId = ? and CarId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, client);
            stmt.setInt(2, car);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
    }

    public Client getClient(int id){
        openConnection();
        String sql = "SELECT * FROM Clients where ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client c = new Client(rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Phone"));
                closeConnection();
                return c;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return null;
    }
}
