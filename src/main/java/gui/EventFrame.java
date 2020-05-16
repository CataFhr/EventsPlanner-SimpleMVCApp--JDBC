package gui;

import controller.EventController;
import validator.MyValidator;
import model.Event;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;

public class EventFrame extends JFrame {

    private JPanel mainPanel;
    private JList list1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton addButton;
    private JMenuBar menuBar;
    private JMenu optionsMenu;
    private JMenuItem logoutItem;
    private DefaultListModel<Event> model;

    public EventFrame() {
        super("Events");
        model = new DefaultListModel<>();
        list1.setModel(model);
        showEvents();

        addButton.addActionListener(ev -> addEvent());
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                twoClickedForListAndDelete(e);
            }
        });
        logoutItem = new JMenuItem("logout");
        logoutItem.addActionListener(ev -> logout());
        optionsMenu = new JMenu("Options");
        optionsMenu.add(logoutItem);
        menuBar = new JMenuBar();
        menuBar.add(optionsMenu);

        setContentPane(mainPanel);
        setJMenuBar(menuBar);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void showEvents() {
        List<Event> events = EventController.getInstance().findAll();
        Collections.sort(events);
        model.clear();
        events.forEach(model::addElement);
    }

    private void addEvent() {
        String name = textField1.getText();
        String date = textField2.getText();
        if (MyValidator.isDateValid(date)) {
            Event event = new Event(0, name, date);
            boolean result = EventController.getInstance().addEvent(event);
            if (result) {
                showEvents();
            } else {
                JOptionPane.showMessageDialog(null, "There is already an event on this date");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect date");
        }
        textField1.setText("");
        textField2.setText("");
    }

    private void twoClickedForListAndDelete(MouseEvent e) {
        boolean isItemSelected = list1.getSelectedValue() != null;
        if (isItemSelected && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
            Event event = (Event) list1.getSelectedValue();
            new GuestFrame(event);
            this.dispose();
        }
        if (isItemSelected && e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 2) {
            Event event = (Event) list1.getSelectedValue();
            boolean result = EventController.getInstance().deleteEvent(event.getId());
            if (result) {
                showEvents();
            } else {
                JOptionPane.showMessageDialog(null, "Error");
            }
        }
    }

    private void logout() {
        new LoginFrame();
        this.dispose();
    }

}
