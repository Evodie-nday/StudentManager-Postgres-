import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements GenericDAO<Student> {
    private final String url = "jdbc:postgresql://localhost:5432/StudentManagement";
    private final String user = "postgres";
    private final String password = "munyakazi1";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void add(Student student) {
        String sql = "insert into students(first_name, last_name, email, date_of_birth) VALUES(?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getEmail());
            stmt.setDate(4, new java.sql.Date(student.getDateOfBirth().getTime()));
//            stmt.setDate(4, student.getDateOfBirth());

            stmt.executeUpdate();
            System.out.println("Student added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                Date dateOfBirth = rs.getDate("date_of_birth");

                Student student = new Student(id, firstName, lastName, email, dateOfBirth);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Student getById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                Date dateOfBirth = rs.getDate("date_of_birth");

                student = new Student(id, firstName, lastName, email, dateOfBirth);
            } else {
                System.out.println("Student with ID" + id + "not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }
    @Override
    public void update(Student student){
        String sql = "UPDATE students SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
        try(Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getEmail());
            stmt.setDate(4, new java.sql.Date(student.getDateOfBirth().getTime()));
            stmt.setInt(5, student.getId());

            int rowsUpdated = stmt.executeUpdate();
            if(rowsUpdated > 0){
                System.out.println("Student updated successfully");
            } else {
                System.out.println("Student with ID");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void delete(Student student){
        String sql = "DELETE FROM students WHERE id = ?";
        try(Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, student.getId());

            int rowsDeleted = stmt.executeUpdate();
            if(rowsDeleted > 0){
                System.out.println("Student deleted successfully.");

            }else{
                System.out.println("Student with ID " + student.getId() + "not found.");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}

