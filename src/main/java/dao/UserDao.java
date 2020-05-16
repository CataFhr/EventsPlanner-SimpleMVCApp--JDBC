package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {

    private Connection connection;
    private PreparedStatement findByUsernameStatement;
    private PreparedStatement createStatement;

    public UserDao(Connection connection) {
        this.connection = connection;
        try {
            findByUsernameStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            createStatement = connection.prepareStatement("INSERT INTO users VALUES (null, ?, ?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findUser(String username) {
        try {
            findByUsernameStatement.setString(1, username);
            User user = null;
            ResultSet rs = findByUsernameStatement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername((rs.getString("username")));
                user.setPassword(rs.getString("password"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void addUser (User user) {
        try {
            createStatement.setString(1,user.getUsername());
            createStatement.setString(2, user.getPassword());
            createStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
