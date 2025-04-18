package Entities;

public class StudentComplaint implements Complaint {
    private String complaintId;
    private String date;
    private String description;
    private String status;
    private String resolution;

    public StudentComplaint(String complaintId, String date, String description, String status, String resolution) {
        this.complaintId = complaintId;
        this.date = date;
        this.description = description;
        this.status = status;
        this.resolution = resolution;
    }

    public String getComplaintId() { return complaintId; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getResolution() { return resolution; }
    public String getType() { return "Student"; }

    public void handleComplaint(String status, String resolution) {
        this.status = status;
        this.resolution = resolution;
    }
}
