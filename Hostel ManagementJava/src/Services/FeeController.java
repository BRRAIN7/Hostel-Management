package Services;

import DAO.FeeOperations;
import Validation.PaymentValidator;

import javax.swing.*;
import java.sql.SQLException;

public class FeeController {
    private static final PaymentValidator validator = new PaymentValidator();

    public static void processPayment(float amt, String password, String studentId) {
        // Validate amount
        if (!validator.isValidPaymentAmount(amt)) {
            showMessage("Invalid amount. Please enter a valid fee amount.");
            return;
        }

        // Validate password
        if (!validator.isValidPassword(password, Session.getUserPassword())) {
            showMessage("Invalid password. Please enter the correct password.");
            return;
        }

        try {
            // Fetch pending fee ID
            String feeId = FeeOperations.getFeeIdForStudent(studentId);

            if (feeId == null) {
                showMessage("No pending fee found for student ID: " + studentId);
                return;
            }

            // Update fee status
            boolean feeUpdated = FeeOperations.updateFeeStatus(feeId);
            boolean paymentUpdated = FeeOperations.updatePaymentDate(studentId, feeId);

            if (feeUpdated && paymentUpdated) {
                showMessage("Payment of ₹" + amt + " successful!");
            } else {
                showMessage("Failed to update payment records. Please try again.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            showMessage("An error occurred while processing the payment: " + e.getMessage());
        }
    }

    private static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}