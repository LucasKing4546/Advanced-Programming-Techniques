package repository;

import domain.Courier;
import domain.Package;

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

    public List<Package> getPackages(){
        openConnection();
        List<Package> packages = new ArrayList<>();
        String sql = "SELECT * FROM Packages";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Package p = new Package(rs.getString("Recipient"),
                        rs.getString("Address"),
                        rs.getInt("Location_X"),
                        rs.getInt("Location_Y"),
                        rs.getBoolean("Status"));
                packages.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return packages;
    }

    public void deliverPackage(String recipient, String address){
        openConnection();
        String sql = "UPDATE Packages SET Status = ? WHERE Recipient = ? AND Address = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, true);
            stmt.setString(2, recipient);
            stmt.setString(3, address);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
    }

    public void addPackage(Package p){
        openConnection();
        String sql = "INSERT INTO Packages (Recipient, Address, Location_X, Location_Y, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getRecipient());
            stmt.setString(2, p.getAddress());
            stmt.setInt(3, p.getLocationX());
            stmt.setInt(4, p.getLocationY());
            stmt.setBoolean(5, p.isStatus());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
    }

    public Courier getCourier(String name){
        openConnection();
        String sql = "SELECT * FROM Couriers WHERE Name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Courier c = new Courier(rs.getString("Name"),
                        rs.getString("Streets"),
                        rs.getInt("Zone_X"),
                        rs.getInt("Zone_Y"),
                        rs.getInt("Radius")
                );
                closeConnection();
                return c;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return null;
    }
}