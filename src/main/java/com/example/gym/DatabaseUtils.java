package com.example.gym;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gym";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    // Get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // Get all members
    public static List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM Member";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Member member = new Member(
                        rs.getString("customerID"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getInt("age"),
                        rs.getString("address"),
                        rs.getString("domain"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("status")
                );
                members.add(member);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log the error in production
        }

        return members;
    }

    // Add a new member
    public static void addMember(Member member) {
        String query = "INSERT INTO Member (customerID, name, phone, gender, age, address, domain, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, member.getCustomerID()); // Correct syntax
            stmt.setString(2, member.getName());
            stmt.setString(3, member.getPhone());
            stmt.setString(4, member.getGender());
            stmt.setInt(5, member.getAge());
            stmt.setString(6, member.getAddress());
            stmt.setString(7, member.getDomain());

            // Ensure start and end dates are valid before converting to java.sql.Date
            stmt.setDate(8, member.getStartDate() != null ? java.sql.Date.valueOf(member.getStartDate()) : null);
            stmt.setDate(9, member.getEndDate() != null ? java.sql.Date.valueOf(member.getEndDate()) : null);

            stmt.setString(10, member.getStatus());

            stmt.executeUpdate(); // Execute the query to insert data into the database

        } catch (SQLException e) {
            e.printStackTrace(); // Log error for debugging or handling production issues
        }
    }

    // Update an existing member
    public static void updateMember(Member member) {
        String query = "UPDATE Member SET name = ?, phone = ?, gender = ?, age = ?, address = ?, domain = ?, start_date = ?, end_date = ?, status = ? WHERE customerID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, member.getName());
            stmt.setString(2, member.getPhone());
            stmt.setString(3, member.getGender());
            stmt.setInt(4, member.getAge());
            stmt.setString(5, member.getAddress());
            stmt.setString(6, member.getDomain());
            stmt.setDate(7, Date.valueOf(member.getStartDate())); // Ensure start date is not null
            stmt.setDate(8, Date.valueOf(member.getEndDate())); // Ensure end date is not null
            stmt.setString(9, member.getStatus());
            stmt.setString(10, member.getCustomerID());


            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Log error in production
        }
    }

    // Delete an existing member
    public static void deleteMember(String customerID) {
        String query = "DELETE FROM Member WHERE customerID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customerID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Log error in production
        }
    }

    // Get all coaches
    public static List<Coach> getCoaches() {
        List<Coach> coaches = new ArrayList<>();
        String query = "SELECT * FROM Coach";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Coach coach = new Coach(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("domain"),
                        rs.getString("address")
                );
                coaches.add(coach);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log error in production
        }

        return coaches;
    }

    // Add a new coach
    public static void addCoach(Coach coach) {
        String query = "INSERT INTO Coach (name, phone, gender, domain, address) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, coach.getName());
            stmt.setString(2, coach.getPhone());
            stmt.setString(3, coach.getGender());
            stmt.setString(4, coach.getDomain());
            stmt.setString(5, coach.getAddress());

            stmt.executeUpdate(); // Execute the prepared statement

        } catch (SQLException e) {
            e.printStackTrace(); // Log errors for debugging
        }
    }

    // Update an existing coach
    public static void updateCoach(Coach coach) {
        String query = "UPDATE Coach SET name = ?, phone = ?, gender = ?, domain = ?, address = ? WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, coach.getName());
            stmt.setString(2, coach.getPhone());
            stmt.setString(3, coach.getGender());
            stmt.setString(4, coach.getDomain());
            stmt.setString(5, coach.getAddress());
            stmt.setString(6, coach.getName()); // Where clause to identify coach

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Log error in production
        }
    }

    // Delete a coach
    public static void deleteCoach(String coachName) {
        String query = "DELETE FROM Coach WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, coachName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Log error in production
        }
    }

    // Add a new payment
    public static void addPayment(Payment payment) {
        String query = "INSERT INTO Payment (customerID, amount, payment_date) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, payment.getCustomerID());
            stmt.setInt(2, payment.getAmount());
            stmt.setDate(3, Date.valueOf(payment.getPaymentDate()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Log error in production
        }
    }

    // Get all payments
    public static List<Payment> getPayments() {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM Payment";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("paymentID"),
                        rs.getString("customerID"),
                        rs.getInt("amount"),
                        rs.getDate("payment_date").toLocalDate()
                );
                payments.add(payment);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log error in production
        }

        return payments;
    }
}
