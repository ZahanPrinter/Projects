package school.erp.ui.components;

import school.erp.utils.UIConstants;
import javax.swing.*;
import java.awt.*;

public class DarkScrollPane extends JScrollPane {
    
    public DarkScrollPane(Component view) {
        super(view);
        setupDarkTheme();
    }
    
    private void setupDarkTheme() {
        getViewport().setBackground(UIConstants.CARD_BG);
        setBackground(UIConstants.CARD_BG);
        setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1));
        
        // Style scrollbars
        getVerticalScrollBar().setBackground(UIConstants.PRIMARY);
        getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = UIConstants.BORDER_COLOR;
                this.trackColor = UIConstants.PRIMARY;
            }
            
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
            
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }
        });
        
        getHorizontalScrollBar().setBackground(UIConstants.PRIMARY);
        getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = UIConstants.BORDER_COLOR;
                this.trackColor = UIConstants.PRIMARY;
            }
            
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
            
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }
        });
    }
}