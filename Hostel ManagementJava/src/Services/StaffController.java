package Services;

import DAO.StaffOperations;
import Entities.Staff;
import Validation.StaffValidation;

import javax.swing.*;
import java.sql.SQLException;

public class StaffController {

    public static boolean addStaff(Staff newStaff, JFrame parent) {
        // Validate the inputs using StaffValidation
        if (!StaffValidation.isValidName(newStaff.getName())) {
            JOptionPane.showMessageDialog(null, "Invalid Name. Please enter a valid name.");
            return false;
        }

        if (!StaffValidation.isValidRole(newStaff.getRole())) {
            JOptionPane.showMessageDialog(null, "Invalid Role. Please enter a valid role.");
            return false;
        }

        if (!StaffValidation.isValidContactNumber(newStaff.getContactNumber())) {
            JOptionPane.showMessageDialog(null, "Invalid Contact Number. Please enter a 10-digit number.");
            return false;
        }

//        if (!StaffValidation.isValidEmail(newStaff.getEmail())) {
//            JOptionPane.showMessageDialog(null, "Invalid Email. Please enter a valid email.");
//            return false;
//        }

        if (!StaffValidation.isValidAddress(newStaff.getAddress())) {
            JOptionPane.showMessageDialog(null, "Address cannot be empty.");
            return false;
        }

        if (!StaffValidation.isValidSalary(newStaff.getSalary())) {
            JOptionPane.showMessageDialog(null, "Invalid Salary. Please enter a valid salary.");
            return false;
        }

        // Call the DAO to save the staff to the database
        boolean isSaved = false;
        try {
            isSaved = StaffOperations.addStaffDao(newStaff);  // Add the staff to the database
            if (isSaved) {
                JOptionPane.showMessageDialog(null, "Staff added successfully!");
                parent.dispose();  // Close the current frame if the staff is added successfully
            } else {
                JOptionPane.showMessageDialog(null, "Error saving staff. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error occurred while adding the staff.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading database driver.");
        }

        return isSaved;  // Return the result of the database operation
    }
    public static Staff searchStaff(String staffId) {
        try {
            return new StaffOperations().getStaffById(staffId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateStaff(Staff staff) {
        try {
            return new StaffOperations().updateStaff(staff);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteStaff(String staffId) {
        try {
            return new StaffOperations().deleteStaff(staffId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
