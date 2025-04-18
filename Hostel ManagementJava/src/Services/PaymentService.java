package Services;

import DAO.FeeOperations;
import DAO.PaidDAO;
import Entities.Fee;
import Validation.PaymentValidator;

import java.sql.SQLException;
import java.util.List;

public class PaymentService {
    private final FeeOperations feeDAO;
    private final PaidDAO paidDAO;
    private final PaymentValidator validator;

    public PaymentService() {
        this.feeDAO = new FeeOperations();
        this.paidDAO = new PaidDAO();
        this.validator = new PaymentValidator();
    }

    public double calculatePendingAmount(String studentId) throws SQLException, ClassNotFoundException {
        double totalPending = 0.0;
        List<Fee> unpaidFees = feeDAO.getUnpaidFeesByStudentId(studentId);

        for (Fee fee : unpaidFees) {
            if ("Unpaid".equals(fee.getStatus()) || "Partially Paid".equals(fee.getStatus())) {
                totalPending += fee.getAmount();
            }
        }

        return totalPending;
    }

    public boolean processPayment(String studentId, double paymentAmount, String paymentMethod)
            throws SQLException, ClassNotFoundException {
        if (!validator.isValidPaymentAmount(paymentAmount)) {
            throw new IllegalArgumentException("Invalid payment amount");
        }

        List<String> feeIds = paidDAO.getStudentFeeIds(studentId);

        for (String feeId : feeIds) {
            Fee fee = feeDAO.getFeeById(feeId);

            if (fee != null && ("Unpaid".equals(fee.getStatus()) || "Partially Paid".equals(fee.getStatus()))) {
                if (paymentAmount > fee.getAmount()) {
                    throw new IllegalArgumentException("Payment exceeds fee amount");
                }

                String newStatus = paymentAmount == fee.getAmount() ? "Paid" : "Partially Paid";
                feeDAO.updateFeeStatus(feeId, newStatus, paymentMethod);
                paidDAO.updatePaymentDate(studentId, feeId);
                break; // Only process one fee at a time
            }
        }

        return true;
    }
}