package Entities;

public class User {
    protected String name;
    protected String contactNumber;
    protected String address;
    protected String email;  // Added email field

    // Constructor
    public User(String name, String contactNumber, String address, String email) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.address = address;
        this.email = email;
    }

    // Overloaded constructor (without email, for cases where email might not be required)
    public User(String name, String contactNumber, String address) {
        this(name, contactNumber, address, null);  // Email set to null if not passed
    }
    public User(){

    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Optional: Display user details
    @Override
    public String toString() {
        return "Name: " + name + ", Contact: " + contactNumber + ", Address: " + address + ", Email: " + email;
    }
}
