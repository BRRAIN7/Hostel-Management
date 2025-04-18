package Validation;

public class RoomValidation {

    public static boolean isRoomNumberValid(String roomNoStr) {
        try {
            int roomNo = Integer.parseInt(roomNoStr);
            return roomNo > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isRoomTypeValid(String type) {
        return type != null && (type.equalsIgnoreCase("Single") || type.equalsIgnoreCase("Double") || type.equalsIgnoreCase("Triple"));
    }

    public static boolean isCapacityValid(String capacityStr) {
        try {
            int cap = Integer.parseInt(capacityStr);
            return cap > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
