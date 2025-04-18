package Services;

import DAO.StudentOperations;
import Entities.Student;
import Validation.StudentValidation;

import java.sql.SQLException;

public class StudentController {

    private final StudentOperations operations = new StudentOperations();
    private final StudentValidation validator = new StudentValidation();

    /**
     * Validates and updates the student entity based on the new input fields.
     * Only non-empty fields will be updated.
     *
     * @param existingStudent The current student object retrieved from DB.
     * @param name New name (optional).
     * @param gender New gender (optional).
     * @param contact New contact number (optional).
     * @param address New address (optional).
     * @param password New password (optional).
     * @param roomId New room ID (optional).
     * @return true if update succeeds, false otherwise.
     */
    public boolean updateStudent(Student existingStudent, String name, String gender, String contact,
                                 String address, String password, String roomId) {
        if (existingStudent == null) return false;

        // Validate only if field is non-empty
        if (!name.isEmpty() && !validator.isValidName(name)) return false;
        if (!contact.isEmpty() && !validator.isValidContactNumber(contact)) return false;

        // Apply updates
        if (!name.isEmpty()) existingStudent.setName(name);
        if (!gender.isEmpty()) existingStudent.setGender(gender);
        if (!contact.isEmpty()) existingStudent.setContactNumber(contact);
        if (!address.isEmpty()) existingStudent.setAddress(address);
        if (!password.isEmpty()) existingStudent.setStudentPass(password);
        if (roomId != null && !roomId.equals(existingStudent.getroomno())) {
            existingStudent.setroomno(roomId);
        }

        // Call DAO
        try {
            return StudentOperations.updateStudent(existingStudent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a student based on their ID.
     * @param studentId ID of the student to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteStudent(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) return false;
        try {
            return operations.deleteStudent(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves student details by ID.
     * @param studentId Student ID
     * @return Student object or null
     */
    public Student getStudentDetails(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) return null;
        try {
            return operations.getStudentById(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
