package Entities;

public interface Complaint {
    String getComplaintId();
    String getDate();
    String getDescription();
    String getStatus();
    String getResolution();
    String getType(); // "Student" or "Staff"

    void handleComplaint(String status, String resolution); // e.g. for updating
}
