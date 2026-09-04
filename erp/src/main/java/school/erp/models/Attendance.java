package school.erp.models;

import java.sql.Date;

public class Attendance {
    private int id;
    private int studentId;
    private String studentName;
    private Date date;
    private String status;
    
    public Attendance() {}
    
    public Attendance(int id, int studentId, String studentName, Date date, String status) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.date = date;
        this.status = status;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}