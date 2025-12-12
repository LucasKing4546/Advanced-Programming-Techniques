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

    public void updateElement(String name, Entity newElement) {
        openConnection();
        try{
            PreparedStatement st =conn.prepareStatement("UPDATE Medication SET SideEffects=?  WHERE Name=?");
            st.setString(1, newElement.getSideEffects());
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
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Medication");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Entity appointment = new Entity(rs.getString("Category"), rs.getString("Name"), rs.getString("SideEffects"));
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
