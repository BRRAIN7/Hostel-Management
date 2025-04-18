package Entities;

import java.util.Date;

public class Paid {
    private String studentId;
    private String feeId;
    private Date paymentDate;

    // Constructors
    public Paid() {}

    public Paid(String studentId, String feeId, Date paymentDate) {
        this.studentId = studentId;
        this.feeId = feeId;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}