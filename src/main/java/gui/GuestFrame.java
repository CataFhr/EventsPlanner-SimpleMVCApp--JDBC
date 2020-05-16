package gui;

import controller.EventController;
import controller.GuestController;
import model.Event;
import model.Guest;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class GuestFrame extends JFrame {

    private JList list1;
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton addButton;
    private JMenuBar menuBar;
    private JMenu optionsMenu;
    private JMenuItem backItem;
    private Event event;
    private DefaultListModel<Guest> model;

    public GuestFrame(Event event) {
        super("Guests");
        this.event = event;
        model = new DefaultListModel<>();
        list1.setModel(model);
        showGuests();
        addButton.addActionListener(ev -> addGuests());

        backItem = new JMenuItem("back");
        backItem.addActionListener(ev -> back());
        optionsMenu = new JMenu("Options");
        optionsMenu.add(backItem);
        menuBar = new JMenuBar();
        menuBar.add(optionsMenu);

        setContentPane(mainPanel);
        setJMenuBar(menuBar);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void showGuests() {
        List<Guest> guests = GuestController.getInstance().findByEvent(event.getId());
        Collections.sort(guests);
        model.clear();
        guests.forEach(model::addElement);
    }

    private void addGuests() {
        String name = textField1.getText();
        String phone = textField2.getText();
        Guest guest = new Guest(0, name, phone, event.getId());
        List<Guest> guests = GuestController.getInstance().findByEvent(event.getId());
        if (guests.size() >= Event.getMAXCAPACITY()) {
            JOptionPane.showMessageDialog(null, "Nu mai e loc");
            return;
        }
        boolean result = GuestController.getInstance().addGuest(guest);
        if (result) {
            showGuests();
        } else {
            JOptionPane.showMessageDialog(null, "The guest is already on the event list");
        }
        textField1.setText("");
        textField2.setText("");
    }

    private void back() {
        new EventFrame();
        this.dispose();
    }

}
