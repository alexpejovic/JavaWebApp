package Modules.UI;

import Modules.Controllers.AccountCreator;
import Modules.Exceptions.NonUniqueUsernameException;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class CreateAccountUI {

    private AccountCreator accountCreator;

    private Scanner input = new Scanner(System.in);

    /** Constructor for CreateAccountUI
     *
     * @param accountCreator Controller which handles user inputs regarding logging-in
     */
    public CreateAccountUI(AccountCreator accountCreator) {
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
        try{
            switch (num) {
                case "1":
                    return accountCreator.createAttendeeAccount(username, password, new ArrayList<>());
                case "2":
                    return accountCreator.createSpeakerAccount(username, password, new ArrayList<>());
                case "3":
                    return accountCreator.createOrganizerAccount(username, password);
                default:
                    System.out.println("Invalid Input, try again");
                    return false;
            }
        }catch(NonUniqueUsernameException e){
            System.out.println("Sorry, someone else already has that username, please re-run the program");
            return false;
        }

    }
}
