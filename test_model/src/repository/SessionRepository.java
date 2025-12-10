package repository;

import domain.Session;

import java.sql.*;
import java.util.ArrayList;

public class SessionRepository {
    private ArrayList<Session> sessions;
    private String URL;
    private Connection conn = null;

    public SessionRepository(String URL){
        this.URL = URL;
        this.sessions = this.getAllFromDB();
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

    public ArrayList<Session> getAllFromDB() {
        openConnection();
        try{
            ArrayList<Session> elems = new ArrayList<>();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Sessions");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Session session = new Session( rs.getInt("Start_time"), rs.getInt("End_time"), rs.getString("Name"), rs.getInt("Intensity"), rs.getString("Description"));
                elems.add(session);
            }
            return elems;
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    public ArrayList<Session> getAll(){
        return this.sessions;
    }

}
