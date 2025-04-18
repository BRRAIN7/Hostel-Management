package Validation;



public class ComplaintValidation {

    public static boolean isValidStatus(String status) {
        return status != null && (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("In Progress"));
    }

    public static boolean isValidResolution(String resolution) {
        return resolution != null && !resolution.trim().isEmpty();
    }
    public static boolean isValidDescription(String description) {
        return description != null && description.trim().length() >= 10;
    }
}
