package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    public static Customer getCustomer(String email){
        return (CustomerService.getCustomer(email));
    }
    public static void adRoom(List<IRoom> rooms){
        //Looping through the list and add the content to the list of all rooms.
        for(IRoom k: rooms){
            ReservationService.addRoom(k);
        }
    }
    public static Collection<IRoom> getAllRooms(){
        return (ReservationService.getListOfAllRooms());
    }
    public static Collection<Customer> getAllCustomer(){
        return( CustomerService.getAllCustomer());
    }
    public static void displayAllReservations(){
        ReservationService.printAllReservation();
    }

}
