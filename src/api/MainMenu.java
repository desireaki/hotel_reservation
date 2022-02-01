package api;

import model.IRoom;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args)throws Exception{
        printMenu();
        switch (getUserMenuChoice()) {
            case 1:
                System.out.println("Find and reserve a room.");
                Date[] reservationDates=parseDate();
                Collection<IRoom> availableRooms=HotelResource.findARoom(reservationDates[0], reservationDates[1]);
                for(IRoom k: availableRooms){
                    System.out.println(k);
                }
                break;
            case 2:
                System.out.println("See customer's reservations");
                System.out.println("Customer's email:");
                Scanner scanner= new Scanner(System.in);
                String customerEmail= scanner.nextLine();
                if(HotelResource.getCustomer(customerEmail) != null){
                    HotelResource.getCustomerReservations(customerEmail);
                }
                else{
                    System.out.println("No reservation associated with the account found!");
                }
                scanner.close();

                break;
            case 3:
                System.out.println("Create an account");
                System.out.println("Customer's email: ");

                Scanner scanner1 = new Scanner(System.in);
                String newCustomerEmail = scanner1.nextLine();
                if(HotelResource.getCustomer(newCustomerEmail) == null) {
                    System.out.println("Customer's firstName: ");
                    String newCustomerFirstName = scanner1.nextLine();
                    System.out.println("Customer's lastName: ");
                    String newCustomerLastName = scanner1.nextLine();
                    HotelResource.createACustomer(newCustomerEmail, newCustomerFirstName, newCustomerLastName);
                }
                else{
                    throw new IllegalArgumentException("An account already exist for the email provided!");
                }
                break;
            case 4:
                System.out.println("Admin window");
                break;
            case 5:
                System.out.println("Exiting...");
        }
    }
    public static void printMenu(){
        System.out.println("Choose option from the list below:" +
                "\n     1. Find and Reserve a room" +
                "\n     2. See my reservation" +
                "\n     3. Create an account" +
                "\n     4. Admin window" +
                "\n     5. Exit" +
                "\n\nEnter choice: ");
    }

    /**
     * This method get the input from the user and validates it
     * @return The user choice from the menu
     */
    public static int getUserMenuChoice(){
        Scanner scanner= new Scanner(System.in);
        String initUserChoice= scanner.nextLine();
        int userChoice=0;
        try {
            userChoice = Integer.parseInt(initUserChoice);
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid input! Numerical input required.");
        }
        //validate user input
        while(userChoice<1 || userChoice>5){
            System.out.println("Enter a valid option (1-5)!");
            userChoice=scanner.nextInt();
        }
       return(userChoice);
    }

    /**
     * This method get the user input for check in and check out as Strings, validates them
     * and returns them as Date data type
     * @return  Check in and Check out dates within an array
     * @throws Exception Invalid input date format
     */
    public static Date[] parseDate() throws Exception{
        Date[] reservationDates= new Date[2];

        System.out.println("Enter check in date (DD/MM/YYYY): ");
        Scanner scanner= new Scanner(System.in);
        String checkInDate=scanner.nextLine();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        reservationDates[0]= dateFormat.parse(checkInDate);

        System.out.println("Enter check out date (DD/MM/YYYY): ");
        String checkOutDate=scanner.nextLine();
        reservationDates[1]= dateFormat.parse(checkOutDate);

        return(reservationDates);
    }
}
