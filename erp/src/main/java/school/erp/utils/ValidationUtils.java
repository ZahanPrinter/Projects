package school.erp.utils;

import javax.swing.*;
import java.util.regex.Pattern;

public class ValidationUtils {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^\\d{10}$");
    
    //Email Validator
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    //Phone Validator
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }
    
    //Not Null checker
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    //Error thing
    public static void showValidationError(java.awt.Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
    
    //Validate student form data
    public static boolean validateStudentForm(String name, String rollNo, String className, 
                                              String email, String phone, java.awt.Component parent) {
        if (!isNotEmpty(name)) {
            showValidationError(parent, "Name cannot be empty!");
            return false;
        }
        
        if (!isNotEmpty(rollNo)) {
            showValidationError(parent, "Roll Number cannot be empty!");
            return false;
        }
        
        if (!isNotEmpty(className)) {
            showValidationError(parent, "Class cannot be empty!");
            return false;
        }
        
        if (!isValidEmail(email)) {
            showValidationError(parent, "Invalid email format! Email must contain @ symbol.\nExample: student@example.com");
            return false;
        }
        
        if (!isValidPhone(phone)) {
            showValidationError(parent, "Invalid phone number! Phone must be exactly 10 digits.\nExample: 9876543210");
            return false;
        }
        
        return true;
    }
    
    //Validate teacher form data
    public static boolean validateTeacherForm(String name, String empId, String subject,
                                             String email, String phone, java.awt.Component parent) {
        if (!isNotEmpty(name)) {
            showValidationError(parent, "Name cannot be empty!");
            return false;
        }
        
        if (!isNotEmpty(empId)) {
            showValidationError(parent, "Employee ID cannot be empty!");
            return false;
        }
        
        if (!isNotEmpty(subject)) {
            showValidationError(parent, "Subject cannot be empty!");
            return false;
        }
        
        if (!isValidEmail(email)) {
            showValidationError(parent, "Invalid email format! Email must contain @ symbol.\nExample: teacher@example.com");
            return false;
        }
        
        if (!isValidPhone(phone)) {
            showValidationError(parent, "Invalid phone number! Phone must be exactly 10 digits.\nExample: 9876543210");
            return false;
        }
        
        return true;
    }
}