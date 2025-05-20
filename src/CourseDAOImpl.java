import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements GenericDAO<Course>{
    private final String url = "jdbc:postgresql://localhost:5432/StudentManagement";
    private final String user = "postgres";
    private final String password = "munyakaz1";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void add(Course course){
        String sql = "INSERT INTO courses(course_name, course_description) VALUE (?, ?)";
        try(Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getCourseDescription());
            stmt.executeUpdate();

            System.out.println("Course added successfully!");
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public List<Course> getAll(){
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try(Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("course_name");
                String desc = rs.getString("course_description");
                courses.add(new Course(id, name, desc));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return courses;
    }
    @Override
    public Course getById(int id){
        String sql = "SELECT * FROM courses WHERE id = ?";
        Course course = null;

        try(Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                String name = rs.getString("course_name");
                String desc = rs.getString("course_description");
                course = new Course(id, name, desc);
            } else{
                System.out.println("Course not found.");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return course;
    }
    @Override
    public void update(Course course){
        String sql = "UPDATE course SET course_name = ?, course_description = ? WHERE id = ?";

        try(Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getCourseDescription());
            stmt.setInt(3, course.getId());

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Course updated successfully");
            } else{
                System.out.println("Course not found");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void delete(Course course){
        String sql = "DELETE FROM courses WHERE id = ?";


    }
}
