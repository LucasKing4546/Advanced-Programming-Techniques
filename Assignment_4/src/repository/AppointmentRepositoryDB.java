package repository;

import domain.Appointment;
import validation.RepositoryException;

import java.sql.*;
import java.util.ArrayList;

public class AppointmentRepositoryDB implements IRepository<Integer, Appointment> {
    private String URL;
    private Connection conn = null;

    public AppointmentRepositoryDB(String URL){
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
    public void addElement(Integer integer, Appointment element) {
        openConnection();
        try{
            PreparedStatement st =conn.prepareStatement("INSERT INTO Appointments VALUES(?,?,?,?)");
            st.setInt(1, integer);
            st.setInt(2, element.getPatientId());
            st.setDate(3, Date.valueOf(element.getDate()));
            st.setTime(4, Time.valueOf(element.getTime()));
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
            PreparedStatement st =conn.prepareStatement("DELETE FROM Appointments WHERE AppointmentID=?");
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
    public void updateElement(Integer old_id, Appointment newElement) {
        openConnection();
        try{
            PreparedStatement st =conn.prepareStatement("UPDATE Appointments SET PatientID=?, AppointmentDate=?, AppointmentTime=? WHERE AppointmentID=?");
            st.setInt(1, newElement.getPatientId());
            st.setDate(2, Date.valueOf(newElement.getDate()));
            st.setTime(3, Time.valueOf(newElement.getTime()));
            st.setInt(4, old_id);
            st.executeUpdate();
        }catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public Appointment findById(Integer integer) {
        openConnection();
        try{
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Appointments WHERE AppointmentID=?");
            st.setInt(1, integer);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return new Appointment(rs.getInt("AppointmentID"), rs.getInt("PatientID"), rs.getDate("AppointmentDate").toString(), rs.getTime("AppointmentTime").toString());
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
    public Iterable<Appointment> getAll() {
        openConnection();
        try{
            ArrayList<Appointment> appointments = new ArrayList<>();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Appointments");
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                Appointment appointment = new Appointment(rs.getInt("AppointmentID"), rs.getInt("PatientID"), rs.getDate("AppointmentDate").toString(), rs.getTime("AppointmentTime").toString());
                appointments.add(appointment);
            }
            return appointments;
        }catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        finally {
            closeConnection();
        }
    }
}
