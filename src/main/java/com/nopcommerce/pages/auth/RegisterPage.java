package com.nopcommerce.pages.auth;

import com.nopcommerce.core.base.BasePage;
import org.openqa.selenium.By;

public class RegisterPage extends BasePage {
    
    // Registration form fields - all using IDs
    private static final By GENDER_MALE = By.id("gender-male");
    private static final By GENDER_FEMALE = By.id("gender-female");
    private static final By FIRST_NAME_INPUT = By.id("FirstName");
    private static final By LAST_NAME_INPUT = By.id("LastName");
    private static final By EMAIL_INPUT = By.id("Email");
    private static final By PASSWORD_INPUT = By.id("Password");
    private static final By CONFIRM_PASSWORD_INPUT = By.id("ConfirmPassword");
    private static final By REGISTER_BUTTON = By.id("register-button");
    
    // Success message and continue button - no IDs available, using CSS
    private static final By SUCCESS_MESSAGE = By.cssSelector(".result");
    private static final By CONTINUE_BUTTON = By.cssSelector("a.button-1.register-continue-button");

    public void selectGender(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            click(GENDER_MALE);
        } else if ("female".equalsIgnoreCase(gender)) {
            click(GENDER_FEMALE);
        }
    }

    public void enterFirstName(String firstName) {
        sendKeys(FIRST_NAME_INPUT, firstName);
    }

    public void enterLastName(String lastName) {
        sendKeys(LAST_NAME_INPUT, lastName);
    }

    public void enterEmail(String email) {
        sendKeys(EMAIL_INPUT, email);
    }

    public void enterPassword(String password) {
        sendKeys(PASSWORD_INPUT, password);
    }

    public void enterConfirmPassword(String password) {
        sendKeys(CONFIRM_PASSWORD_INPUT, password);
    }

    public void clickRegisterButton() {
        click(REGISTER_BUTTON);
    }

    public void register(String firstName, String lastName, String email, String password) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(password);
        clickRegisterButton();
    }

    public boolean isRegistrationSuccessful() {
        return isElementVisible(SUCCESS_MESSAGE);
    }

    public String getSuccessMessage() {
        if (isRegistrationSuccessful()) {
            return getText(SUCCESS_MESSAGE);
        }
        return "";
    }

    public void clickContinue() {
        if (isElementVisible(CONTINUE_BUTTON)) {
            click(CONTINUE_BUTTON);
        }
    }
}
