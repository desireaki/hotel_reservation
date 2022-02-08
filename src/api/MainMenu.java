package api;

import model.IRoom;
import model.Room;
import model.RoomType;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    public static void main(String[] args)throws Exception{
        mainMenu();
    }

    /**
     * This method prints the main menu
     */
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
    /**
     * This method prints the main menu
     */
    public static void mainMenu() throws Exception{
        printMenu();
        switch (getUserMenuChoice()) {
            case 1:
                System.out.println("Find and reserve a room.");
                Date[] reservationDates = parseDate();
                Collection<IRoom> availableRooms = HotelResource.findARoom(reservationDates[0], reservationDates[1]);
                for (IRoom k : availableRooms) {
                    System.out.println(k);
                }
                break;
            case 2:
                System.out.println("See customer's reservations");
                System.out.println("Customer's email:");
                Scanner scanner = new Scanner(System.in);
                String customerEmail = scanner.nextLine();
                if (HotelResource.getCustomer(customerEmail) != null) {
                    HotelResource.getCustomerReservations(customerEmail);
                }
                scanner.close();

                break;
            case 3:
                System.out.println("Creating an account.");
                System.out.println("Customer's email: ");

                Scanner scanner1 = new Scanner(System.in);
                String newCustomerEmail = scanner1.nextLine();
                if (HotelResource.getCustomer(newCustomerEmail) == null) {
                    System.out.println("Customer's firstName: ");
                    String newCustomerFirstName = scanner1.nextLine();
                    System.out.println("Customer's lastName: ");
                    String newCustomerLastName = scanner1.nextLine();
                    HotelResource.createACustomer(newCustomerEmail, newCustomerFirstName, newCustomerLastName);
                } else {
                    throw new IllegalArgumentException("An account already exist for the email provided!");
                }
                scanner1.close();
                break;
            case 4:
                adminMenu();
                break;
            default:
                System.exit(0);
        }
    }

    /**
     * This method prints the admin menu
     */
    public static void adminMenu() throws Exception{
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to main menu.");
        System.out.println("\nChoice: ");
        Scanner scanner3=new Scanner(System.in);
        int choice= scanner3.nextInt();
        while(choice<1 || choice>5) {
            System.out.println("Enter 5 to return to the main menu.");
            System.out.println("\nChoice: ");
            choice = scanner3.nextInt();
        }
        System.out.println("\n");
        switch(choice) {
            case 1:
                AdminResource.getAllCustomer();
                break;
            case 2:
                //Seeing all the rooms
                System.out.println(AdminResource.getAllRooms());
                break;
            case 3:
                AdminResource.displayAllReservations();
                break;
            case 4:
                System.out.println("Adding a new room.");
                List<Room> newRooms= new ArrayList<>();
                newRooms.add(createRoom());
                AdminResource.adRoom(newRooms);
                newRooms.clear();
                Scanner scn= new Scanner(System.in);



                int addMoreRooms=1;
                int choice2;
                while(addMoreRooms==1) {
                    //newRooms.add(createRoom());
                   // AdminResource.adRoom(newRooms);
                    System.out.println("Do you want to add more rooms?\n1. Yes\n2. Return to the main menu.");
                    choice2= scn.nextInt();
                    switch(choice2){
                        case 1:
                            newRooms.add(createRoom());
                            AdminResource.adRoom(newRooms);
                            System.out.println("Do you want add more rooms?\n1. Yes\n2. Return to the main menu.");
                            addMoreRooms=scn.nextInt();
                            break;
                        case 2:
                            System.out.println("Exiting window...");
                            addMoreRooms=0;
                            break;
                    }
                }
                System.out.println("Exiting window...");
                mainMenu();
                break;

            default:
                System.out.println("Backing to the main menu...");
                mainMenu();
        }
    }
    public static Room createRoom(){
        RoomType roomType=null;
        System.out.println("Enter new room info\n----------------------");
        System.out.println("Enter new room ID: ");
        Scanner scanner4= new Scanner(System.in);
        String newRoomNumber=scanner4.nextLine();
        System.out.println("Enter new room price");
        Double newRoomPrice=scanner4.nextDouble();
        System.out.println("Enter new room type. \nEnter 1 for single or 2 for double");
        int newRoomType= scanner4.nextInt();
        while(newRoomType<1 || newRoomType>2){
            System.out.println("Invalid room type entered! \nEnter 1 for single or 2 for double");
            newRoomType= scanner4.nextInt();
        }
        if (newRoomType==1) {
            roomType=RoomType.SINGLE;
        }
        if(newRoomType==2) {
            roomType=RoomType.DOUBLE;
        }
        return(new Room(newRoomNumber, newRoomPrice, roomType));
    }
}
