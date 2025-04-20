package DAO;

import Entities.Staff;
import Services.DBController;
import java.sql.*;

public class StaffOperations {

    // Method to get the next Staff ID
    public static String getNextStaffID() throws SQLException, ClassNotFoundException {
        String staffID = "";

        // Connect to the database
        DBController db = new DBController();
        Connection con = db.connect();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Staff_ID FROM staff ORDER BY Staff_ID DESC LIMIT 1");

        if (rs.next()) {
            String lastStaffID = rs.getString("Staff_ID");
            // Extract the numeric part and increment it
            String numericPart = lastStaffID.substring(2); // Extracts "005" from "ST005"
            int nextID = Integer.parseInt(numericPart) + 1;
            // Format the next ID with leading zeros (e.g., ST006, ST100)
            staffID = "ST" + String.format("%03d", nextID);  // Ensure it's padded to 3 digits
        } else {
            // If no staff records are present, the first Staff ID should be ST001
            staffID = "ST001";
        }

        // Close resources
        rs.close();
        stmt.close();
        con.close();

        return staffID;
    }

    // Method to add new Staff
    public static boolean addStaffDao(Staff staff) throws SQLException, ClassNotFoundException {
        String staffID = getNextStaffID();
        staff.setStaffId(staffID);  // Assign the generated Staff ID to the staff object

        // Connect to the database
        DBController db = new DBController();
        Connection con = db.connect();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO staff (Staff_ID, name, role, contact, email, address, salary) VALUES (?, ?, ?, ?, ?, ?, ?)");

        ps.setString(1, staff.getStaffId());  // Set the auto-generated Staff ID
        ps.setString(2, staff.getName());
        ps.setString(3, staff.getRole());
        ps.setString(4, staff.getContactNumber());
        ps.setString(5, staff.getEmail());
        ps.setString(6, staff.getAddress());
        ps.setFloat(7, staff.getSalary());

        int rowsAffected = ps.executeUpdate();

        // Close resources
        ps.close();
        con.close();

        return rowsAffected > 0; // Return true if row insertion was successful
    }
    public Staff getStaffById(String staffId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM staff WHERE Staff_ID = ?";
        try (Connection con = DBController.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, staffId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Staff(
                        rs.getString("Name"),
                        rs.getString("Contact"),
                        rs.getString("Address"),
                        rs.getString("Email"),
                        rs.getString("Staff_ID"),
                        rs.getString("Role"),
                        rs.getFloat("Salary")
                );
            }
        }
        return null;
    }

    public boolean updateStaff(Staff staff) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE staff SET Name=?, Role=?, Contact=?, Email=?, Address=?, Salary=? WHERE Staff_ID=?";
        try (Connection con = DBController.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, staff.getName());
            ps.setString(2, staff.getRole());
            ps.setString(3, staff.getContactNumber());
            ps.setString(4, staff.getEmail());
            ps.setString(5, staff.getAddress());
            ps.setDouble(6, staff.getSalary());
            ps.setString(7, staff.getStaffId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteStaff(String staffId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM staff WHERE Staff_ID = ?";
        try (Connection con = DBController.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, staffId);
            return ps.executeUpdate() > 0;
        }
    }
}
