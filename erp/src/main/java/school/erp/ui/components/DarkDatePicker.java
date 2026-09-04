package school.erp.ui.components;

import school.erp.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class DarkDatePicker extends JPanel {
    private LocalDate selectedDate;
    private JLabel monthYearLabel;
    private JPanel daysPanel;
    private List<DateSelectionListener> listeners = new ArrayList<>();
    private LocalDate currentMonth;
    
    public interface DateSelectionListener {
        void onDateSelected(LocalDate date);
    }
    
    public DarkDatePicker() {
        this(LocalDate.now());
    }
    
    public DarkDatePicker(LocalDate initialDate) {
        this.selectedDate = initialDate;
        this.currentMonth = YearMonth.from(initialDate).atDay(1);
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIConstants.CARD_BG);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        // Header with month/year and navigation
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.CARD_BG);
        
        DarkButton prevBtn = new DarkButton("<", UIConstants.ACCENT);
        prevBtn.setPreferredSize(new Dimension(40, 30));
        prevBtn.addActionListener(e -> {
            currentMonth = currentMonth.minusMonths(1);
            updateCalendar();
        });
        
        monthYearLabel = new JLabel();
        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 16));
        monthYearLabel.setForeground(UIConstants.TEXT_DARK);
        monthYearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        DarkButton nextBtn = new DarkButton(">", UIConstants.ACCENT);
        nextBtn.setPreferredSize(new Dimension(40, 30));
        nextBtn.addActionListener(e -> {
            currentMonth = currentMonth.plusMonths(1);
            updateCalendar();
        });
        
        DarkButton todayBtn = new DarkButton("Today", UIConstants.SUCCESS);
        todayBtn.setPreferredSize(new Dimension(80, 30));
        todayBtn.addActionListener(e -> {
            selectedDate = LocalDate.now();
            currentMonth = YearMonth.from(selectedDate).atDay(1);
            updateCalendar();
            notifyListeners();
        });
        
        headerPanel.add(prevBtn, BorderLayout.WEST);
        headerPanel.add(monthYearLabel, BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setBackground(UIConstants.CARD_BG);
        rightPanel.add(todayBtn);
        rightPanel.add(nextBtn);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Days of week header
        JPanel daysOfWeekPanel = new JPanel(new GridLayout(1, 7, 5, 5));
        daysOfWeekPanel.setBackground(UIConstants.CARD_BG);
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        
        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            label.setForeground(UIConstants.TEXT_GRAY);
            daysOfWeekPanel.add(label);
        }
        
        add(daysOfWeekPanel, BorderLayout.CENTER);
        
        // Days grid
        daysPanel = new JPanel(new GridLayout(6, 7, 5, 5));
        daysPanel.setBackground(UIConstants.CARD_BG);
        add(daysPanel, BorderLayout.SOUTH);
        
        updateCalendar();
    }
    
    private void updateCalendar() {
        daysPanel.removeAll();
        
        monthYearLabel.setText(currentMonth.getMonth().toString() + " " + currentMonth.getYear());
        
        YearMonth yearMonth = YearMonth.from(currentMonth);
        int daysInMonth = yearMonth.lengthOfMonth();
        int firstDayOfWeek = currentMonth.getDayOfWeek().getValue() % 7; // Sunday = 0
        
        // Add empty cells for days before the first day of month
        for (int i = 0; i < firstDayOfWeek; i++) {
            JLabel emptyLabel = new JLabel();
            emptyLabel.setOpaque(false);
            daysPanel.add(emptyLabel);
        }
        
        // Add day buttons
        for (int day = 1; day <= daysInMonth; day++) {
            final LocalDate date = currentMonth.withDayOfMonth(day);
            JButton dayBtn = createDayButton(date);
            daysPanel.add(dayBtn);
        }
        
        // Fill remaining cells
        int totalCells = firstDayOfWeek + daysInMonth;
        int remainingCells = (6 * 7) - totalCells;
        for (int i = 0; i < remainingCells; i++) {
            JLabel emptyLabel = new JLabel();
            emptyLabel.setOpaque(false);
            daysPanel.add(emptyLabel);
        }
        
        daysPanel.revalidate();
        daysPanel.repaint();
    }
    
    private JButton createDayButton(LocalDate date) {
        JButton btn = new JButton(String.valueOf(date.getDayOfMonth()));
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Styling based on date
        boolean isSelected = date.equals(selectedDate);
        boolean isToday = date.equals(LocalDate.now());
        boolean isPast = date.isBefore(LocalDate.now());
        
        if (isSelected) {
            btn.setBackground(UIConstants.ACCENT);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
        } else if (isToday) {
            btn.setBackground(UIConstants.SUCCESS);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
        } else if (isPast) {
            btn.setBackground(UIConstants.CARD_BG);
            btn.setForeground(UIConstants.TEXT_GRAY);
            btn.setOpaque(true);
        } else {
            btn.setBackground(UIConstants.CARD_BG);
            btn.setForeground(UIConstants.TEXT_DARK);
            btn.setOpaque(true);
        }
        
        btn.addActionListener(e -> {
            selectedDate = date;
            updateCalendar();
            notifyListeners();
        });
        
        return btn;
    }
    
    public void addDateSelectionListener(DateSelectionListener listener) {
        listeners.add(listener);
    }
    
    private void notifyListeners() {
        for (DateSelectionListener listener : listeners) {
            listener.onDateSelected(selectedDate);
        }
    }
    
    public LocalDate getSelectedDate() {
        return selectedDate;
    }
    
    public void setSelectedDate(LocalDate date) {
        this.selectedDate = date;
        this.currentMonth = YearMonth.from(date).atDay(1);
        updateCalendar();
    }
}