package school.erp.ui.components;

import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DarkTextField extends JTextField {
    
    public DarkTextField() {
        this(20);
    }
    
    public DarkTextField(int columns) {
        super(columns);
        setupDarkTheme();
    }
    
    public DarkTextField(String text) {
        super(text);
        setupDarkTheme();
    }
    
    private void setupDarkTheme() {
        setBackground(UIConstants.CARD_BG);
        setForeground(UIConstants.TEXT_DARK);
        setCaretColor(UIConstants.TEXT_DARK);
        setSelectionColor(UIConstants.TABLE_SELECTION);
        setSelectedTextColor(Color.WHITE);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
    }
}