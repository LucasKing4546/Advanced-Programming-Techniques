package repository;

import domain.Entity;

import javax.lang.model.element.Element;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class Repository {
    private ArrayList<Entity> elements;
    private String URL;
    private Connection conn = null;

    public Repository(String URL){
        this.URL = URL;
        elements = this.getAll();
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
    public void addElement(Entity element) {
        openConnection();
        try{
            PreparedStatement st =conn.prepareStatement("INSERT INTO Sessions VALUES(?,?,?,?,?)");
            st.setInt(1, element.getStart_time());
            st.setInt(2, element.getEnd_time());
            st.setString(3, element.getName());
            st.setInt(4, element.getIntensity());
            st.setString(5, element.getDescription());
            st.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    public void removeElement(String name) {
        openConnection();
        try{
            PreparedStatement st =conn.prepareStatement("DELETE FROM Sessions WHERE Name=?");
            st.setString(1, name);
            st.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    public void updateElement(String name, Entity newElement) {
        openConnection();
        try{
            PreparedStatement st =conn.prepareStatement("UPDATE Sessions SET Description=?  WHERE Name=?");
            st.setString(1, newElement.getDescription());
            st.setString(2, name);
            st.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    public ArrayList<Entity> getAll() {
        openConnection();
        try{
            ArrayList<Entity> entities = new ArrayList<>();
            PreparedStatement st = conn.prepareStatement("SELECT Start_time, End_time, Name, Intensity, Description FROM Sessions");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Entity appointment = new Entity(rs.getInt("Start_time"), rs.getInt("End_time"), rs.getString("Name"), rs.getInt("Intensity"), rs.getString("Description"));
                entities.add(appointment);
            }
            return entities;
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }
}
