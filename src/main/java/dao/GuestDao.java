package dao;

import model.Guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GuestDao {

    private Connection connection;
    private PreparedStatement createStatement;
    private PreparedStatement findByEventStatement;
    private PreparedStatement findByPhoneAndEventStatement;

    public GuestDao(Connection connection) {
        this.connection = connection;
        try {
            createStatement = connection.prepareStatement("INSERT INTO guests VALUES (null, ? , ?, ?)");
            findByEventStatement = connection.prepareStatement(("SELECT * FROM guests WHERE event_id = ?"));
            findByPhoneAndEventStatement = connection.prepareStatement("SELECT * FROM guests WHERE (phone = ? AND event_id = ?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addGuest(Guest guest) {
        try {
            createStatement.setString(1, guest.getName());
            createStatement.setString(2, guest.getPhoneNumber());
            createStatement.setInt(3, guest.getEventId());
            return createStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Guest> findByEvent(int eventId) {
        List<Guest> guests = new ArrayList<>();
        try {
            findByEventStatement.setInt(1, eventId);
            ResultSet rs = findByEventStatement.executeQuery();
            while (rs.next()) {
                Guest guest = new Guest(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("event_id"));
                guests.add(guest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guests;
    }

    public Optional<Guest> findByPhoneNumber(String phoneNo, int eventId) {
        try {
            findByPhoneAndEventStatement.setString(1, phoneNo);
            findByPhoneAndEventStatement.setInt(2, eventId);
            ResultSet rs = findByPhoneAndEventStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        new Guest(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("phone"),
                                rs.getInt("event_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
