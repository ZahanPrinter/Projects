package school.erp.ui.components;

import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class DarkTable extends JTable {
    
    public DarkTable(DefaultTableModel model) {
        super(model);
        setupDarkTheme();
    }
    
    private void setupDarkTheme() {
        // Table styling
        setRowHeight(35);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBackground(UIConstants.CARD_BG);
        setForeground(UIConstants.TEXT_DARK);
        setGridColor(UIConstants.BORDER_COLOR);
        setSelectionBackground(UIConstants.TABLE_SELECTION);
        setSelectionForeground(Color.WHITE);
        setShowGrid(true);
        setIntercellSpacing(new Dimension(1, 1));
        
        // Header styling
        JTableHeader header = getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(45, 45, 60));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        
        // Custom header renderer
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                          boolean isSelected, boolean hasFocus,
                                                          int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                setBackground(new Color(45, 45, 60));
                setForeground(Color.WHITE);
                setFont(new Font("Arial", Font.BOLD, 14));
                setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, UIConstants.BORDER_COLOR));
                setHorizontalAlignment(SwingConstants.LEFT);
                
                return this;
            }
        });
        
        // Custom cell renderer
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                          boolean isSelected, boolean hasFocus,
                                                          int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    setBackground(UIConstants.TABLE_SELECTION);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(UIConstants.CARD_BG);
                    setForeground(UIConstants.TEXT_DARK);
                }
                
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                
                return this;
            }
        });
    }
}