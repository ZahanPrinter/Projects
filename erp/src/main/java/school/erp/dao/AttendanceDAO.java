package school.erp.dao;

import school.erp.models.Attendance;
import school.erp.database.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    private Connection conn;
    
    public AttendanceDAO() {
        conn = DatabaseConnection.getConnection();
    }
    
    public void markAttendance(Attendance attendance) throws SQLException {
        String sql = "INSERT INTO attendance (student_id, date, status) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, attendance.getStudentId());
        pstmt.setDate(2, attendance.getDate());
        pstmt.setString(3, attendance.getStatus());
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public List<Attendance> getAllAttendance() throws SQLException {
        List<Attendance> records = new ArrayList<>();
        String sql = "SELECT a.id, a.student_id, s.name, a.date, a.status " +
                     "FROM attendance a " +
                     "JOIN students s ON a.student_id = s.id " +
                     "ORDER BY a.date DESC";
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        while (rs.next()) {
            Attendance a = new Attendance();
            a.setId(rs.getInt("id"));
            a.setStudentId(rs.getInt("student_id"));
            a.setStudentName(rs.getString("name"));
            a.setDate(rs.getDate("date"));
            a.setStatus(rs.getString("status"));
            records.add(a);
        }
        
        rs.close();
        stmt.close();
        return records;
    }
    
    public List<Attendance> getTodayAttendance() throws SQLException {
        List<Attendance> records = new ArrayList<>();
        String sql = "SELECT a.id, a.student_id, s.name, a.date, a.status " +
                     "FROM attendance a " +
                     "JOIN students s ON a.student_id = s.id " +
                     "WHERE a.date = ? " +
                     "ORDER BY s.name";
        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDate(1, Date.valueOf(LocalDate.now()));
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Attendance a = new Attendance();
            a.setId(rs.getInt("id"));
            a.setStudentId(rs.getInt("student_id"));
            a.setStudentName(rs.getString("name"));
            a.setDate(rs.getDate("date"));
            a.setStatus(rs.getString("status"));
            records.add(a);
        }
        
        rs.close();
        pstmt.close();
        return records;
    }
    
    public List<Attendance> getAttendanceByStudent(int studentId) throws SQLException {
        List<Attendance> records = new ArrayList<>();
        String sql = "SELECT a.id, a.student_id, s.name, a.date, a.status " +
                     "FROM attendance a " +
                     "JOIN students s ON a.student_id = s.id " +
                     "WHERE a.student_id = ? " +
                     "ORDER BY a.date DESC";
        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, studentId);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Attendance a = new Attendance();
            a.setId(rs.getInt("id"));
            a.setStudentId(rs.getInt("student_id"));
            a.setStudentName(rs.getString("name"));
            a.setDate(rs.getDate("date"));
            a.setStatus(rs.getString("status"));
            records.add(a);
        }
        
        rs.close();
        pstmt.close();
        return records;
    }
    
    public void updateAttendance(Attendance attendance) throws SQLException {
        String sql = "UPDATE attendance SET status=?, date=? WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, attendance.getStatus());
        pstmt.setDate(2, attendance.getDate());
        pstmt.setInt(3, attendance.getId());
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public void deleteAttendance(int id) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM attendance WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public int getTodayAttendanceCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM attendance WHERE date = ? AND status = 'Present'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDate(1, Date.valueOf(LocalDate.now()));
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        pstmt.close();
        return count;
    }
}