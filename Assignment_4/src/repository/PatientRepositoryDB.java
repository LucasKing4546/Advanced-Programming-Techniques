package repository;

import domain.Patient;
import validation.RepositoryException;

import java.sql.*;
import java.util.ArrayList;

public class PatientRepositoryDB implements IRepository<Integer, Patient> {
    private String URL;
    private Connection conn = null;

    public PatientRepositoryDB(String URL){
        this.URL = URL;
    }

    private void openConnection(){
        try{
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
            }
        }catch(SQLException e){
            throw new RepositoryException(e.getMessage());
        }
    }

    private void closeConnection(){
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
                throw new RepositoryException(e.getMessage());
            }
        }
    }
    @Override
    public void addElement(Integer integer, Patient element) {
        openConnection();
        try{
            PreparedStatement st =conn.prepareStatement("INSERT INTO Patients VALUES(?,?,?,?,?)");
            st.setInt(1, integer);
            st.setString(2, element.getName());
            st.setString(3, element.getEmail());
            st.setString(4, element.getPhone());
            st.setInt(5, element.getAge());
            st.executeUpdate();
        }catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public void removeElement(Integer integer) {
        openConnection();
        try{
            PreparedStatement st =conn.prepareStatement("DELETE FROM Patients WHERE PatientID=?");
            st.setInt(1, integer);
            st.executeUpdate();
        }catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public void updateElement(Integer old_id, Patient newElement) {
        openConnection();
        try{
            PreparedStatement st =conn.prepareStatement("UPDATE Patients SET Name=?, Email=?, Phone=?, Age=? WHERE PatientID=?");
            st.setString(1, newElement.getName());
            st.setString(2, newElement.getEmail());
            st.setString(3, newElement.getPhone());
            st.setInt(4, newElement.getAge());
            st.setInt(5, old_id);
            st.executeUpdate();
        }catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public Patient findById(Integer integer) {
        openConnection();
        try{
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Patients WHERE PatientID=?");
            st.setInt(1, integer);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return new Patient(rs.getInt("PatientID"), rs.getString("Name"), rs.getString("Email"), rs.getString("Phone"), rs.getInt("Age"));
            }
            else{
                return null;
            }
        }catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public Iterable<Patient> getAll() {
        openConnection();
        try{
            ArrayList<Patient> patients = new ArrayList<>();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Patients");
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                Patient patient = new Patient(rs.getInt("PatientID"), rs.getString("Name"), rs.getString("Email"), rs.getString("Phone"), rs.getInt("Age"));
                patients.add(patient);
            }
            return patients;
        }catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }
}
