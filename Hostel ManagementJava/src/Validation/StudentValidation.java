package Validation;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StudentValidation {
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z ]+");
    }
    public static boolean isValidID(String id) {
        return id != null && id.matches("\\d{12}"); // Check for exactly 12 digits
    }
    public static boolean isValidGender(String gender) {
        return gender != null && (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"));
    }
    public static boolean isValidDOB(String DOB) {
        if (DOB == null || !DOB.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        try {
            LocalDate dobDate = LocalDate.parse(DOB, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate today = LocalDate.now();
            Period age = Period.between(dobDate, today);
            return age.getYears() >= 18;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    public static boolean isValidDateOfAdmission(String date_of_admission) {
        return date_of_admission != null && date_of_admission.matches("\\d{4}-\\d{2}-\\d{2}"); // Check for 'yyyy-mm-dd' format
    }
    public static boolean isValidContactNumber(String contact_number) {
        return contact_number != null && contact_number.matches("\\d{10}"); // Check for exactly 10 digits
    }
    public static boolean isValidAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }

    // New method for password validation
    public static boolean isValidPassword(String password) {
        // Password must be at least 8 characters long
        // Must contain at least one digit, one uppercase letter, one lowercase letter
        // and one special character
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return password != null && password.matches(passwordRegex);
    }
}