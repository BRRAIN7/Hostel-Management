package DAO;

import Entities.Student;
import Services.DBController;

import java.sql.*;

public class WardenOperations {
    public static boolean addNewStudentDAO(Student S) throws SQLException, ClassNotFoundException {
        DBController db = new DBController();
        Connection con = db.connect();
        boolean isSuccessful = false;

        con.setAutoCommit(false); // Begin Transaction

        try {
            // Step 1: Insert into 'student' table
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

            if (rowsInserted > 0) {
                // Step 2: Get Room_ID for given Room_no
                String fetchRoomIdQuery = "SELECT Room_ID FROM room WHERE Room_no = ? LIMIT 1";
                PreparedStatement fetchRoomIdStmt = con.prepareStatement(fetchRoomIdQuery);
                fetchRoomIdStmt.setString(1, S.getroomno());
                ResultSet rs = fetchRoomIdStmt.executeQuery();

                if (rs.next()) {
                    String roomId = rs.getString("Room_ID");

                    // Step 3: Insert into stays
                    String str1 = "INSERT INTO stays(Student_ID, Room_ID, Check_in_date) VALUES(?, ?, ?)";
                    PreparedStatement ps1 = con.prepareStatement(str1);
                    ps1.setString(1, S.getId());
                    ps1.setString(2, roomId);
                    ps1.setDate(3, java.sql.Date.valueOf(S.getAdmissionDate()));
                    int rowsInsertedStays = ps1.executeUpdate();

                    if (rowsInsertedStays > 0) {
                        // Step 4: Update room capacity
                        String updateRoomQuery = "UPDATE room SET remaining_capacity = remaining_capacity - 1 WHERE Room_ID = ? AND remaining_capacity > 0";
                        PreparedStatement updateRoomStmt = con.prepareStatement(updateRoomQuery);
                        updateRoomStmt.setString(1, roomId);
                        int rowsUpdatedRoom = updateRoomStmt.executeUpdate();

                        // Step 5: Insert into fee
                        String feeId = "F" + S.getId().substring(1); // Assuming F006 for S006
                        String insertFeeQuery = "INSERT INTO fee(Fee_ID, Amount, Due_date, Status, Payment_method) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement insertFeeStmt = con.prepareStatement(insertFeeQuery);
                        insertFeeStmt.setString(1, feeId);
                        insertFeeStmt.setDouble(2, 5000.0); // Default amount
                        insertFeeStmt.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now().plusMonths(1))); // Due in a month
                        insertFeeStmt.setString(4, "Unpaid"); // Initial status
                        insertFeeStmt.setString(5, null); // No method yet
                        int feeInserted = insertFeeStmt.executeUpdate();

                        // Step 6: Insert into pays (associating student with fee)
                        String paysQuery = "INSERT INTO pays(Student_ID, Fee_ID) VALUES (?, ?)";
                        PreparedStatement paysStmt = con.prepareStatement(paysQuery);
                        paysStmt.setString(1, S.getId());
                        paysStmt.setString(2, feeId);
                        int paysInserted = paysStmt.executeUpdate();

//                        // Step 7: Insert into files table (for complaint linking)
//                        String complaintId = generateNewComplaintId(con); // e.g., C007
//                        String fileComplaintQuery = "INSERT INTO files(Student_ID, Complaint_ID, Filing_date) VALUES (?, ?, CURDATE())";
//                        PreparedStatement fileStmt = con.prepareStatement(fileComplaintQuery);
//                        fileStmt.setString(1, S.getId());
//                        fileStmt.setString(2, complaintId);
//                        int fileInserted = fileStmt.executeUpdate();

                        // Final check before commit
                        System.out.println(rowsUpdatedRoom+" "+feeInserted+" "+paysInserted);
                        if (rowsUpdatedRoom > 0 && feeInserted > 0 && paysInserted > 0 ) {
                            con.commit();
                            isSuccessful = true;
                        } else {
                            con.rollback();
                        }
                    } else {
                        con.rollback();
                    }
                } else {
                    con.rollback();
                }
            } else {
                con.rollback();
            }
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }

        return isSuccessful;
    }
    private static String generateNewComplaintId(Connection con) throws SQLException {
        String getMaxIdQuery = "SELECT MAX(CAST(SUBSTRING(Complaint_ID, 2) AS UNSIGNED)) AS max_id FROM files";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(getMaxIdQuery);

        int nextId = 1;
        if (rs.next()) {
            nextId = rs.getInt("max_id") + 1;
        }

        return "C" + String.format("%03d", nextId); // e.g., C007
    }



}
