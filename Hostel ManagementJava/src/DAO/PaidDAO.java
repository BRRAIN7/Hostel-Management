package DAO;

import Entities.Paid;
import Services.DBController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaidDAO {
    private DBController dbController;

    public PaidDAO() {
        this.dbController = new DBController();
    }

    public List<String> getStudentFeeIds(String studentId) throws SQLException, ClassNotFoundException {
        List<String> feeIds = new ArrayList<>();
        String query = "SELECT Fee_ID FROM pays WHERE Student_ID = ?";

        try (Connection conn = dbController.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                feeIds.add(rs.getString("Fee_ID"));
            }
        }

        return feeIds;
    }

    public boolean updatePaymentDate(String studentId, String feeId) throws SQLException, ClassNotFoundException {
        String query = "UPDATE pays SET Payment_date = CURDATE() WHERE Student_ID = ? AND Fee_ID = ?";

        try (Connection conn = dbController.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
            stmt.setString(2, feeId);

            return stmt.executeUpdate() > 0;
        }
    }
}