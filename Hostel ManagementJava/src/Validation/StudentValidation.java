package Validation;

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
        return DOB != null && DOB.matches("\\d{4}-\\d{2}-\\d{2}"); // Check for 'yyyy-mm-dd' format
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
}
