package DAO;

import Entities.Student;
import Services.DBController;
import java.sql.*;

public class StudentOperations {
    public String studentVerify(String username, String password) throws SQLException, ClassNotFoundException {
        DBController db = new DBController();

        try (Connection con = db.connect()) { // Auto-closing connection

            // Check in student table
            String studentQuery = "SELECT * FROM student WHERE Student_ID = ? AND Student_pass = ?";
            try (PreparedStatement pstmt = con.prepareStatement(studentQuery)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return "student"; // User is a student
                    }
                }
            }

            // Check in warden table
            String wardenQuery = "SELECT * FROM warden WHERE Warden_ID = ? AND Warden_pass = ?";
            try (PreparedStatement pstmt = con.prepareStatement(wardenQuery)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return "warden"; // User is a warden
                    }
                }
            }
        }

        return "invalid"; // No match found
    }



}
