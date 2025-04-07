package Services;
import DAO.StudentOperations;
import UserInterface.LoginUI;
import UserInterface.StudentUI;
import UserInterface.WardenUI;

import javax.swing.*;
import java.sql.SQLException;

public class LoginController {
    private JFrame loginUiFrame;

    // Constructor that accepts JFrame
    public LoginController(JFrame j){
        this.loginUiFrame = j;
    }

    public boolean authenticate(String username, String password){
        StudentOperations SO = new StudentOperations();
        try {
            String result = SO.studentVerify(username, password);
            if (result.equals("student")) {
                System.out.println("Student Login Successful !");
                StudentUI SU = new StudentUI();
                loginUiFrame.dispose();  // This will now work because loginUiFrame is properly set
                SU.showStudentUi();
            } else if (result.equals("warden")) {
                System.out.println("Warden Login successful!!");
                WardenUI W= new WardenUI();

                loginUiFrame.dispose();
                W.showWardenUi();
            } else {
                System.out.println("Invalid login details");
                return false;

            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return true;
    }
}

