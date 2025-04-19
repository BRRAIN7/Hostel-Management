package Services;

import DAO.OutpassOperations;
import Entities.Outpass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class OutpassController {

    public static boolean approveOutpass(String outpassId, String wardenId,String comment) {
        try {
            return new OutpassOperations().approveDao(outpassId, wardenId,comment);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean rejectOutpass(String outpassId, String wardenId,String comment) {
        try {
            return new OutpassOperations().rejectDao(outpassId, wardenId,comment);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
