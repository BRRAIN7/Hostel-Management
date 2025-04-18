package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Entities.Fee;
import Services.DBController;

public class FeeOperations {
    public static List<Fee> getFeeDetails() throws SQLException, ClassNotFoundException {
        List<Fee> feeList = new ArrayList<>();

        // SQL query to get fee details
        String query = "SELECT f.Fee_ID, f.Amount, f.Due_date, f.Status, f.Payment_method, p.Payment_date, p.Student_ID " +
                "FROM fee f LEFT JOIN pays p ON f.Fee_ID = p.Fee_ID";

        try (Connection con = DBController.connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Fee fee = new Fee();
                fee.setFeeID(rs.getString("Fee_ID"));
                fee.setAmount(rs.getFloat("Amount"));
                fee.setDueDate(rs.getDate("Due_date"));
                fee.setStatus(rs.getString("Status"));
                fee.setPaymentMethod(rs.getString("Payment_method"));
                fee.setPaymentDate(rs.getDate("Payment_date"));
                fee.setStudentID(rs.getString("Student_ID"));

                feeList.add(fee);
            }
        }
        return feeList;
    }
    public Fee getFeeById(String feeId) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM fee WHERE Fee_ID = ?";
        Fee fee = null;

        try (Connection conn = DBController.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, feeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                fee = new Fee();
                fee.setFeeID(rs.getString("Fee_ID"));
                fee.setAmount(rs.getDouble("Amount"));
                fee.setStatus(rs.getString("Status"));
                fee.setPaymentMethod(rs.getString("Payment_method"));
            }
        }

        return fee;
    }
    public List<Fee> getUnpaidFeesByStudentId(String studentId) throws SQLException, ClassNotFoundException {
        List<Fee> fees = new ArrayList<>();
        String query = "SELECT f.* FROM fee f JOIN pays p ON f.Fee_ID = p.Fee_ID WHERE p.Student_ID = ? AND (f.Status = 'Unpaid' OR f.Status = 'Partially Paid')";

        try (Connection conn = DBController.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Fee fee = new Fee();
                fee.setFeeID(rs.getString("Fee_ID"));
                fee.setAmount(rs.getDouble("Amount"));
                fee.setStatus(rs.getString("Status"));
                fee.setPaymentMethod(rs.getString("Payment_method"));
                fees.add(fee);
            }
        }

        return fees;
    }
    public static String getFeeIdForStudent(String studentId) throws SQLException, ClassNotFoundException {
        String query = "SELECT Fee_ID FROM pays WHERE Student_ID = ? AND Fee_ID IN (SELECT Fee_ID FROM fee WHERE Status = 'Unpaid' OR Status = 'Partially Paid') LIMIT 1";

        try (Connection conn = DBController.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Fee_ID");
            }
        }

        return null;
    }
    public static boolean updateFeeStatus(String feeId) throws SQLException, ClassNotFoundException {
        String query = "UPDATE fee SET Status = 'Paid', Payment_method = 'Online' WHERE Fee_ID = ?";

        try (Connection conn = DBController.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, feeId);
            return stmt.executeUpdate() > 0;
        }
    }
    public static boolean updatePaymentDate(String studentId, String feeId) throws SQLException, ClassNotFoundException {
        String query = "UPDATE pays SET Payment_date = CURDATE() WHERE Student_ID = ? AND Fee_ID = ?";

        try (Connection conn = DBController.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
            stmt.setString(2, feeId);
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean updateFeeStatus(String feeId, String status, String paymentMethod) throws SQLException, ClassNotFoundException {
        String query = "UPDATE fee SET Status = ?, Payment_method = ? WHERE Fee_ID = ?";

        try (Connection conn = DBController.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setString(2, paymentMethod);
            stmt.setString(3, feeId);

            return stmt.executeUpdate() > 0;
        }
    }
}
