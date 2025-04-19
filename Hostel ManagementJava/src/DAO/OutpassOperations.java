package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Entities.Outpass;
import Services.DBController;

public class OutpassOperations {

    public List<Outpass> getPendingOutpasses() throws SQLException, ClassNotFoundException {
        List<Outpass> pendingList = new ArrayList<>();

        // Corrected SQL query to join outpass and apply tables
        String sql = "SELECT o.Outpass_ID, o.Date_of_issue, o.Departure_time, o.Return_time, o.Purpose, a.Student_ID AS Applier_ID " +
                "FROM outpass o " +
                "JOIN apply a ON o.Outpass_ID = a.Outpass_ID " +
                "WHERE o.Status = 'Pending'";

        try (Connection con = DBController.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Outpass outpass = new Outpass();

                outpass.setOutpassId(rs.getString("Outpass_ID"));
                outpass.setDateOfIssue(rs.getDate("Date_of_issue"));
                outpass.setDepartureTime(rs.getTime("Departure_time"));
                outpass.setReturnTime(rs.getTime("Return_time"));
                outpass.setPurpose(rs.getString("Purpose"));
                outpass.setApplierId(rs.getString("Applier_ID")); // from apply table

                pendingList.add(outpass);
            }
        }

        return pendingList;
    }
    public boolean approveDao(String outpassId, String wardenId,String comment) throws SQLException, ClassNotFoundException {
        String updateOutpass = "UPDATE outpass SET Status = 'Approved' WHERE Outpass_ID = ?";
        String insertApproval = "INSERT INTO approves (Warden_ID, Outpass_ID, Approval_date, Comments) VALUES (?, ?, CURRENT_DATE, ?)";

        try (Connection con = DBController.connect()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(updateOutpass);
                 PreparedStatement ps2 = con.prepareStatement(insertApproval)) {

                ps1.setString(1, outpassId);
                ps1.executeUpdate();

                ps2.setString(1, wardenId);
                ps2.setString(2, outpassId);
                ps2.setString(3, comment); // Customize comment as needed
                ps2.executeUpdate();

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean rejectDao(String outpassId, String wardenId, String comment) throws SQLException, ClassNotFoundException {
        String updateOutpass = "UPDATE outpass SET Status = 'Rejected' WHERE Outpass_ID = ?";
        String insertApproval = "INSERT INTO rejects (Warden_ID, Outpass_ID, Rejection_date, Comments) VALUES (?, ?, CURRENT_DATE, ?)";

        try (Connection con = DBController.connect()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(updateOutpass);
                 PreparedStatement ps2 = con.prepareStatement(insertApproval)) {

                ps1.setString(1, outpassId);
                ps1.executeUpdate();

                ps2.setString(1, wardenId);
                ps2.setString(2, outpassId);
                ps2.setString(3, comment); // Customize comment as needed
                ps2.executeUpdate();

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        }
    }
}
