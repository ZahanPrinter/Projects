package school.erp.dao;


import school.erp.models.Teacher;
import school.erp.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private Connection conn;
    
    public TeacherDAO() {
        conn = DatabaseConnection.getConnection();
    }
    
    public void addTeacher(Teacher teacher) throws SQLException {
        String sql = "INSERT INTO teachers (name, employee_id, subject, email, phone, qualification) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, teacher.getName());
        pstmt.setString(2, teacher.getEmployeeId());
        pstmt.setString(3, teacher.getSubject());
        pstmt.setString(4, teacher.getEmail());
        pstmt.setString(5, teacher.getPhone());
        pstmt.setString(6, teacher.getQualification());
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public List<Teacher> getAllTeachers() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM teachers");
        
        while (rs.next()) {
            Teacher t = new Teacher();
            t.setId(rs.getInt("id"));
            t.setName(rs.getString("name"));
            t.setEmployeeId(rs.getString("employee_id"));
            t.setSubject(rs.getString("subject"));
            t.setEmail(rs.getString("email"));
            t.setPhone(rs.getString("phone"));
            t.setQualification(rs.getString("qualification"));
            teachers.add(t);
        }
        
        rs.close();
        stmt.close();
        return teachers;
    }
    
    public void updateTeacher(Teacher teacher) throws SQLException {
        String sql = "UPDATE teachers SET name=?, employee_id=?, subject=?, email=?, phone=?, qualification=? WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, teacher.getName());
        pstmt.setString(2, teacher.getEmployeeId());
        pstmt.setString(3, teacher.getSubject());
        pstmt.setString(4, teacher.getEmail());
        pstmt.setString(5, teacher.getPhone());
        pstmt.setString(6, teacher.getQualification());
        pstmt.setInt(7, teacher.getId());
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public void deleteTeacher(int id) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM teachers WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public int getTeacherCount() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM teachers");
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        stmt.close();
        return count;
    }
}