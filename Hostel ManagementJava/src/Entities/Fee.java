package Entities;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fee {
    private String feeID;
    private double amount;
    private Date dueDate;
    private String status;
    private String paymentMethod;
    private Date paymentDate;
    private String studentID;

    public Fee(){

    }
    public Fee(String feeId, double amount, String status, String paymentMethod) {
        this.feeID = feeId;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public String getFeeID() {
        return feeID;
    }

    public void setFeeID(String feeID) {
        this.feeID = feeID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    // Method to format Date to String
    public String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        } else {
            return "N/A"; // If date is null, return "N/A"
        }
    }
}
