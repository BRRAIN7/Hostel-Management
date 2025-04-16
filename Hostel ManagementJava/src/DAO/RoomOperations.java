package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Services.DBController;

public class RoomOperations {
    public List<String> fetchAvailableRooms() {
        List<String> availableRooms = new ArrayList<>();
        DBController db = new DBController();

        try (Connection con = db.connect()) {
            String query = "SELECT Room_no FROM room WHERE Availability = 'Available' AND remaining_capacity > 0";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String roomNo = rs.getString("Room_no");
                availableRooms.add(roomNo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return availableRooms;
    }
}
