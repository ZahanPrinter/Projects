package school.erp.ui.panels;

import school.erp.dao.StudentDAO;
import school.erp.dao.AttendanceDAO;
import school.erp.models.Student;
import school.erp.models.Attendance;
import school.erp.ui.components.*;
import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AttendancePanel extends JPanel {
    private AttendanceDAO attendanceDAO;
    private StudentDAO studentDAO;
    private DefaultTableModel tableModel;
    private DarkTable table;
    
    public AttendancePanel() {
        attendanceDAO = new AttendanceDAO();
        studentDAO = new StudentDAO();
        
        setLayout(new BorderLayout(20, 20));
        setBackground(UIConstants.SECONDARY);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.SECONDARY);
        
        JLabel header = new JLabel("Attendance Management");
        header.setFont(UIConstants.HEADER_FONT);
        header.setForeground(UIConstants.TEXT_DARK);
        
        DarkButton markBtn = new DarkButton("Mark Attendance", UIConstants.SUCCESS);
        markBtn.addActionListener(e -> showMarkAttendanceDialog());
        
        headerPanel.add(header, BorderLayout.WEST);
        headerPanel.add(markBtn, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Student ID", "Student Name", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new DarkTable(tableModel);
        DarkScrollPane scrollPane = new DarkScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(UIConstants.SECONDARY);
        
        DarkButton todayBtn = new DarkButton("Today's Records", UIConstants.ACCENT);
        todayBtn.addActionListener(e -> loadTodayAttendance());
        
        DarkButton allBtn = new DarkButton("All Records", UIConstants.ACCENT);
        allBtn.addActionListener(e -> loadAllAttendance());
        
        DarkButton deleteBtn = new DarkButton("Delete", UIConstants.DANGER);
        deleteBtn.addActionListener(e -> deleteSelected());
        
        btnPanel.add(todayBtn);
        btnPanel.add(allBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);
        
        loadTodayAttendance();
    }
    
    private void loadTodayAttendance() {
        tableModel.setRowCount(0);
        try {
            List<Attendance> records = attendanceDAO.getTodayAttendance();
            for (Attendance a : records) {
                tableModel.addRow(new Object[]{
                    a.getId(), a.getStudentId(), a.getStudentName(),
                    a.getDate(), a.getStatus()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void loadAllAttendance() {
        tableModel.setRowCount(0);
        try {
            List<Attendance> records = attendanceDAO.getAllAttendance();
            for (Attendance a : records) {
                tableModel.addRow(new Object[]{
                    a.getId(), a.getStudentId(), a.getStudentName(),
                    a.getDate(), a.getStatus()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void showMarkAttendanceDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                                     "Mark Attendance", true);
        dialog.setSize(600, 550);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(15, 15));
        dialog.getContentPane().setBackground(UIConstants.SECONDARY);
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(UIConstants.SECONDARY);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Calendar Panel
        DarkDatePicker datePicker = new DarkDatePicker();
        datePicker.setPreferredSize(new Dimension(550, 280));
        
        JLabel selectedDateLabel = new JLabel("Selected Date: " + LocalDate.now());
        selectedDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        selectedDateLabel.setForeground(UIConstants.TEXT_DARK);
        selectedDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        datePicker.addDateSelectionListener(date -> {
            selectedDateLabel.setText("Selected Date: " + date);
        });
        
        JPanel calendarPanel = new JPanel(new BorderLayout(10, 10));
        calendarPanel.setBackground(UIConstants.SECONDARY);
        calendarPanel.add(selectedDateLabel, BorderLayout.NORTH);
        calendarPanel.add(datePicker, BorderLayout.CENTER);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        formPanel.setBackground(UIConstants.SECONDARY);
        
        // Load students
        DarkComboBox<String> studentCombo = new DarkComboBox<>();
        try {
            List<Student> students = studentDAO.getAllStudents();
            for (Student s : students) {
                studentCombo.addItem(s.getId() + " - " + s.getName());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(dialog, "Error loading students: " + e.getMessage());
        }
        
        DarkComboBox<String> statusCombo = new DarkComboBox<>(new String[]{"Present", "Absent", "Late"});
        
        formPanel.add(createLabel("Student:"));
        formPanel.add(studentCombo);
        formPanel.add(createLabel("Status:"));
        formPanel.add(statusCombo);
        
        mainPanel.add(calendarPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        DarkButton saveBtn = new DarkButton("Mark Attendance", UIConstants.SUCCESS);
        saveBtn.setPreferredSize(new Dimension(150, 40));
        saveBtn.addActionListener(e -> {
            try {
                String selected = (String) studentCombo.getSelectedItem();
                if (selected == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select a student");
                    return;
                }
                
                int studentId = Integer.parseInt(selected.split(" - ")[0]);
                String status = (String) statusCombo.getSelectedItem();
                LocalDate selectedDate = datePicker.getSelectedDate();
                Date sqlDate = Date.valueOf(selectedDate);
                
                Attendance attendance = new Attendance();
                attendance.setStudentId(studentId);
                attendance.setStatus(status);
                attendance.setDate(sqlDate);
                
                attendanceDAO.markAttendance(attendance);
                JOptionPane.showMessageDialog(dialog, "Attendance marked successfully!");
                loadTodayAttendance();
                dialog.dispose();
            } catch (SQLException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(UIConstants.SECONDARY);
        btnPanel.add(saveBtn);
        
        dialog.add(mainPanel, BorderLayout.CENTER);
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
                "Are you sure you want to delete this attendance record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    attendanceDAO.deleteAttendance(id);
                    JOptionPane.showMessageDialog(this, "Attendance record deleted!");
                    loadTodayAttendance();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete");
        }
    }
}