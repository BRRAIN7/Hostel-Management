package Services;
import DAO.ComplaintOperations;
import Entities.Complaint;
import Entities.StudentComplaint;
import Validation.ComplaintValidation;

import java.util.ArrayList;
import java.util.List;

public class ComplaintController {
    ComplaintOperations dao = new ComplaintOperations();

    public List<Complaint> getAllComplaints() throws Exception {
        return dao.getAllComplaints();
    }

    public String updateComplaint(String id, String status, String resolution) {
        try {
            if (!ComplaintValidation.isValidStatus(status)) {
                return "Invalid status!";
            }

            if (!ComplaintValidation.isValidResolution(resolution)) {
                return "Resolution cannot be empty!";
            }

            boolean success = dao.updateComplaint(id, status, resolution);
            return success ? "Complaint updated successfully!" : "Update failed!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while updating complaint.";
        }
    }
    public String fileComplaint(String studentId, String description) {
        try {
            if (!ComplaintValidation.isValidDescription(description)) {
                return "Invalid complaint description!";
            }

            ComplaintOperations dao = new ComplaintOperations();
            String newId = dao.generateNextComplaintId();
            String today = java.time.LocalDate.now().toString();

            StudentComplaint complaint = new StudentComplaint(newId, today, description, "Pending", null);

            boolean filed = dao.fileStudentComplaint(complaint, studentId);
            return filed ? "Complaint filed successfully!" : "Error while filing complaint.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception occurred while filing complaint.";
        }
    }

    public List<StudentComplaint> getComplaintsByStudent(String studentId) {
        try {
            ComplaintOperations dao = new ComplaintOperations();
            return dao.getComplaintsByStudent(studentId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
