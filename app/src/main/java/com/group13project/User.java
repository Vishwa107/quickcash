package com.group13project;
/**
 * The User class represents a user of the application. Each User object contains
 * information such as the user's first and last name, email address, and phone number.
 */
public class User {
    public String firstName, lastName, emailAddress, phoneNumber;


    /**
     * Empty constructor for the User class.
     */
    public  User(){}


    /**
     * Constructor for the User class that takes in the user's first and last name, email address, and phone number.
     *
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param emailAddress the user's email address
     * @param phoneNumber the user's phone number
     */
    public User(String firstName, String lastName, String emailAddress, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
}
