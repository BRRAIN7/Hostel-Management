package Services;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;

import DAO.StudentOperations;
import Entities.Student;
import Validation.StudentValidation;

public class StudentController {

    public static void addNewStudent(Student S) {
        // Validate the inputs
        if (!StudentValidation.isValidName(S.getName())) {
            JOptionPane.showMessageDialog(null, "Invalid Name. Please enter a valid name.");
            return;
        }
        if (!StudentValidation.isValidID(S.getId())) {
            JOptionPane.showMessageDialog(null, "Invalid ID. Please enter a valid 12-digit Aadhar ID.");
            return;
        }
        if (!StudentValidation.isValidGender(S.getGender())) {
            JOptionPane.showMessageDialog(null, "Invalid Gender. Please enter 'Male' or 'Female'.");
            return;
        }
        if (!StudentValidation.isValidDOB(S.getDob())) {
            JOptionPane.showMessageDialog(null, "Invalid Date of Birth. Please use the format 'yyyy-mm-dd'.");
            return;
        }
        if (!StudentValidation.isValidDateOfAdmission(S.getAdmissionDate())) {
            JOptionPane.showMessageDialog(null, "Invalid Admission Date. Please use the format 'yyyy-mm-dd'.");
            return;
        }
        if (!StudentValidation.isValidContactNumber(S.getContactNumber())) {
            JOptionPane.showMessageDialog(null, "Invalid Contact Number. Please enter a 10-digit number.");
            return;
        }
        if (!StudentValidation.isValidAddress(S.getAddress())) {
            JOptionPane.showMessageDialog(null, "Address cannot be empty.");
            return;
        }



        // Call the DAO to save the student to the database
        boolean isSaved = StudentOperations.addNewStudentDAO(S);

        if (isSaved) {
            JOptionPane.showMessageDialog(null, "Student added successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Error saving student.");
        }
    }
}
