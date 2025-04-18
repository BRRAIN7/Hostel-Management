package DAO;

import Entities.Student;
import Services.DBController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WardenOperations {
    public static boolean addNewStudentDAO(Student S) throws SQLException, ClassNotFoundException {
        DBController db = new DBController();
        Connection con = db.connect();
        boolean isSuccessful = false;

        // Begin Transaction: To ensure atomicity (either both operations succeed or fail)
        con.setAutoCommit(false); // Disable auto-commit for transactional operations.


        // Step 1: Insert student into the 'student' table
        String str = "INSERT INTO student(Name, Student_ID, Gender, DOB, Age, Admission_date, Contact, Address, Student_pass) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(str);

        ps.setString(1, S.getName());
        ps.setString(2, S.getId());
        ps.setString(3, S.getGender());
        ps.setDate(4, java.sql.Date.valueOf(S.getDob()));
        ps.setInt(5, S.getAge());
        ps.setDate(6, java.sql.Date.valueOf(S.getAdmissionDate()));
        ps.setString(7, S.getContactNumber());
        ps.setString(8, S.getAddress());
        ps.setString(9, S.getStudentPass());

        int rowsInserted = ps.executeUpdate();

        // Step 2: If student record is inserted successfully, proceed with the stays table
        if (rowsInserted > 0) {
            // Fetch Room_ID based on the Room_no
            String fetchRoomIdQuery = "SELECT Room_ID FROM room WHERE Room_no = ? LIMIT 1";
            PreparedStatement fetchRoomIdStmt = con.prepareStatement(fetchRoomIdQuery);
            fetchRoomIdStmt.setString(1, S.getroomno());  // Room_no from Student object
            ResultSet rs = fetchRoomIdStmt.executeQuery();

            if (rs.next()) {
                String roomId = rs.getString("Room_ID");

                // Step 3: Insert into the 'stays' table
                String str1 = "INSERT INTO stays(Student_ID, Room_ID, Check_in_date) VALUES(?, ?, ?)";
                PreparedStatement ps1 = con.prepareStatement(str1);

                ps1.setString(1, S.getId());  // Student ID from the Student object
                ps1.setString(2, roomId);     // Room_ID from the result set
                ps1.setDate(3, java.sql.Date.valueOf(S.getAdmissionDate()));  // Check-in Date (you may choose a different one)

                int rowsInsertedStays = ps1.executeUpdate();

                // Step 4: Update the room availability and remaining capacity
                if (rowsInsertedStays > 0) {
                    String updateRoomQuery = "UPDATE room SET remaining_capacity = remaining_capacity - 1 " +
                            "WHERE Room_ID = ? AND remaining_capacity > 0";

                    PreparedStatement updateRoomStmt = con.prepareStatement(updateRoomQuery);
                    updateRoomStmt.setString(1, roomId);
                    int rowsUpdatedRoom = updateRoomStmt.executeUpdate();

                    // If all updates are successful, commit the transaction
                    if (rowsUpdatedRoom > 0) {
                        con.commit();
                        isSuccessful = true;
                    } else {
                        // If room update fails, roll back
                        con.rollback();
                    }
                } else {
                    // If stays insertion fails, roll back
                    con.rollback();
                }
            } else {
                // Room not found, this should not happen as the UI only shows available rooms
//                    JOptionPane.showMessageDialog(null, "Room not found.");
//                    con.rollback();
            }
        } else {
            // If student insertion fails, roll back
            con.rollback();
        }


        if (con != null) {
            con.setAutoCommit(true);  // Restore default auto-commit behavior
            con.close();
        }


        return isSuccessful;
    }


}
