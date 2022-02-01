package model;

import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    /**
     * This is the main constructor for the customer class
     * @param firstName
     * @param lastName
     * @param email
     */
    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        if(validateEmail(email)){
            this.email=email;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    /**
     * This method takes the customer's email and validate its format
     * @param email
     * @return true or false
     */
    public static boolean validateEmail(String email){
        Pattern pattern=Pattern.compile("^(.+)@(.+).(.+)$");
        if(pattern.matcher(email).matches()) {
            return true;
        }
        else{
            throw new IllegalArgumentException("Invalid email address input!");
        }
    }

    @Override
    public String toString() {
        return  "First Name: " +'\''+ firstName + '\'' +
                "\nLast Name: " +'\''+ lastName + '\'' +
                "\nEmail: " + '\''+ email + '\'';

    }
}
