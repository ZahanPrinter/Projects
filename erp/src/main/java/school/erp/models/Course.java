package school.erp.models;

public class Course {
    private int id;
    private String courseName;
    private String courseCode;
    private int teacherId;
    private int credits;
    
    public Course() {}
    
    public Course(int id, String courseName, String courseCode, int teacherId, int credits) {
        this.id = id;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.teacherId = teacherId;
        this.credits = credits;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    
    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
    
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
}