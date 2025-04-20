package Entities;

import java.sql.Date;
import java.sql.Time;

public class Outpass {
    private String outpassId;
    private Date dateOfIssue;
    private Time departureTime;
    private Time returnTime;
    private String purpose;
    private String status;
    private String applierId;
    private String studentId;// Tracks the applicant (e.g., student) associated with the outpass

    // Constructors
    public Outpass() {
    }

    public Outpass(String outpassId, Date dateOfIssue, Time departureTime, Time returnTime,
                   String purpose, String status) {
        this.outpassId = outpassId;
        this.dateOfIssue = dateOfIssue;
        this.departureTime = departureTime;
        this.returnTime = returnTime;
        this.purpose = purpose;
        this.status = status;
    }

    // Getters and Setters
    public String getOutpassId() {
        return outpassId;
    }

    public void setOutpassId(String outpassId) {
        this.outpassId = outpassId;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Time returnTime) {
        this.returnTime = returnTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplierId() {
        return applierId;
    }

    public void setApplierId(String applierId) {
        this.applierId = applierId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    @Override
    public String toString() {
        return "Outpass{" +
                "outpassId='" + outpassId + '\'' +
                ", dateOfIssue=" + dateOfIssue +
                ", departureTime=" + departureTime +
                ", returnTime=" + returnTime +
                ", purpose='" + purpose + '\'' +
                ", status='" + status + '\'' +
                ", applierId='" + applierId + '\'' +
                '}';
    }


}