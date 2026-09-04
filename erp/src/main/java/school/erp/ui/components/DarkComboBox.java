package school.erp.ui.components;

import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class DarkComboBox<E> extends JComboBox<E> {
    
    public DarkComboBox() {
        super();
        setupDarkTheme();
    }
    
    public DarkComboBox(E[] items) {
        super(items);
        setupDarkTheme();
    }
    
    private void setupDarkTheme() {
        setBackground(UIConstants.CARD_BG);
        setForeground(UIConstants.TEXT_DARK);
        setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Custom renderer for dropdown items
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                                                         int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                setBackground(isSelected ? UIConstants.TABLE_SELECTION : UIConstants.CARD_BG);
                setForeground(isSelected ? Color.WHITE : UIConstants.TEXT_DARK);
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                
                return this;
            }
        });
        
        // Custom UI for the combobox itself
        setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▼");
                button.setBackground(UIConstants.CARD_BG);
                button.setForeground(UIConstants.TEXT_DARK);
                button.setBorder(BorderFactory.createEmptyBorder());
                return button;
            }
        });
    }
}