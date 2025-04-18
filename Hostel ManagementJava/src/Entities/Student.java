package Entities;

import java.time.LocalDate;
import java.time.Period;

public class Student extends User {
    private String id;
    private String gender;
    private String dob;
    private String admissionDate;
    private int age; // Field for age
    private String studentPass; // Student password
    private String roomno; // Current room number
    private String newRoomNo; // New room number for room change

    // Constructor using super() for shared fields
    public Student(String name, String id, String gender, String dob, String admissionDate,
                   String contactNumber, String address, String studentPass, String roomno) {
        super(name, contactNumber, address); // Call the parent constructor
        this.id = id;
        this.gender = gender;
        this.dob = dob;
        this.admissionDate = admissionDate;
        this.age = calculateAge(dob);  // Calculate age from DOB
        this.studentPass = studentPass;
        this.roomno = roomno; // Set initial room number
    }

    // Default constructor
    public Student() {}

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for roomno (current room)
    public String getroomno() {
        return roomno;
    }

    public void setroomno(String roomno) {
        this.roomno = roomno;
    }

    // Getter and Setter for newRoomNo (room change)
    public String getNewRoomNo() {
        return newRoomNo;
    }

    public void setNewRoomNo(String newRoomNo) {
        this.newRoomNo = newRoomNo;
    }

    // Getter and Setter for gender
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getter and Setter for dob
    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
        this.age = calculateAge(dob);  // Recalculate age if DOB changes
    }

    // Getter and Setter for admissionDate
    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    // Getter and Setter for studentPass
    public String getStudentPass() {
        return studentPass;
    }

    public void setStudentPass(String studentPass) {
        this.studentPass = studentPass;
    }

    // Getter for age (age is calculated dynamically from DOB)
    public int getAge() {
        return age;
    }

    // Method to calculate age from DOB
    private int calculateAge(String dob) {
        LocalDate birthDate = LocalDate.parse(dob);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();  // Calculate the years
    }

    // Override toString method
    @Override
    public String toString() {
        return "Student [name=" + name + ", id=" + id + ", gender=" + gender + ", dob=" + dob
                + ", admissionDate=" + admissionDate + ", age=" + age + ", contactNumber=" + contactNumber
                + ", address=" + address + ", studentPass=" + studentPass + "]";
    }
}
