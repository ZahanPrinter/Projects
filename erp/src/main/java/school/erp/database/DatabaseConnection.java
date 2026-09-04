package school.erp.database;

import javax.swing.JOptionPane;
import java.sql.*;

public class DatabaseConnection {
    //Following details are to be changed according to your mysql setup
    private static Connection conn;
    private static final String URL = "jdbc:mysql://localhost:3306/school_erp";
    private static final String USER = "root";
    private static final String PASSWORD = ""; //Add your password here
    
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null,
                    "Database connection failed!\nMake sure MySQL is running and database 'school_erp' exists.\nError: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        return conn;
    }
    
    public static void initializeDatabase() {
        try {
            Connection connection = getConnection();
            if (connection == null) return;
            
            Statement stmt = connection.createStatement();
            
            // Create students table
            stmt.execute("CREATE TABLE IF NOT EXISTS students (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "roll_no VARCHAR(20) NOT NULL," +
                "class VARCHAR(20)," +
                "email VARCHAR(100)," +
                "phone VARCHAR(15)," +
                "address TEXT," +
                "admission_date DATE DEFAULT (CURRENT_DATE))");
            
            // Create teachers table
            stmt.execute("CREATE TABLE IF NOT EXISTS teachers (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "employee_id VARCHAR(20) UNIQUE NOT NULL," +
                "subject VARCHAR(50)," +
                "email VARCHAR(100)," +
                "phone VARCHAR(15)," +
                "qualification VARCHAR(100)," +
                "join_date DATE DEFAULT (CURRENT_DATE))");
            
            // Create courses table
            stmt.execute("CREATE TABLE IF NOT EXISTS courses (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "course_name VARCHAR(100) NOT NULL," +
                "course_code VARCHAR(20) UNIQUE NOT NULL," +
                "teacher_id INT," +
                "credits INT," +
                "FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE SET NULL)");
            
            // Create attendance table
            stmt.execute("CREATE TABLE IF NOT EXISTS attendance (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "student_id INT," +
                "date DATE," +
                "status ENUM('Present', 'Absent', 'Late')," +
                "FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE)");
                
            stmt.close();
            System.out.println("Database tables initialized successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}