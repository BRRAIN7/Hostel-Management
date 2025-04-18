package Entities;

public class Staff extends User {
    private String staffId;
    private String role;
    private float salary;

    // Constructor
    public Staff(String name, String contactNumber, String address, String email, String staffId, String role, float salary) {
        super(name, contactNumber, address, email);  // Call the parent constructor to initialize common attributes
        this.staffId = staffId;
        this.role = role;
        this.salary = salary;
    }

    // Overloaded constructor if salary isn't provided, or role is optional
    public Staff(String name, String contactNumber, String address, String role) {
        super(name, contactNumber, address, null);  // Assuming email isn't mandatory here
        this.role = role;
        this.salary = 0.0f;  // Default salary if not provided
    }

    // Getters and setters
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    // Override toString method to display Staff-specific info
    @Override
    public String toString() {
        return super.toString() + ", Staff ID: " + staffId + ", Role: " + role + ", Salary: " + salary;
    }

    // Optional: Method to display staff role info
    public void displayStaffInfo() {
        System.out.println("Staff ID: " + staffId);
        System.out.println("Role: " + role);
        System.out.println("Salary: " + salary);
    }
}
