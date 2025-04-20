package Services;

import DAO.OutpassOperations;
import Entities.Outpass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class OutpassController {
    private final OutpassOperations outpassDAO;
    public OutpassController() {

        outpassDAO = new OutpassOperations();
    }

    /**
     * Apply for a new outpass
     * @param departureTime Time of departure
     * @param returnTime Time of return
     * @param purpose Purpose of the outpass
     * @param studentId ID of the student applying
     * @return boolean indicating success or failure
     */
    public boolean applyForOutpass(String departureTime, String returnTime, String purpose, String studentId) {
        try {
            // Input validation
            if (departureTime == null || departureTime.trim().isEmpty() ||
                    returnTime == null || returnTime.trim().isEmpty() ||
                    purpose == null || purpose.trim().isEmpty()) {
                return false;
            }

            // Generate new outpass ID
            String outpassId = generateNewOutpassId();

            // Create outpass object
            Outpass outpass = new Outpass();
            outpass.setOutpassId(outpassId);
            outpass.setDateOfIssue(Date.valueOf(LocalDate.now()));
            outpass.setDepartureTime(Time.valueOf(departureTime));
            outpass.setReturnTime(Time.valueOf(returnTime));
            outpass.setPurpose(purpose);
            outpass.setStatus("Pending");
            outpass.setStudentId(studentId);

            // Save to database
            return outpassDAO.createOutpass(outpass, studentId);
        } catch (Exception e) {
            System.err.println("Error in applyForOutpass: " + e.getMessage());
            return false;
        }
    }

    /**
     * Approve an outpass
     * @param outpassId ID of the outpass
     * @param wardenId ID of the warden approving the outpass
     * @param comment Comment from the warden
     * @return boolean indicating success or failure
     */


    public static boolean approveOutpass(String outpassId, String wardenId,String comment) {
        try {
            return new OutpassOperations().approveDao(outpassId, wardenId,comment);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean rejectOutpass(String outpassId, String wardenId,String comment) {
        try {
            return new OutpassOperations().rejectDao(outpassId, wardenId,comment);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all outpasses for a student
     * @param studentId ID of the student
     * @return List of outpass objects
     */
    public List<Outpass> getStudentOutpasses(String studentId) {
        try {
            return outpassDAO.getOutpassesByStudent(studentId);
        } catch (Exception e) {
            System.err.println("Error getting student outpasses: " + e.getMessage());
            return null;
        }
    }

    /**
     * Generate a new outpass ID
     * @return String containing the new outpass ID
     */
    public String generateNewOutpassId() {
        try {
            return outpassDAO.generateOutpassId();
        } catch (Exception e) {
            System.err.println("Error generating outpass ID: " + e.getMessage());
            return null;
        }
    }
}
