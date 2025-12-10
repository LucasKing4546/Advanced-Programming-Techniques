package repository;

import domain.Medication;

import java.sql.*;
import java.util.ArrayList;

public class MedicationRepository {
    private ArrayList<Medication> elements;
    private String URL;
    private Connection conn = null;

    public MedicationRepository(String URL){
        this.URL = URL;
        this.elements = this.getAllFromDB();
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

    public ArrayList<Medication> getAllFromDB() {
        openConnection();
        try{
            ArrayList<Medication> elems = new ArrayList<>();
            PreparedStatement st = conn.prepareStatement("SELECT Category, Name, SideEffects FROM Medication");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Medication medication = new Medication(rs.getString("Category"), rs.getString("Name"), rs.getString("SideEffects"));
                elems.add(medication);
            }
            return elems;
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    /*
    public void updateElement(String name, Medication newElement, String sideEffect) {
        openConnection();
        try{
            PreparedStatement st =conn.prepareStatement("UPDATE Medication SET SideEffects=? WHERE Name=?");
            st.setString(newElement.getSideEffects().concat(sideEffect));
            st.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }
    */
    public ArrayList<Medication> getAll(){
        return this.elements;
    }

}
