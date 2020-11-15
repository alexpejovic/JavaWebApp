package Modules.UI;

import Modules.Controllers.AccountCreator;

import java.util.ArrayList;
import java.util.Scanner;

public class CreateAccountUI {

    private AccountCreator accountCreator;

    private Scanner input = new Scanner(System.in);

    /** Constructor for CreateAccountUI
     *
     * @param accountCreator Controller which handles user inputs regarding logging-in
     */
    CreateAccountUI(AccountCreator accountCreator) {
        this.accountCreator = accountCreator;
    }


    /** Prompts user to create account within the console.
     *
     * @return True if account creation is successful, false otherwise
     */
    public boolean run(){

        String username;
        String password;

        System.out.println("---Create Account---\n");

        System.out.println("\nEnter Username");
        username = input.nextLine();
        System.out.println("\nEnter Password");
        password = input.nextLine();

        System.out.println("Enter, \n" +
                "1, if you wish to create an Attendee account\n" +
                "2, if you wish to create a Speaker account\n" +
                "3, if you wish to create an Organizer account\n");

        String num = input.nextLine();

        while(!createAccount(num, username, password)){
            num = input.nextLine();
        }

        return true;
    }

    private boolean createAccount(String num, String username, String password){

        switch (num) {
            case "1":
                accountCreator.createAttendeeAccount(username, password, new ArrayList<>());
                return true;
            case "2":
                accountCreator.createSpeakerAccount(username, password, new ArrayList<>());
                return true;
            case "3":
                accountCreator.createOrganizerAccount(username, password);
                return true;
            default:
                System.out.println("Invalid Input, try again");
                return false;
        }
    }
}