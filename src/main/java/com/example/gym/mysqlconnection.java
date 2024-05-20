package com.example.gym;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt; // Import BCrypt

public class mysqlconnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gym";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public boolean signup(String username, String email, String password) {
        // Hash the password before storing it
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword); // Store hashed password
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error during signup: " + e.getMessage());
            return false;
        }
    }

    public boolean login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                // Validate the password with the stored hash using BCrypt
                if (BCrypt.checkpw(password, storedHash)) {
                    return true; // Correct password
                }
            }
            return false; // Incorrect password or username not found

        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
            return false;
        }
    }
}
