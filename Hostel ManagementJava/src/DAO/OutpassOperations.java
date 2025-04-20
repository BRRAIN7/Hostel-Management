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
    public boolean createOutpass(Outpass outpass, String studentId) {
        String outpassQuery = "INSERT INTO outpass (Outpass_ID, Date_of_issue, Departure_time, Return_time, Purpose, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String applyQuery = "INSERT INTO apply (Student_ID, Outpass_ID) VALUES (?, ?)";

        try (Connection conn = DBController.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstOutpass = conn.prepareStatement(outpassQuery);
                 PreparedStatement pstApply = conn.prepareStatement(applyQuery)) {

                // Insert outpass
                pstOutpass.setString(1, outpass.getOutpassId());
                pstOutpass.setDate(2, outpass.getDateOfIssue());
                pstOutpass.setTime(3, outpass.getDepartureTime());
                pstOutpass.setTime(4, outpass.getReturnTime());
                pstOutpass.setString(5, outpass.getPurpose());
                pstOutpass.setString(6, outpass.getStatus());
                pstOutpass.executeUpdate();

                // Link to student
                pstApply.setString(1, studentId);
                pstApply.setString(2, outpass.getOutpassId());
                pstApply.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Transaction failed: " + ex.getMessage());
                return false;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
            return false;
        }
    }
    public String generateOutpassId() {
        String newId = "O001";
        String query = "SELECT MAX(Outpass_ID) AS MaxID FROM outpass";

        try (Connection con = DBController.connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            if (rs.next() && rs.getString("MaxID") != null) {
                int idNum = Integer.parseInt(rs.getString("MaxID").substring(1)) + 1;
                newId = "O" + String.format("%03d", idNum);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("ID generation error: " + ex.getMessage());
        }
        return newId;
    }
    public List<Outpass> getOutpassesByStudent(String studentId) {
        List<Outpass> outpasses = new ArrayList<>();
        String query = "SELECT o.*, a.Student_ID AS Applier_ID FROM outpass o "
                + "JOIN apply a ON o.Outpass_ID = a.Outpass_ID "
                + "WHERE a.Student_ID = ? ORDER BY o.Date_of_issue DESC";

        try (Connection conn = DBController.connect();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, studentId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Outpass op = new Outpass();
                    op.setOutpassId(rs.getString("Outpass_ID"));
                    op.setDateOfIssue(rs.getDate("Date_of_issue"));
                    op.setDepartureTime(rs.getTime("Departure_time"));
                    op.setReturnTime(rs.getTime("Return_time"));
                    op.setPurpose(rs.getString("Purpose"));
                    op.setStatus(rs.getString("Status"));
                    op.setApplierId(rs.getString("Applier_ID"));
                    outpasses.add(op);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Query error: " + ex.getMessage());
        }
        return outpasses;
    }
    public boolean updateOutpass(Outpass outpass) {
        String query = "UPDATE outpass SET Purpose = ?, Departure_time = ?, "
                + "Return_time = ?, Status = ? WHERE Outpass_ID = ?";

        try (Connection conn = DBController.connect();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, outpass.getPurpose());
            pst.setTime(2, outpass.getDepartureTime());
            pst.setTime(3, outpass.getReturnTime());
            pst.setString(4, outpass.getStatus());
            pst.setString(5, outpass.getOutpassId());

            return pst.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Update error: " + ex.getMessage());
            return false;
        }
    }
}
