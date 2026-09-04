package school.erp.ui.components;

import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StyledButton extends JButton {
    private Color originalColor;
    
    public StyledButton(String text, Color bgColor) {
        super(text);
        this.originalColor = bgColor;
        setFont(UIConstants.BUTTON_FONT);
        setForeground(Color.BLACK);
        setBackground(bgColor);
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setFocusPainted(false);
        setOpaque(true);
        setContentAreaFilled(true);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Force UI to use our colors
        setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(bgColor.brighter());
            }
            public void mouseExited(MouseEvent e) {
                setBackground(originalColor);
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}