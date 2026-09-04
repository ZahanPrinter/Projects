package school.erp.models;

import java.sql.Date;

public class Teacher {
    private int id;
    private String name;
    private String employeeId;
    private String subject;
    private String email;
    private String phone;
    private String qualification;
    private Date joinDate;
    
    public Teacher() {}
    
    public Teacher(int id, String name, String employeeId, String subject,
                   String email, String phone, String qualification, Date joinDate) {
        this.id = id;
        this.name = name;
        this.employeeId = employeeId;
        this.subject = subject;
        this.email = email;
        this.phone = phone;
        this.qualification = qualification;
        this.joinDate = joinDate;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    
    public Date getJoinDate() { return joinDate; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }
}