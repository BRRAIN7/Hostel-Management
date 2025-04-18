package DAO;

import Entities.*;
import Services.DBController;

import java.sql.*;
import java.util.*;

public class ComplaintOperations {

    public List<Complaint> getAllComplaints() throws SQLException, ClassNotFoundException {
        List<Complaint> complaints = new ArrayList<>();

        Connection con =new DBController().connect();

        // Student complaints
        PreparedStatement ps = con.prepareStatement("SELECT * FROM complaint");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            complaints.add(new StudentComplaint(
                    rs.getString("Complaint_ID"),
                    rs.getString("Date_of_complaint"),
                    rs.getString("Description"),
                    rs.getString("Status"),
                    rs.getString("Resolution")));
        }

        // Staff complaints
        ps = con.prepareStatement("SELECT * FROM Staff_complaints");
        rs = ps.executeQuery();
        while (rs.next()) {
            complaints.add(new StaffComplaint(
                    rs.getString("S_complaint_id"),
                    rs.getString("Complaint_date"),
                    rs.getString("C_description"),
                    rs.getString("C_status"),
                    rs.getString("Resolution")));
        }

        return complaints;
    }

    public boolean updateComplaint(String complaintId, String status, String resolution) throws SQLException, ClassNotFoundException  {
        Connection con = DBController.connect();

        // Detect whether it's a student or staff complaint
        String prefix = complaintId.substring(0, 3);

        String sql;
        if (prefix.equalsIgnoreCase("C00")) {
            sql = "UPDATE complaint SET Status = ?, Resolution = ? WHERE Complaint_ID = ?";
        } else {
            sql = "UPDATE Staff_complaints SET C_status = ?, Resolution = ? WHERE S_complaint_id = ?";
        }

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, status);
        ps.setString(2, resolution);
        ps.setString(3, complaintId);

        return ps.executeUpdate() > 0;
    }
    public String generateNextComplaintId() throws Exception {
        Connection con = DBController.connect();
        PreparedStatement ps = con.prepareStatement("SELECT Complaint_ID FROM complaint ORDER BY Complaint_ID DESC LIMIT 1");
        ResultSet rs = ps.executeQuery();

        String lastId = "C000";
        if (rs.next()) {
            lastId = rs.getString("Complaint_ID");
        }

        int nextNumber = Integer.parseInt(lastId.substring(1)) + 1;
        return String.format("C%03d", nextNumber);
    }

    public boolean fileStudentComplaint(StudentComplaint complaint, String studentId) throws Exception {
        Connection con = DBController.connect();

        // Insert into complaint table
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO complaint (Complaint_ID, Date_of_complaint, Description, Status) VALUES (?, ?, ?, ?)");
        ps.setString(1, complaint.getComplaintId());
        ps.setString(2, complaint.getDate());
        ps.setString(3, complaint.getDescription());
        ps.setString(4, complaint.getStatus());
        ps.executeUpdate();

        // Insert into files table
        ps = con.prepareStatement("INSERT INTO files (Student_ID, Complaint_ID, Filing_date) VALUES (?, ?, ?)");
        ps.setString(1, studentId);
        ps.setString(2, complaint.getComplaintId());
        ps.setString(3, complaint.getDate());

        return ps.executeUpdate() > 0;
    }

    public List<StudentComplaint> getComplaintsByStudent(String studentId) throws Exception {
        List<StudentComplaint> complaints = new ArrayList<>();
        Connection con = DBController.connect();

        PreparedStatement ps = con.prepareStatement(
                "SELECT c.Complaint_ID, c.Date_of_complaint, c.Description, c.Status, c.Resolution " +
                        "FROM complaint c JOIN files f ON c.Complaint_ID = f.Complaint_ID WHERE f.Student_ID = ?");
        ps.setString(1, studentId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            complaints.add(new StudentComplaint(
                    rs.getString("Complaint_ID"),
                    rs.getString("Date_of_complaint"),
                    rs.getString("Description"),
                    rs.getString("Status"),
                    rs.getString("Resolution")));
        }

        return complaints;
    }
}
