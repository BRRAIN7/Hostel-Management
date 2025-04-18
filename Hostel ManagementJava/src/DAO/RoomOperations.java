package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Entities.Room;
import Services.DBController;

public class RoomOperations {

    // Existing function to fetch available rooms
    public List<String> fetchAvailableRooms() {
        List<String> availableRooms = new ArrayList<>();
        DBController db = new DBController();

        try (Connection con = db.connect()) {
            String query = "SELECT Room_no FROM room WHERE remaining_capacity > 0";
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

    // Existing function to fetch all rooms
    public static List<Room> getAllRooms() throws SQLException, ClassNotFoundException {
        List<Room> roomList = new ArrayList<>();

        String query = "SELECT * FROM room";

        try (Connection con = DBController.connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getString("Room_ID"));
                room.setRoomNo(rs.getInt("Room_no"));
                room.setRoomType(rs.getString("Room_type"));
                room.setCapacity(rs.getInt("Capacity"));
                room.setRemainingCapacity(rs.getInt("remaining_capacity"));

                roomList.add(room);
            }
        }

        return roomList;
    }

    // Existing function to insert a new room
    public static void insertRoom(Room room) throws SQLException, ClassNotFoundException {
        String getMaxIdQuery = "SELECT Room_ID FROM room ORDER BY Room_ID DESC LIMIT 1";
        String insertQuery = "INSERT INTO room (Room_ID, Room_no, Room_type, Capacity, remaining_capacity) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBController.connect();
             PreparedStatement insertStmt = con.prepareStatement(insertQuery);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(getMaxIdQuery)) {

            String newId = "R001"; // default
            if (rs.next()) {
                String lastId = rs.getString("Room_ID"); // e.g., "R005"
                int num = Integer.parseInt(lastId.substring(1)); // "005" -> 5
                newId = String.format("R%03d", num + 1);
            }

            insertStmt.setString(1, newId);
            insertStmt.setInt(2, room.getRoomNo());
            insertStmt.setString(3, room.getRoomType());
            insertStmt.setInt(4, room.getCapacity());
            insertStmt.setInt(5, room.getRemainingCapacity());

            insertStmt.executeUpdate();
        }
    }

    // New function to check if a room has available capacity
    public boolean isRoomAvailable(String roomId) {
        try (Connection con = DBController.connect()) {
            String query = "SELECT remaining_capacity FROM room WHERE Room_ID = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, roomId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("remaining_capacity") > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // New function to update the student's room in the stays table
    public boolean updateStudentRoom(String studentId, String newRoomId) {
        try (Connection con = DBController.connect()) {
            String query = "UPDATE stays SET Room_ID = ? WHERE Student_ID = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, newRoomId);
            stmt.setString(2, studentId);

            int result = stmt.executeUpdate();
            return result > 0;  // Return true if the update was successful
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // New function to update the room capacity
    public void updateRoomCapacity(String oldRoomId, String newRoomId) {
        try (Connection con = DBController.connect()) {
            // Decrease capacity for the new room
            String updateNewRoom = "UPDATE room SET remaining_capacity = remaining_capacity - 1 WHERE Room_ID = ?";
            PreparedStatement stmtNew = con.prepareStatement(updateNewRoom);
            stmtNew.setString(1, newRoomId);
            stmtNew.executeUpdate();

            // Increase capacity for the old room
            String updateOldRoom = "UPDATE room SET remaining_capacity = remaining_capacity + 1 WHERE Room_ID = ?";
            PreparedStatement stmtOld = con.prepareStatement(updateOldRoom);
            stmtOld.setString(1, oldRoomId);
            stmtOld.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
