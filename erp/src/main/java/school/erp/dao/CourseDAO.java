package school.erp.dao;

import school.erp.models.Course;
import school.erp.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private Connection conn;
    
    public CourseDAO() {
        conn = DatabaseConnection.getConnection();
    }
    
    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO courses (course_name, course_code, teacher_id, credits) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, course.getCourseName());
        pstmt.setString(2, course.getCourseCode());
        pstmt.setInt(3, course.getTeacherId());
        pstmt.setInt(4, course.getCredits());
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM courses");
        
        while (rs.next()) {
            Course c = new Course();
            c.setId(rs.getInt("id"));
            c.setCourseName(rs.getString("course_name"));
            c.setCourseCode(rs.getString("course_code"));
            c.setTeacherId(rs.getInt("teacher_id"));
            c.setCredits(rs.getInt("credits"));
            courses.add(c);
        }
        
        rs.close();
        stmt.close();
        return courses;
    }
    
    public void deleteCourse(int id) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM courses WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public int getCourseCount() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM courses");
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        stmt.close();
        return count;
    }
}