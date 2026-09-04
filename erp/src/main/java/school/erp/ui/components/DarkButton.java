package school.erp.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DarkButton extends JButton {
    private Color originalColor;
    private Color hoverColor;
    
    public DarkButton(String text, Color bgColor) {
        super(text);
        this.originalColor = bgColor;
        this.hoverColor = bgColor.brighter();
        
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setBackground(bgColor);
        setFocusPainted(false);
        setOpaque(true);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(120, 40));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(originalColor);
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Paint background
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        
        // Paint text
        g2d.setColor(getForeground());
        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(getText())) / 2;
        int textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(getText(), textX, textY);
        
        g2d.dispose();
    }
}