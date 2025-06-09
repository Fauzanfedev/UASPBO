package dao;

import model.User;
import db.DatabaseConnection;

import java.sql.*;

public class UserDAO {
    public static User login(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                return new User(username, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // login gagal
    }

    public static void tambahUser (User user) {
        String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
