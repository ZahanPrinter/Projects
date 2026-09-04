package school.erp.ui;

import school.erp.database.DatabaseConnection;
import school.erp.ui.panels.*;
import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    public MainFrame() {
        setTitle("School ERP System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        DatabaseConnection.initializeDatabase();
        initUI();
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(UIConstants.SECONDARY);
        
        // Sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);
        
        // Main content area
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(UIConstants.SECONDARY);
        
        mainPanel.add(new DashboardPanel(), "Dashboard");
        mainPanel.add(new StudentPanel(), "Students");
        mainPanel.add(new TeacherPanel(), "Teachers");
        mainPanel.add(new CoursePanel(), "Courses");
        mainPanel.add(new AttendancePanel(), "Attendance");
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UIConstants.PRIMARY);
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        JLabel title = new JLabel("School ERP");
        title.setFont(UIConstants.TITLE_FONT);
        title.setForeground(UIConstants.ACCENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0, 0, 30, 0));
        sidebar.add(title);
        
        String[] menuItems = {"Dashboard", "Students", "Teachers", "Courses", "Attendance"};
        
        for (int i = 0; i < menuItems.length; i++) {
            sidebar.add(createMenuButton(menuItems[i]));
            sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        return sidebar;
    }
    
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setForeground(Color.WHITE); 
        btn.setBackground(UIConstants.PRIMARY);
        btn.setBorder(new EmptyBorder(15, 20, 15, 20));
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(230, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(UIConstants.HOVER_PRIMARY);
                btn.setForeground(UIConstants.ACCENT);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(UIConstants.PRIMARY);
                btn.setForeground(Color.WHITE);
            }
        });
        
        btn.addActionListener(e -> cardLayout.show(mainPanel, text));
        
        return btn;
    }
}