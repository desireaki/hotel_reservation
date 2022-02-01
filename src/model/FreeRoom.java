package model;

public class FreeRoom extends Room{
    /**
     * This constructor initializes the price of the room tp free and allow the user to initialize
     * the remaining fields.
     * @param roomNumber
     * @param roomType
     */
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    @Override
    public String toString() {
        return "FreeRoom{" +
                "roomNumber: " + getRoomNumber() + '\'' +
                ", roomPrice: free" +
                ", roomType: " + getRoomType() ;

    }

}
