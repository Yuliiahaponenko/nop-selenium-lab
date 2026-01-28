package com.nopcommerce.pages.auth;

import com.nopcommerce.core.base.BasePage;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    
    // Login form fields - using IDs
    private static final By EMAIL_INPUT = By.id("Email");
    private static final By PASSWORD_INPUT = By.id("Password");
    
    // Login button - try ID first, then CSS fallback
    private static final By LOGIN_BUTTON_ID = By.id("login-button");
    private static final By LOGIN_BUTTON = By.cssSelector("button[type='submit'].login-button");
    
    // Error messages - no IDs available, using CSS
    private static final By ERROR_MESSAGE = By.cssSelector(".message-error.validation-summary-errors");
    private static final By EMAIL_VALIDATION_ERROR = By.cssSelector("#Email.validation-message, #Email + .field-validation-error, span[data-valmsg-for='Email']");

    public void enterEmail(String email) {
        sendKeys(EMAIL_INPUT, email);
    }

    public void enterPassword(String password) {
        sendKeys(PASSWORD_INPUT, password);
    }

    public void clickLoginButton() {
        // Try ID first, then CSS fallback
        if (isElementVisible(LOGIN_BUTTON_ID)) {
            click(LOGIN_BUTTON_ID);
        } else {
            click(LOGIN_BUTTON);
        }
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isErrorMessageDisplayed() {
        return isElementVisible(ERROR_MESSAGE);
    }

    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            return getText(ERROR_MESSAGE);
        }
        return "";
    }

    public boolean isEmailValidationErrorDisplayed() {
        // Check for HTML5 validation (required field)
        try {
            org.openqa.selenium.WebElement emailElement = driver.findElement(EMAIL_INPUT);
            String validationMessage = emailElement.getAttribute("validationMessage");
            boolean hasHtml5Validation = validationMessage != null && !validationMessage.isEmpty();
            
            // Check for inline validation error messages
            boolean hasInlineValidation = isElementVisible(EMAIL_VALIDATION_ERROR);
            
            // Check for general error message that might contain email validation
            boolean hasGeneralError = isErrorMessageDisplayed();
            String errorText = hasGeneralError ? getErrorMessage().toLowerCase() : "";
            boolean errorMentionsEmail = errorText.contains("email") || errorText.contains("required");
            
            return hasHtml5Validation || hasInlineValidation || errorMentionsEmail;
        } catch (Exception e) {
            // Fallback to general error message check
            return isErrorMessageDisplayed();
        }
    }

    public boolean isPasswordValidationErrorDisplayed() {
        // Check for HTML5 validation (required field)
        try {
            org.openqa.selenium.WebElement passwordElement = driver.findElement(PASSWORD_INPUT);
            String validationMessage = passwordElement.getAttribute("validationMessage");
            boolean hasHtml5Validation = validationMessage != null && !validationMessage.isEmpty();
            
            // Check for general error message
            boolean hasGeneralError = isErrorMessageDisplayed();
            String errorText = hasGeneralError ? getErrorMessage().toLowerCase() : "";
            boolean errorMentionsPassword = errorText.contains("password") || errorText.contains("required");
            
            return hasHtml5Validation || errorMentionsPassword;
        } catch (Exception e) {
            // Fallback to general error message check
            return isErrorMessageDisplayed();
        }
    }
}
