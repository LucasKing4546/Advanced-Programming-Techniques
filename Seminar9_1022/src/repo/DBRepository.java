package repo;

import domain.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DBRepository implements IRepository<Doctor> {
    private String URL;
    private Connection conn = null;

    public DBRepository(String URL) {
        this.URL = URL;
    }

    private void openConnection() {
        try {
            if (conn == null || conn.isClosed())
                conn = DriverManager.getConnection(this.URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void add(Doctor elem) throws RepositoryException {
        openConnection();
        try{
            PreparedStatement st = conn.prepareStatement("INSERT into doctors VALUES (?,?,?,?,?)");
            st.setInt(1,elem.getId());
            st.setString(2,elem.getName());
            st.setString(3,elem.getSpecialty());
            st.setString(4,elem.getLocation());
            st.setDouble(5,elem.getGrade());
            st.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        finally
        {
            closeConnection();
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Iterator<Doctor> iterator() {
        openConnection();
        try{
            PreparedStatement st = conn.prepareStatement("SELECT * from doctors");
            ResultSet rs = st.executeQuery();
            ArrayList<Doctor> list = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialty = rs.getString("specialty");
                String location = rs.getString("location");
                Float grade = rs.getFloat("grade");
                Doctor doctor = new Doctor(id,name,specialty,location,grade);
                list.add(doctor);
            }
            closeConnection();
            return list.iterator();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            closeConnection();
        }
    }
}
