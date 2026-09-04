package school.erp.ui.panels;

import school.erp.dao.TeacherDAO;
import school.erp.models.Teacher;
import school.erp.ui.components.*;
import school.erp.utils.UIConstants;
import school.erp.utils.ValidationUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TeacherPanel extends JPanel {
    private TeacherDAO teacherDAO;
    private DefaultTableModel tableModel;
    private DarkTable table;
    
    public TeacherPanel() {
        teacherDAO = new TeacherDAO();
        setLayout(new BorderLayout(20, 20));
        setBackground(UIConstants.SECONDARY);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.SECONDARY);
        
        JLabel header = new JLabel("Teachers");
        header.setFont(UIConstants.HEADER_FONT);
        header.setForeground(UIConstants.TEXT_DARK);
        
        DarkButton addBtn = new DarkButton("Add New", UIConstants.SUCCESS);
        addBtn.addActionListener(e -> showAddDialog());
        
        headerPanel.add(header, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        
        String[] columns = {"ID", "Name", "Employee ID", "Subject", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new DarkTable(tableModel);
        DarkScrollPane scrollPane = new DarkScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
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
            List<Teacher> teachers = teacherDAO.getAllTeachers();
            for (Teacher t : teachers) {
                tableModel.addRow(new Object[]{
                    t.getId(), t.getName(), t.getEmployeeId(),
                    t.getSubject(), t.getEmail(), t.getPhone()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void showAddDialog() {
    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                                 "Add New Teacher", true);
    dialog.setSize(450, 500);
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(new BorderLayout(15, 15));
    dialog.getContentPane().setBackground(UIConstants.SECONDARY);
    
    JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
    formPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
    formPanel.setBackground(UIConstants.SECONDARY);
    
    DarkTextField nameField = new DarkTextField();
    DarkTextField empIdField = new DarkTextField();
    DarkTextField subjectField = new DarkTextField();
    DarkTextField emailField = new DarkTextField();
    DarkTextField phoneField = new DarkTextField();
    DarkTextField qualField = new DarkTextField();
    
    formPanel.add(createLabel("Name:"));
    formPanel.add(nameField);
    formPanel.add(createLabel("Employee ID:"));
    formPanel.add(empIdField);
    formPanel.add(createLabel("Subject:"));
    formPanel.add(subjectField);
    formPanel.add(createLabel("Email:"));
    formPanel.add(emailField);
    formPanel.add(createLabel("Phone (10 digits):"));
    formPanel.add(phoneField);
    formPanel.add(createLabel("Qualification:"));
    formPanel.add(qualField);
    
    DarkButton saveBtn = new DarkButton("Save", UIConstants.SUCCESS);
    saveBtn.addActionListener(e -> {
        // Validate inputs
        if (!ValidationUtils.validateTeacherForm(
                nameField.getText(),
                empIdField.getText(),
                subjectField.getText(),
                emailField.getText(),
                phoneField.getText(),
                dialog)) {
            return; // Validation failed
        }
        
        try {
            Teacher teacher = new Teacher();
            teacher.setName(nameField.getText().trim());
            teacher.setEmployeeId(empIdField.getText().trim());
            teacher.setSubject(subjectField.getText().trim());
            teacher.setEmail(emailField.getText().trim());
            teacher.setPhone(phoneField.getText().trim());
            teacher.setQualification(qualField.getText().trim());
            
            teacherDAO.addTeacher(teacher);
            JOptionPane.showMessageDialog(dialog, "Teacher added successfully!");
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
                "Are you sure you want to delete this teacher?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    teacherDAO.deleteTeacher(id);
                    JOptionPane.showMessageDialog(this, "Teacher deleted successfully!");
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