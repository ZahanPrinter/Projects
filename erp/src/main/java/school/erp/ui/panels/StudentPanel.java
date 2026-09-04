package school.erp.ui.panels;

import school.erp.dao.StudentDAO;
import school.erp.models.Student;
import school.erp.ui.components.*;
import school.erp.utils.UIConstants;
import school.erp.utils.ValidationUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class StudentPanel extends JPanel {
    private StudentDAO studentDAO;
    private DefaultTableModel tableModel;
    private DarkTable table;
    
    public StudentPanel() {
        studentDAO = new StudentDAO();
        setLayout(new BorderLayout(20, 20));
        setBackground(UIConstants.SECONDARY);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.SECONDARY);
        
        JLabel header = new JLabel("Students");
        header.setFont(UIConstants.HEADER_FONT);
        header.setForeground(UIConstants.TEXT_DARK);
        
        DarkButton addBtn = new DarkButton("Add New", UIConstants.SUCCESS);
        addBtn.addActionListener(e -> showAddDialog());
        
        headerPanel.add(header, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Name", "Roll No", "Class", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new DarkTable(tableModel);
        DarkScrollPane scrollPane = new DarkScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(UIConstants.SECONDARY);
        
        DarkButton refreshBtn = new DarkButton("Refresh", UIConstants.ACCENT);
        refreshBtn.addActionListener(e -> loadData());
        
        DarkButton deleteBtn = new DarkButton("Delete", UIConstants.DANGER);
        deleteBtn.addActionListener(e -> deleteSelected());
        
        btnPanel.add(refreshBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);
        
        loadData();
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<Student> students = studentDAO.getAllStudents();
            for (Student s : students) {
                tableModel.addRow(new Object[]{
                    s.getId(), s.getName(), s.getRollNo(), 
                    s.getClassName(), s.getEmail(), s.getPhone()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void showAddDialog() {
    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                 "Add New Student", true);
    dialog.setSize(450, 500);
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(new BorderLayout(15, 15));
    dialog.getContentPane().setBackground(UIConstants.SECONDARY);
    
    JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
    formPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
    formPanel.setBackground(UIConstants.SECONDARY);
    
    DarkTextField nameField = new DarkTextField();
    DarkTextField rollNoField = new DarkTextField();
    DarkTextField classField = new DarkTextField();
    DarkTextField emailField = new DarkTextField();
    DarkTextField phoneField = new DarkTextField();
    DarkTextField addressField = new DarkTextField();
    
    formPanel.add(createLabel("Name:"));
    formPanel.add(nameField);
    formPanel.add(createLabel("Roll No:"));
    formPanel.add(rollNoField);
    formPanel.add(createLabel("Class:"));
    formPanel.add(classField);
    formPanel.add(createLabel("Email:"));
    formPanel.add(emailField);
    formPanel.add(createLabel("Phone (10 digits):"));
    formPanel.add(phoneField);
    formPanel.add(createLabel("Address:"));
    formPanel.add(addressField);
    
    DarkButton saveBtn = new DarkButton("Save", UIConstants.SUCCESS);
    saveBtn.addActionListener(e -> {
        // Validate inputs
        if (!ValidationUtils.validateStudentForm(
                nameField.getText(),
                rollNoField.getText(),
                classField.getText(),
                emailField.getText(),
                phoneField.getText(),
                dialog)) {
            return; // Validation failed
        }
        
        try {
            Student student = new Student();
            student.setName(nameField.getText().trim());
            student.setRollNo(rollNoField.getText().trim());
            student.setClassName(classField.getText().trim());
            student.setEmail(emailField.getText().trim());
            student.setPhone(phoneField.getText().trim());
            student.setAddress(addressField.getText().trim());
            
            studentDAO.addStudent(student);
            JOptionPane.showMessageDialog(dialog, "Student added successfully!");
            loadData();
            dialog.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(dialog, "Database Error: " + ex.getMessage());
        }
    });
    
    JPanel btnPanel = new JPanel();
    btnPanel.setBackground(UIConstants.SECONDARY);
    btnPanel.add(saveBtn);
    
    dialog.add(formPanel, BorderLayout.CENTER);
    dialog.add(btnPanel, BorderLayout.SOUTH);
    dialog.setVisible(true);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UIConstants.TEXT_DARK);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }
    
    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this student?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    studentDAO.deleteStudent(id);
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                    loadData();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete");
        }
    }
}