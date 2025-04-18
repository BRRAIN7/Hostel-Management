package DAO;

import Entities.Student;
import Services.DBController;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class StudentOperations {

    // Verify student or warden credentials
    public String studentVerify(String username, String password) throws SQLException, ClassNotFoundException {
        DBController db = new DBController();

        try (Connection con = db.connect()) {
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

    // Search student by ID or Name
    public List<Student> searchStudent(String query) throws SQLException, ClassNotFoundException {
        List<Student> resultList = new ArrayList<>();
        try (Connection con = DBController.connect()) {
            String sql = """
            SELECT s.Student_ID, s.Name, s.Gender, s.Contact, s.Address, st.Room_ID
            FROM student s
            LEFT JOIN stays st ON s.Student_ID = st.Student_ID
            WHERE s.Student_ID LIKE ? OR s.Name LIKE ?
        """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + query + "%");
            ps.setString(2, "%" + query + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getString("Student_ID"));
                s.setName(rs.getString("Name"));
                s.setGender(rs.getString("Gender"));
                s.setContactNumber(rs.getString("Contact"));
                s.setAddress(rs.getString("Address"));
                s.setroomno(rs.getString("Room_ID")); // Room_ID from stays table

                resultList.add(s);
            }
        }

        return resultList;
    }

    // Update student details
    public static boolean updateStudent(Student student) throws SQLException, ClassNotFoundException {
        String sqlUpdateStudent = "UPDATE student SET Name = ?, Gender = ?, DOB = ?, Age = ?, Admission_date = ?, Contact = ?, Address = ?, Student_pass = ? WHERE Student_ID = ?";
        String sqlUpdateRoom = "UPDATE stays SET Room_ID = ? WHERE Student_ID = ?";
        String sqlUpdateRoomCapacity = "UPDATE room SET remaining_capacity = remaining_capacity + 1 WHERE Room_no = ?";
        String sqlDeductOldRoomCapacity = "UPDATE room SET remaining_capacity = remaining_capacity - 1 WHERE Room_no = ?";
        Connection conn = DBController.connect();

        conn.setAutoCommit(false); // Start transaction

        // Update student details
        try (PreparedStatement ps = conn.prepareStatement(sqlUpdateStudent)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getGender());
            ps.setDate(3, Date.valueOf(student.getDob()));  // Convert String to SQL Date
            ps.setDate(5, Date.valueOf(student.getAdmissionDate()));  // Convert String to SQL Date // Assuming DOB is in LocalDate format
            ps.setInt(4, student.getAge());

            ps.setString(6, student.getContactNumber());
            ps.setString(7, student.getAddress());
            ps.setString(8, student.getStudentPass());
            ps.setString(9, student.getId());
            ps.executeUpdate();
        }

        // Check if the room is changed and update room details
        if (student.getNewRoomNo() != null && !student.getNewRoomNo().equals(student.getroomno())) {
            // Deduct capacity from old room
            try (PreparedStatement ps = conn.prepareStatement(sqlDeductOldRoomCapacity)) {
                ps.setString(1, student.getroomno());
                ps.executeUpdate();
            }

            // Update room in stays table
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateRoom)) {
                ps.setString(1, student.getNewRoomNo());
                ps.setString(2, student.getId());
                ps.executeUpdate();
            }

            // Add capacity to the new room
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateRoomCapacity)) {
                ps.setString(1, student.getNewRoomNo());
                ps.executeUpdate();
            }
        }

        conn.commit(); // Commit transaction
        return true;

    }

    // Delete student and update room capacity
    public boolean deleteStudent(String studentId) throws SQLException, ClassNotFoundException {
        String sqlDeleteFromStays = "DELETE FROM stays WHERE Student_ID = ?";
        String sqlDeleteStudent = "DELETE FROM student WHERE Student_ID = ?";
        String sqlUpdateRoomCapacity = "UPDATE room SET remaining_capacity = remaining_capacity + 1 WHERE Room_ID = (SELECT Room_ID FROM stays WHERE Student_ID = ?)";


        Connection conn = DBController.connect();
        conn.setAutoCommit(false); // Start transaction

        try {
            // Update room capacity before deleting student
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateRoomCapacity)) {
                ps.setString(1, studentId);
                ps.executeUpdate();
            }

            // Delete from stays table
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteFromStays)) {
                ps.setString(1, studentId);
                ps.executeUpdate();
            }

            // Delete student from student table
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteStudent)) {
                ps.setString(1, studentId);
                ps.executeUpdate();
            }

            conn.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            conn.rollback(); // Rollback the transaction if any error occurs
            throw e; // re-throw the exception to be handled by the caller
        } finally {
            conn.setAutoCommit(true); // Restore auto-commit mode
            conn.close();
        }
    }


    // Fetch student by ID
    public Student getStudentById(String studentId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM student WHERE Student_ID = ?";
        try (Connection con = DBController.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("Student_ID"));
                student.setName(rs.getString("Name"));
                student.setGender(rs.getString("Gender"));
                student.setDob(rs.getDate("DOB").toLocalDate().toString());  // Convert Date to String
                student.setAdmissionDate(rs.getDate("Admission_date").toLocalDate().toString());  // Convert Date to String
                String dob = rs.getString("DOB");
                LocalDate birthDate = LocalDate.parse(dob);
                LocalDate currentDate = LocalDate.now();
                int age = Period.between(birthDate, currentDate).getYears();  // Calculate age from DOB

                student.setContactNumber(rs.getString("Contact"));
                student.setAddress(rs.getString("Address"));
                student.setStudentPass(rs.getString("Student_pass"));

                // Optionally, get room information for this student
                String roomQuery = "SELECT Room_ID FROM stays WHERE Student_ID = ?";
                try (PreparedStatement roomPs = con.prepareStatement(roomQuery)) {
                    roomPs.setString(1, studentId);
                    ResultSet roomRs = roomPs.executeQuery();
                    if (roomRs.next()) {
                        student.setroomno(roomRs.getString("Room_ID"));
                    }
                }

                return student;
            }
        }

        return null; // If student not found
    }
}
