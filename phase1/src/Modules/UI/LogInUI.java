package Modules.UI;

import Modules.Controllers.LoginController;
import java.util.Scanner;

public class LogInUI {

    private LoginController loginController;

    private Scanner input = new Scanner(System.in);

    /** Constructor for LogInUI
     *
     * @param loginController Controller which handles user inputs regarding logging-in
     */
    LogInUI(LoginController loginController) {
        this.loginController = loginController;
    }


    /** Prompts user to log into their account within the console, and stores user information within loginController
     *  after log-in is successful.
     *
     * @return True if log-in is successful, false otherwise
     */
    public boolean run(){

        String username;
        String password;

        System.out.println("---Log In---\n");

        System.out.println("\nEnter Username");
        username = input.nextLine();
        System.out.println("\nEnter Password");
        password = input.nextLine();

        while (!loginController.validateUsernamePassword(username, password)) {
            System.out.println("\nInvalid Username and Password Combination. Please Retry.");
            System.out.println("\nEnter Username");
            username = input.nextLine();
            System.out.println("\nEnter Password");
            password = input.nextLine();
        }

        return loginController.logIn(username, password);
    }
}
