package school.erp.main;

import school.erp.ui.MainFrame;
import school.erp.utils.DarkTheme;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        
        DarkTheme.apply();
        
        SwingUtilities.invokeLater(() -> {
            MainFrame app = new MainFrame();
            app.setVisible(true);
        });
    }
}