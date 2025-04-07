package Entities;
public class Student {
    // Fields (Attributes) of the Student class
    private String name;
    private String id;
    private String gender;
    private String dob; // Date of Birth
    private String admissionDate;
    private String contactNumber;
    private String address;

    // Constructor to initialize all fields
    public Student(String name, String id, String gender, String dob, String admissionDate, String contactNumber, String address) {
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.dob = dob;
        this.admissionDate = admissionDate;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    // Getters and Setters for each field
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Override toString method to display Student information easily
    @Override
    public String toString() {
        return "Student [name=" + name + ", id=" + id + ", gender=" + gender + ", dob=" + dob + ", admissionDate=" + admissionDate
                + ", contactNumber=" + contactNumber + ", address=" + address + "]";
    }

}

