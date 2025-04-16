package DAO;

import Entities.Student;
import Services.DBController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WardenOperations {
    public static boolean addNewStudentDAO(Student S) throws SQLException, ClassNotFoundException {
        DBController db = new DBController();
        Connection con = db.connect();

        String str = "INSERT INTO student(Name, Student_ID, Gender, DOB, Age, Admission_date, Contact, Address, Student_pass) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(str);

        ps.setString(1, S.getName());
        ps.setString(2, S.getId());
        ps.setString(3, S.getGender());
        ps.setDate(4, java.sql.Date.valueOf(S.getDob()));
        ps.setInt(5, S.getAge());
        ps.setDate(6, java.sql.Date.valueOf(S.getAdmissionDate()));
        ps.setString(7, S.getContactNumber());
        ps.setString(8, S.getAddress());
        ps.setString(9, S.getStudentPass());

        int rowsInserted = ps.executeUpdate();


        String str1 = "Insert into stays(Student_ID,Room_ID,Check_in_date) values(?,?,?)";
        ps = con.prepareStatement(str1);
        ps.setString(1,S.getId());
        ps.setString(2,S.getroomno() );
        ps.setDate(3,java.sql.Date.valueOf(S.getAdmissionDate()));
        int rowsInserted1=ps.executeUpdate();



        return rowsInserted > 0 && rowsInserted1>0;
    }

}
