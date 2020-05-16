package dao;

import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventDao {

    private Connection connection;
    private PreparedStatement createStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findByDateStatement;
    private PreparedStatement deleteStatement;

    public EventDao(Connection connection) {
        this.connection = connection;
        try {
            createStatement = connection.prepareStatement("INSERT INTO events VALUES (null, ?, ?)");
            findAllStatement = connection.prepareStatement("SELECT * FROM events");
            findByDateStatement = connection.prepareStatement("SELECT * FROM events WHERE date = ?");
            deleteStatement = connection.prepareStatement("DELETE FROM events WHERE id = ? ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addEvent(Event event) {
        try {
            createStatement.setString(1, event.getName());
            createStatement.setString(2, event.getDate());
            return createStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        try {
            ResultSet rs = findAllStatement.executeQuery();
            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("date"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public Optional<Event> findByDate(String date) {
        try {
            findByDateStatement.setString(1, date);
            ResultSet rs = findByDateStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        new Event(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean deleteEvent(int id) {
        try {
            deleteStatement.setInt(1, id);
            return deleteStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
