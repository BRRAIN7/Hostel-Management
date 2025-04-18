package Services;

import DAO.RoomOperations;
import Entities.Room;
import Validation.RoomValidation;

import javax.swing.*;

public class RoomController {

    public static void addRoom(String roomNoStr, String type, String capacityStr) {
        if (!RoomValidation.isRoomNumberValid(roomNoStr)) {
            JOptionPane.showMessageDialog(null, "Room number must be a positive integer.");
            return;
        }

        if (!RoomValidation.isRoomTypeValid(type)) {
            JOptionPane.showMessageDialog(null, "Room type must be Single, Double, or Triple.");
            return;
        }

        if (!RoomValidation.isCapacityValid(capacityStr)) {
            JOptionPane.showMessageDialog(null, "Capacity must be a positive integer.");
            return;
        }

        // All inputs valid, create Room object
        Room room = new Room();
        room.setRoomNo(Integer.parseInt(roomNoStr));
        room.setRoomType(type);
        room.setCapacity(Integer.parseInt(capacityStr));
        room.setRemainingCapacity(Integer.parseInt(capacityStr)); // Initially full capacity

        try {
            RoomOperations.insertRoom(room);
            JOptionPane.showMessageDialog(null, "Room added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to add room: " + e.getMessage());
        }
    }
}
