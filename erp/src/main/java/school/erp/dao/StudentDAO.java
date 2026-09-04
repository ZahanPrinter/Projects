package school.erp.dao;

import school.erp.models.Student;
import school.erp.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection conn;
    
    public StudentDAO() {
        conn = DatabaseConnection.getConnection();
    }
    
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (name, roll_no, class, email, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, student.getName());
        pstmt.setString(2, student.getRollNo());
        pstmt.setString(3, student.getClassName());
        pstmt.setString(4, student.getEmail());
        pstmt.setString(5, student.getPhone());
        pstmt.setString(6, student.getAddress());
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM students");
        
        while (rs.next()) {
            Student s = new Student();
            s.setId(rs.getInt("id"));
            s.setName(rs.getString("name"));
            s.setRollNo(rs.getString("roll_no"));
            s.setClassName(rs.getString("class"));
            s.setEmail(rs.getString("email"));
            s.setPhone(rs.getString("phone"));
            s.setAddress(rs.getString("address"));
            students.add(s);
        }
        
        rs.close();
        stmt.close();
        return students;
    }
    
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET name=?, roll_no=?, class=?, email=?, phone=?, address=? WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, student.getName());
        pstmt.setString(2, student.getRollNo());
        pstmt.setString(3, student.getClassName());
        pstmt.setString(4, student.getEmail());
        pstmt.setString(5, student.getPhone());
        pstmt.setString(6, student.getAddress());
        pstmt.setInt(7, student.getId());
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public void deleteStudent(int id) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM students WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public int getStudentCount() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM students");
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        stmt.close();
        return count;
    }
}