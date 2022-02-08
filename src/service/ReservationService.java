package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static List<IRoom> listOfAllRooms= new ArrayList<>();
    private static  List<Reservation> reservationList= new ArrayList<>();

    /**
     * This method adds a new room to the room list
     * @param room The new created room
     */
    public static void addRoom(IRoom room){
        listOfAllRooms.add(room);
    }

    /**
     * This method returns the room info from the room number
     * @param roomId The ID for the room
     * @return room profile
     */
    public static IRoom getARoom(String roomId){
        for(IRoom k: listOfAllRooms) {
            if (k.getRoomNumber().equals(roomId)) {
                return k;
            }
        }
        throw new NoSuchElementException("No room associated with the provided room ID!");
    }

    /**
     * This method create a new room reservation
     * @param customer Customer info
     * @param room The room desired
     * @param checkInDate Check in date
     * @param checkOutDate Check out date
     * @return a new reservation
     */
    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation newReservation=null;
        //Call the validateReservation() method to validate the input info
        if (validateReservation(room, checkInDate, checkOutDate) ) {
            newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
            reservationList.add(newReservation);
        }
        return newReservation;
    }

    /**
     * This method allows the user to get a list of available rooms
     * @param checkInDate Check in Date
     * @param checkOutDate Check out date
     * @return list of available rooms.
     */
    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        List<IRoom> listOfAvailableRooms= listOfAllRooms;
        Calendar calendar = Calendar.getInstance();
        Date today=calendar.getTime();// today's date
        if(checkInDate.before(today) || checkOutDate.before(today)){
            throw new IllegalArgumentException("Past date input!");
        }
        if(checkOutDate.before(checkInDate)){
            throw new IllegalArgumentException("Invalid check out date!");
        }
        //Loop through the list of all rooms remove the ones that are booked within the boundaries
        for(Reservation k: reservationList){
            if(k.getCheckInDate().equals(checkInDate) || (k.getCheckInDate().after(checkInDate) && k.getCheckInDate().before(checkOutDate))){
                listOfAvailableRooms.remove(k);//remove reserved date.
            }
        }
        return(listOfAvailableRooms);
    }

    /**
     * This method allows the user to get all the customer's reservation
     * @param customer Customer's info
     * @return list of all reservations
     */
    public static Collection<Reservation>getCustomerReservation(Customer customer){
        List<Reservation> customersReservation= new ArrayList<>();
        //Store the customer's reservations
        //Loop through all the reservation list and catches every reservation with the provided customer
        for(Reservation k: reservationList) {
            if(k.getCustomer().equals(customer) ){
                customersReservation.add(k);
            }
        }
        return customersReservation;
    }

    /**
     *This method returns the list of all rooms ata the hotel
     * @return list of all rooms
     */

    public static List<IRoom> getListOfAllRooms() {
        return listOfAllRooms;
    }

    /**
     * This method prints out all the reservation
     */
    public static void printAllReservation(){
        int i=1;
        for(Reservation k: reservationList){
            System.out.println("Reservation #"+ i +":"+ k);
            i++;
        }
    }

    /**
     * This method validates all the  user input when creating a new reservation
     * @param room Room info
     * @param checkInDate Check in date
     * @param checkOutDate Check out date
     * @return true or false
     */
    private static boolean validateReservation(IRoom room, Date checkInDate, Date checkOutDate){
        for(Reservation k: reservationList){
            //if a reservation with the same room is found on the reservation list
            if(k.getRoom().equals(room)){
                //check if the reservation's check in date is equal to the intended check in date
                if(k.getCheckInDate().equals(checkInDate)) {
                    return false;
                }
                //check if the intended check in date is before the reservation's check in date
                else if(checkInDate.before(k.getCheckInDate())){
                    //check if the intended check out date falls after the reservation's check in date
                    if(checkOutDate.after(k.getCheckInDate())){
                        return false;
                    }
                }
                //also return false when the check in date is after the reservation's check in date and is less than its checkout date
                else if(k.getCheckInDate().after(checkInDate) && (k.getCheckInDate().before(checkOutDate) || k.getCheckInDate().equals(checkOutDate)) ){
                    return false;
                }
            }

        }
        return true;
    }

}
