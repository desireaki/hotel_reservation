package service;

import model.Customer;

import java.util.*;


public class CustomerService {
    private static List<Customer> customerList= new ArrayList<Customer>();//Stores all the customer list

    /**
     * This method takes the customer info and create a new customer's account.
     * @param email
     * @param firstName
     * @param lastName
     */
    public static void addCustomer(String email, String firstName, String lastName){
        customerList.add(new Customer(firstName, lastName, email));
    }
    /**
     * This method returns the Customer profile information using provided customer email
     * @param customerEmail
     * @return the customer profile info
     */
    public static Customer getCustomer(String customerEmail){
        //validate the email before looping through the list
        if(Customer.validateEmail(customerEmail)){
            for(Customer k: customerList){
                if(k.getEmail().equals(customerEmail)){
                    return k;
                }
            }
        }
        System.out.println("The email provided has no account associated!");
        return(null);
    }
    /**
     *This method returns the list of all customers
     * @return list of all the customer
     */
    public static Collection<Customer>getAllCustomer(){
        return customerList;
    }
}
