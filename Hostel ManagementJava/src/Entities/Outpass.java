package Entities;

import java.sql.Date;
import java.sql.Time;

public class Outpass {
    private String outpassId;
    private Date dateOfIssue;
    private Time departureTime;
    private Time returnTime;
    private String purpose;
    private String applierId;

    // Getters and setters
    public String getOutpassId() { return outpassId; }
    public void setOutpassId(String outpassId) { this.outpassId = outpassId; }

    public Date getDateOfIssue() { return dateOfIssue; }
    public void setDateOfIssue(Date dateOfIssue) { this.dateOfIssue = dateOfIssue; }

    public Time getDepartureTime() { return departureTime; }
    public void setDepartureTime(Time departureTime) { this.departureTime = departureTime; }

    public Time getReturnTime() { return returnTime; }
    public void setReturnTime(Time returnTime) { this.returnTime = returnTime; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getApplierId() { return applierId; }
    public void setApplierId(String applierId) { this.applierId = applierId; }
}
