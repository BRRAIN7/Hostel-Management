package Validation;

public class PaymentValidator {
    public boolean isValidPaymentAmount(double amount) {
        return amount > 0;
    }

    public boolean isValidPassword(String enteredPassword, String storedPassword) {
        return enteredPassword != null && enteredPassword.equals(storedPassword);
    }
}