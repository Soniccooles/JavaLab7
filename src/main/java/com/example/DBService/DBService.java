package com.example.DBService;

import com.example.accounts.UserProfile;
import java.sql.*;

public class DBService {
    private final Connection connection;

    public DBService() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    public void addUser(UserProfile user) throws SQLException {
        String sql = "INSERT INTO users (name, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.executeUpdate();
        }
    }

    public UserProfile getUserByLogin(String login) throws SQLException {
        String sql = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserProfile(
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getString("email"));
                }
            }
        }
        return null;
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}