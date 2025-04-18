package Validation;

public class StaffValidation {

    // Validates the name of the staff (only alphabetic characters and spaces allowed)
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z ]+");
    }

    // Validates the staff ID (e.g., ST001, ST002, etc.)
    public static boolean isValidStaffID(String staffId) {
        return staffId != null && staffId.matches("ST\\d{3}"); // e.g., ST001, ST100, etc.
    }

    // Validates the role of the staff (only alphabetic characters and spaces allowed)
    public static boolean isValidRole(String role) {
        return role != null && !role.trim().isEmpty() && role.matches("[a-zA-Z ]+");
    }

    // Validates the contact number (must be exactly 10 digits)
    public static boolean isValidContactNumber(String contactNumber) {
        return contactNumber != null && contactNumber.matches("\\d{10}"); // Exactly 10 digits
    }

    // Validates the email (simple email format)
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"); // Updated regex
    }


    // Validates the address (non-empty string)
    public static boolean isValidAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }

    // Validates the salary (must be a positive number)
    public static boolean isValidSalary(float salary) {
        return salary > 0; // Salary should be positive
    }
}
