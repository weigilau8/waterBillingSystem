package com.example.waterbillingsystem.Controllers;

import java.sql.*;
import java.util.function.Function;

import static java.sql.DriverManager.getConnection;

public class DatabaseConnectionController {

    private static final String dbName = "wbs";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
    private static final String dbUser = "root";
    private static final String dbPassword = "rootLang";
    private static Connection connection = null;

    // Verify if database is connected
    public static void initializeDB() throws SQLException {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database: " + e.getMessage());
        }
    }

    // Verify login and return user data
    public static ResultSet loginVerification(String username, String password) throws SQLException {

        // SQL statement to query from database
        String query = "SELECT * FROM AdminUsers WHERE Username = ? AND Password = ?";
        PreparedStatement beforeQuery = connection.prepareStatement(query);
        beforeQuery.setString(1, username);
        beforeQuery.setString(2, password);
        return beforeQuery.executeQuery();
    }

    // Executing Count query
    private static int countData(String query) throws SQLException {
        return executeQuery(query, rs -> {
            try {
                return rs.next() ? rs.getInt(1) : 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Generic method to execute count queries
    public static <T> T executeQuery(String query, Function<ResultSet, T> processor) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            return processor.apply(rs);
        }
    }

    // Count total customers
    public static int getTotalCustomers() throws SQLException {
        return countData("SELECT COUNT(*) FROM Customers");
    }

    // Count pending bills
    public static int getPendingBills() throws SQLException {
        return countData("SELECT COUNT(*) FROM Bills WHERE Status = 'Pending'");
    }

    // Count paid bills
    public static int getPaidBills() throws SQLException {
        return countData("SELECT COUNT(*) FROM Bills WHERE Status = 'Paid'");
    }

    // Count overdue bills
    public static int getOverdueBills() throws SQLException {
        return countData("SELECT COUNT(*) FROM Bills WHERE Status = 'Overdue'");
    }


    // Get all Customers
    public static ResultSet getAllCustomers() throws SQLException {
        String getAllCust = "SELECT * FROM Customers";
        PreparedStatement dataAllCust = connection.prepareStatement(getAllCust);

        return dataAllCust.executeQuery();
    }

    // Get all Bills
    public static ResultSet getAllBills() throws SQLException {
        String getBills = "SELECT b.CustomerID, CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName, " +
                "b.BillDate, b.DueDate, b.UnitsConsumed, b.RatePerUnit, b.FixedCharge, b.Status " +
                "FROM Bills b JOIN Customers c ON b.CustomerID = c.CustomerID";

        PreparedStatement databills = connection.prepareStatement(getBills);
        return databills.executeQuery();

    }

    // to add customer method return boolean
    public static boolean addCustomer(String firstName, String lastName, String email, String phone, String address, String city, String state, String zipcode, String servicecenter) throws SQLException {
        String beforeAdd = "INSERT INTO Customers (FirstName, LastName, Email, Phone, Address, City, State, ZipCode, ServiceCenter) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(beforeAdd)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, city);
            preparedStatement.setString(7, state);
            preparedStatement.setString(8, zipcode);
            preparedStatement.setString(9, servicecenter);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // old Code

    // Count all Customers
//    public static int getTotalCustomers() throws SQLException {
//        String query = "SELECT COUNT(*) FROM Customers";
//
//        try (PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        }
//        return 0;
//    }

    // Count pending status
//    public static int getPendingBills() throws SQLException {
//        String query = "SELECT COUNT(*) as pending_count FROM Bills WHERE Status = 'Pending'";
//        int count = 0;
//
//        try (PreparedStatement pstmt = connection.prepareStatement(query);
//             ResultSet rs = pstmt.executeQuery()) {
//
//            if (rs.next()) {
//                count = rs.getInt("pending_count");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return count;
//    }

    // Count Paid status
//    public static int getPaidBills() throws SQLException {
//        String query = "SELECT COUNT(*) as pending_count FROM Bills WHERE Status = 'Paid'";
//        int count = 0;
//
//        try (PreparedStatement pstmt = connection.prepareStatement(query);
//             ResultSet rs = pstmt.executeQuery()) {
//
//            if (rs.next()) {
//                count = rs.getInt("pending_count");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return count;
//    }

    // Count Overdue status
//    public static int getOverdueBills() throws SQLException {
//        String query = "SELECT COUNT(*) as pending_count FROM Bills WHERE Status = 'Overdue'";
//        int count = 0;
//
//        try (PreparedStatement pstmt = connection.prepareStatement(query);
//             ResultSet rs = pstmt.executeQuery()) {
//
//            if (rs.next()) {
//                count = rs.getInt("pending_count");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return count;
//    }

}
