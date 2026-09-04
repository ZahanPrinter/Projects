package school.erp.utils;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;

public class DarkTheme {
    
    public static void apply() {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
            
            // Override ALL default UI colors
            UIManager.put("control", new ColorUIResource(UIConstants.CARD_BG));
            UIManager.put("info", new ColorUIResource(UIConstants.CARD_BG));
            UIManager.put("nimbusBase", new ColorUIResource(UIConstants.PRIMARY));
            UIManager.put("nimbusAlertYellow", new ColorUIResource(UIConstants.SUCCESS));
            UIManager.put("nimbusDisabledText", new ColorUIResource(UIConstants.TEXT_GRAY));
            UIManager.put("nimbusFocus", new ColorUIResource(UIConstants.ACCENT));
            UIManager.put("nimbusGreen", new ColorUIResource(UIConstants.SUCCESS));
            UIManager.put("nimbusInfoBlue", new ColorUIResource(UIConstants.ACCENT));
            UIManager.put("nimbusLightBackground", new ColorUIResource(UIConstants.CARD_BG));
            UIManager.put("nimbusOrange", new ColorUIResource(UIConstants.DANGER));
            UIManager.put("nimbusRed", new ColorUIResource(UIConstants.DANGER));
            UIManager.put("nimbusSelectedText", new ColorUIResource(Color.WHITE));
            UIManager.put("nimbusSelectionBackground", new ColorUIResource(UIConstants.TABLE_SELECTION));
            UIManager.put("text", new ColorUIResource(UIConstants.TEXT_DARK));
            
            // Panels
            UIManager.put("Panel.background", new ColorUIResource(UIConstants.SECONDARY));
            
            // Buttons
            UIManager.put("Button.background", new ColorUIResource(UIConstants.ACCENT));
            UIManager.put("Button.foreground", new ColorUIResource(Color.WHITE));
            UIManager.put("Button.select", new ColorUIResource(UIConstants.ACCENT.brighter()));
            UIManager.put("Button.focus", new ColorUIResource(UIConstants.BORDER_COLOR));
            UIManager.put("Button.border", BorderFactory.createEmptyBorder(10, 20, 10, 20));
            
            // Text Fields
            UIManager.put("TextField.background", new ColorUIResource(UIConstants.CARD_BG));
            UIManager.put("TextField.foreground", new ColorUIResource(UIConstants.TEXT_DARK));
            UIManager.put("TextField.caretForeground", new ColorUIResource(UIConstants.TEXT_DARK));
            UIManager.put("TextField.selectionBackground", new ColorUIResource(UIConstants.TABLE_SELECTION));
            UIManager.put("TextField.selectionForeground", new ColorUIResource(Color.WHITE));
            UIManager.put("TextField.inactiveForeground", new ColorUIResource(UIConstants.TEXT_GRAY));
            
            // Text Areas
            UIManager.put("TextArea.background", new ColorUIResource(UIConstants.CARD_BG));
            UIManager.put("TextArea.foreground", new ColorUIResource(UIConstants.TEXT_DARK));
            UIManager.put("TextArea.caretForeground", new ColorUIResource(UIConstants.TEXT_DARK));
            UIManager.put("TextArea.selectionBackground", new ColorUIResource(UIConstants.TABLE_SELECTION));
            UIManager.put("TextArea.selectionForeground", new ColorUIResource(Color.WHITE));
            
            // Labels
            UIManager.put("Label.foreground", new ColorUIResource(UIConstants.TEXT_DARK));
            UIManager.put("Label.background", new ColorUIResource(UIConstants.SECONDARY));
            
            // Tables
            UIManager.put("Table.background", new ColorUIResource(UIConstants.CARD_BG));
            UIManager.put("Table.foreground", new ColorUIResource(UIConstants.TEXT_DARK));
            UIManager.put("Table.selectionBackground", new ColorUIResource(UIConstants.TABLE_SELECTION));
            UIManager.put("Table.selectionForeground", new ColorUIResource(Color.WHITE));
            UIManager.put("Table.gridColor", new ColorUIResource(UIConstants.BORDER_COLOR));
            UIManager.put("Table.focusCellBackground", new ColorUIResource(UIConstants.TABLE_SELECTION));
            UIManager.put("Table.focusCellForeground", new ColorUIResource(Color.WHITE));
            
            // Table Headers
            UIManager.put("TableHeader.background", new ColorUIResource(new Color(45, 45, 60)));
            UIManager.put("TableHeader.foreground", new ColorUIResource(Color.WHITE));
            UIManager.put("TableHeader.cellBorder", BorderFactory.createMatteBorder(0, 0, 1, 1, UIConstants.BORDER_COLOR));
            
            // Scroll Panes
            UIManager.put("ScrollPane.background", new ColorUIResource(UIConstants.CARD_BG));
            UIManager.put("ScrollPane.foreground", new ColorUIResource(UIConstants.TEXT_DARK));
            
            // Scroll Bars
            UIManager.put("ScrollBar.background", new ColorUIResource(UIConstants.PRIMARY));
            UIManager.put("ScrollBar.foreground", new ColorUIResource(UIConstants.BORDER_COLOR));
            UIManager.put("ScrollBar.thumb", new ColorUIResource(UIConstants.BORDER_COLOR));
            UIManager.put("ScrollBar.thumbHighlight", new ColorUIResource(UIConstants.TEXT_GRAY));
            UIManager.put("ScrollBar.thumbShadow", new ColorUIResource(UIConstants.PRIMARY));
            UIManager.put("ScrollBar.track", new ColorUIResource(UIConstants.PRIMARY));
            
            // ComboBox
            UIManager.put("ComboBox.background", new ColorUIResource(UIConstants.CARD_BG));
            UIManager.put("ComboBox.foreground", new ColorUIResource(UIConstants.TEXT_DARK));
            UIManager.put("ComboBox.selectionBackground", new ColorUIResource(UIConstants.TABLE_SELECTION));
            UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.WHITE));
            UIManager.put("ComboBox.disabledBackground", new ColorUIResource(UIConstants.PRIMARY));
            UIManager.put("ComboBox.disabledForeground", new ColorUIResource(UIConstants.TEXT_GRAY));
            
            // Option Pane (Dialogs)
            UIManager.put("OptionPane.background", new ColorUIResource(UIConstants.SECONDARY));
            UIManager.put("OptionPane.foreground", new ColorUIResource(UIConstants.TEXT_DARK));
            UIManager.put("OptionPane.messageForeground", new ColorUIResource(UIConstants.TEXT_DARK));
            
            // Menu Bar
            UIManager.put("MenuBar.background", new ColorUIResource(UIConstants.PRIMARY));
            UIManager.put("MenuBar.foreground", new ColorUIResource(Color.WHITE));
            
            // Menu
            UIManager.put("Menu.background", new ColorUIResource(UIConstants.PRIMARY));
            UIManager.put("Menu.foreground", new ColorUIResource(Color.WHITE));
            UIManager.put("Menu.selectionBackground", new ColorUIResource(UIConstants.HOVER_PRIMARY));
            UIManager.put("Menu.selectionForeground", new ColorUIResource(Color.WHITE));
            
            // Menu Item
            UIManager.put("MenuItem.background", new ColorUIResource(UIConstants.PRIMARY));
            UIManager.put("MenuItem.foreground", new ColorUIResource(Color.WHITE));
            UIManager.put("MenuItem.selectionBackground", new ColorUIResource(UIConstants.HOVER_PRIMARY));
            UIManager.put("MenuItem.selectionForeground", new ColorUIResource(Color.WHITE));
            
            // Checkbox
            UIManager.put("CheckBox.background", new ColorUIResource(UIConstants.SECONDARY));
            UIManager.put("CheckBox.foreground", new ColorUIResource(UIConstants.TEXT_DARK));
            
            // Radio Button
            UIManager.put("RadioButton.background", new ColorUIResource(UIConstants.SECONDARY));
            UIManager.put("RadioButton.foreground", new ColorUIResource(UIConstants.TEXT_DARK));
            
            // Tooltips
            UIManager.put("ToolTip.background", new ColorUIResource(UIConstants.PRIMARY));
            UIManager.put("ToolTip.foreground", new ColorUIResource(Color.WHITE));
            
            // Progress Bar
            UIManager.put("ProgressBar.background", new ColorUIResource(UIConstants.PRIMARY));
            UIManager.put("ProgressBar.foreground", new ColorUIResource(UIConstants.ACCENT));
            UIManager.put("ProgressBar.selectionBackground", new ColorUIResource(Color.WHITE));
            UIManager.put("ProgressBar.selectionForeground", new ColorUIResource(UIConstants.TEXT_DARK));
            
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}