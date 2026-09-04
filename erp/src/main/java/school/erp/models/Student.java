package school.erp.models;
import java.sql.Date;

public class Student {
    private int id;
    private String name;
    private String rollNo;
    private String className;
    private String email;
    private String phone;
    private String address;
    private Date admissionDate;
    
    public Student() {}
    
    public Student(int id, String name, String rollNo, String className, 
                   String email, String phone, String address, Date admissionDate) {
        this.id = id;
        this.name = name;
        this.rollNo = rollNo;
        this.className = className;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.admissionDate = admissionDate;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
    
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public Date getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(Date admissionDate) { this.admissionDate = admissionDate; }
}