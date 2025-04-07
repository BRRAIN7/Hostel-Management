package DAO;

import Entities.Student;
import Services.DBController;
import java.sql.*;

public class StudentOperations {
    public String studentVerify(String username, String password) throws SQLException, ClassNotFoundException {
        DBController db = new DBController();

        try (Connection con = db.connect()) { // Auto-closing connection

            // Check in student table
            String studentQuery = "SELECT * FROM student WHERE Name = ? AND Student_pass = ?";
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
            String wardenQuery = "SELECT * FROM warden WHERE Warden_name = ? AND Warden_pass = ?";
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

    public static boolean addNewStudentDAO(Student S) throws SQLException,ClassNotFoundException{
        DBController db = new DBController();
        Connection con=db.connect();

        String str= "Insert into student(Name,student_id,Student_Gender,DOB,Admission_date,Contact,Address) values (?,?,?,?,?,?,?)";
        PreparedStatement ps=con.prepareStatement(str);
        ps.setString(1,S.getName());
        ps.setString(2,S.getId())

    }
}
