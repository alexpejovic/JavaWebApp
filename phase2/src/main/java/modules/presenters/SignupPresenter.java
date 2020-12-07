package modules.presenters;

import modules.views.ISignupView;

/**
 * Presenter class for the Signup actions
 */
public class SignupPresenter {
    private ISignupView iSignupView;

    /**
     * Constructor for SignupPresenter
     * @param iSignupView the interface for the signup page
     */
    public SignupPresenter(ISignupView iSignupView){
        this.iSignupView = iSignupView;
    }

    /**
     * Sends a message to signup page that a username is already taken
     */
    public void invalidSignup(){
        iSignupView.displaySignupMessage("Sorry username is taken.");
    }

    /**
     * Navigates back to login page after successful login
     */
    public void signupSuccessful(){
        iSignupView.returnToLoginPage();
    }


}
