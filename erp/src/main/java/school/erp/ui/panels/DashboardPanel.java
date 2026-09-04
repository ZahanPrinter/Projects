package school.erp.ui.panels;

import school.erp.dao.*;
import school.erp.ui.components.StatCard;
import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {
    public DashboardPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(UIConstants.SECONDARY);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        JLabel header = new JLabel("Dashboard");
        header.setFont(UIConstants.HEADER_FONT);
        header.setForeground(UIConstants.TEXT_DARK);
        add(header, BorderLayout.NORTH);
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        statsPanel.setBackground(UIConstants.SECONDARY);
        
        try {
            StudentDAO studentDAO = new StudentDAO();
            TeacherDAO teacherDAO = new TeacherDAO();
            CourseDAO courseDAO = new CourseDAO();
            
            statsPanel.add(new StatCard("Total Students", studentDAO.getStudentCount()));
            statsPanel.add(new StatCard("Total Teachers", teacherDAO.getTeacherCount()));
            statsPanel.add(new StatCard("Total Courses", courseDAO.getCourseCount()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading dashboard: " + e.getMessage());
            e.printStackTrace();
        }
        
        add(statsPanel, BorderLayout.CENTER);
    }
}