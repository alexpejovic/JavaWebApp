package modules.views;

/**
 * Interface for the signup page
 */
public interface ISignupView {
    /**
     * Displays given message to user on the signup page
     * @param message the message to display
     */
    public void displaySignupMessage(String message);

    /**
     * Navigates to LoginPage
     */
    public void returnToLoginPage();

}
