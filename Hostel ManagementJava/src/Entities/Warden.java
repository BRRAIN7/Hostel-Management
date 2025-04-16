package Entities;

public class Warden extends User{


    private String id;
    private String gender;
    private String wardenPass; // New field for student password
    private float salary;
    public String getId() {
        return id;
    }

    // Setter for id
    public void setId(String id) {
        this.id = id;
    }

    // Getter for gender
    public String getGender() {
        return gender;
    }

    // Setter for gender
    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getter for wardenPass
    public String getWardenPass() {
        return wardenPass;
    }

    // Setter for wardenPass
    public void setWardenPass(String wardenPass) {
        this.wardenPass = wardenPass;
    }

    // Getter for salary
    public float getSalary() {
        return salary;
    }

    // Setter for salary
    public void setSalary(float salary) {
        this.salary = salary;
    }
}
