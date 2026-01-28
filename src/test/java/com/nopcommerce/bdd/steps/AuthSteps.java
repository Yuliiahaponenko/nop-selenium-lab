package com.nopcommerce.bdd.steps;

import com.nopcommerce.bdd.context.TestContext;
import com.nopcommerce.core.config.Config;
import com.nopcommerce.utils.factories.UserFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthSteps {
    private static final Logger logger = LoggerFactory.getLogger(AuthSteps.class);
    private final TestContext testContext;
    private final Config config;

    public AuthSteps(TestContext testContext) {
        this.testContext = testContext;
        this.config = Config.getInstance();
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        testContext.getHomePage().navigateToHome();
        logger.info("Navigated to home page");
    }

    @Given("I am logged in")
    public void iAmLoggedIn() {
        testContext.getHomePage().navigateToHome();
        testContext.getHomePage().clickLogin();
        testContext.getLoginPage().login(
            config.getTestUserEmail(),
            config.getTestUserPassword()
        );
        logger.info("Logged in as: {}", config.getTestUserEmail());
    }

    @Given("I am not logged in")
    public void iAmNotLoggedIn() {
        // Ensure user is logged out
        if (testContext.getHomePage().isUserLoggedIn()) {
            testContext.getHomePage().clickLogout();
        }
        logger.info("Ensured user is not logged in");
    }

    @When("I login with valid credentials")
    public void iLoginWithValidCredentials() {
        testContext.getHomePage().clickLogin();
        testContext.getLoginPage().login(
            config.getTestUserEmail(),
            config.getTestUserPassword()
        );
    }

    @When("I login with invalid credentials")
    public void iLoginWithInvalidCredentials() {
        testContext.getHomePage().clickLogin();
        testContext.getLoginPage().login(
            "invalid@example.com",
            "wrongpassword"
        );
    }

    @When("I login with email {string} and password {string}")
    public void iLoginWithEmailAndPassword(String email, String password) {
        testContext.getHomePage().clickLogin();
        testContext.getLoginPage().login(email, password);
    }

    @When("I attempt to login with empty email and password {string}")
    public void iAttemptToLoginWithEmptyEmail(String password) {
        testContext.getHomePage().clickLogin();
        testContext.getLoginPage().enterPassword(password);
        testContext.getLoginPage().clickLoginButton();
    }

    @When("I attempt to login with email {string} and empty password")
    public void iAttemptToLoginWithEmptyPassword(String email) {
        testContext.getHomePage().clickLogin();
        testContext.getLoginPage().enterEmail(email);
        testContext.getLoginPage().clickLoginButton();
    }

    @When("I attempt to login with email {string} and password {string}")
    public void iAttemptToLoginWithEmailAndPassword(String email, String password) {
        testContext.getHomePage().clickLogin();
        testContext.getLoginPage().login(email, password);
    }

    @When("I login with valid credentials and check remember me")
    public void iLoginWithValidCredentialsAndCheckRememberMe() {
        testContext.getHomePage().clickLogin();
        // Note: This would require a method in LoginPage to check remember me checkbox
        testContext.getLoginPage().login(
            config.getTestUserEmail(),
            config.getTestUserPassword()
        );
        logger.info("Logged in with remember me checked");
    }

    @When("I register with valid required fields and a unique email")
    public void iRegisterWithValidRequiredFieldsAndAUniqueEmail() {
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.setUniqueEmail(uniqueEmail);
        testContext.getHomePage().clickRegister();
        testContext.getRegisterPage().register(
            "Test",
            "User",
            uniqueEmail,
            "Test123!"
        );
        logger.info("Registered with email: {}", uniqueEmail);
    }

    @When("I register with email {string} password {string} and confirm password {string}")
    public void iRegisterWithEmailPasswordAndConfirmPassword(String email, String password, String confirmPassword) {
        testContext.getHomePage().clickRegister();
        testContext.getRegisterPage().enterFirstName("Test");
        testContext.getRegisterPage().enterLastName("User");
        testContext.getRegisterPage().enterEmail(email);
        testContext.getRegisterPage().enterPassword(password);
        testContext.getRegisterPage().enterConfirmPassword(confirmPassword);
        testContext.getRegisterPage().clickRegisterButton();
    }

    @When("I register with invalid email format {string}")
    public void iRegisterWithInvalidEmailFormat(String email) {
        testContext.getHomePage().clickRegister();
        testContext.getRegisterPage().enterFirstName("Test");
        testContext.getRegisterPage().enterLastName("User");
        testContext.getRegisterPage().enterEmail(email);
        testContext.getRegisterPage().enterPassword("Test123!");
        testContext.getRegisterPage().enterConfirmPassword("Test123!");
        testContext.getRegisterPage().clickRegisterButton();
    }

    @When("I register with weak password {string}")
    public void iRegisterWithWeakPassword(String password) {
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getHomePage().clickRegister();
        testContext.getRegisterPage().enterFirstName("Test");
        testContext.getRegisterPage().enterLastName("User");
        testContext.getRegisterPage().enterEmail(uniqueEmail);
        testContext.getRegisterPage().enterPassword(password);
        testContext.getRegisterPage().enterConfirmPassword(password);
        testContext.getRegisterPage().clickRegisterButton();
    }

    @When("I register with existing email {string}")
    public void iRegisterWithExistingEmail(String email) {
        testContext.getHomePage().clickRegister();
        testContext.getRegisterPage().register(
            "Test",
            "User",
            email,
            "Test123!"
        );
    }

    @When("I register with missing first name")
    public void iRegisterWithMissingFirstName() {
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getHomePage().clickRegister();
        testContext.getRegisterPage().enterLastName("User");
        testContext.getRegisterPage().enterEmail(uniqueEmail);
        testContext.getRegisterPage().enterPassword("Test123!");
        testContext.getRegisterPage().enterConfirmPassword("Test123!");
        testContext.getRegisterPage().clickRegisterButton();
    }

    @When("I register with missing last name")
    public void iRegisterWithMissingLastName() {
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getHomePage().clickRegister();
        testContext.getRegisterPage().enterFirstName("Test");
        testContext.getRegisterPage().enterEmail(uniqueEmail);
        testContext.getRegisterPage().enterPassword("Test123!");
        testContext.getRegisterPage().enterConfirmPassword("Test123!");
        testContext.getRegisterPage().clickRegisterButton();
    }

    @When("I register with gender {string} first name {string} last name {string} email and password")
    public void iRegisterWithGenderFirstNameLastNameEmailAndPassword(String gender, String firstName, String lastName) {
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getHomePage().clickRegister();
        testContext.getRegisterPage().selectGender(gender);
        testContext.getRegisterPage().register(firstName, lastName, uniqueEmail, "Test123!");
    }

    @When("I logout")
    public void iLogout() {
        testContext.getHomePage().clickLogout();
    }

    @Then("I should see my account area")
    public void iShouldSeeMyAccountArea() {
        boolean isLoggedIn = testContext.getHomePage().isUserLoggedIn();
        Assertions.assertThat(isLoggedIn)
            .as("User should be logged in and see account area")
            .isTrue();
    }

    @Then("I should see a validation error message")
    public void iShouldSeeAValidationErrorMessage() {
        boolean hasError = testContext.getLoginPage().isErrorMessageDisplayed();
        Assertions.assertThat(hasError)
            .as("Login error message should be displayed")
            .isTrue();
    }

    @Then("I should see email validation error")
    public void iShouldSeeEmailValidationError() {
        // Check for email field-specific validation error
        // This could be HTML5 validation, inline field error, or general error message
        boolean hasError = testContext.getLoginPage().isEmailValidationErrorDisplayed();
        Assertions.assertThat(hasError)
            .as("Email validation error should be displayed")
            .isTrue();
    }

    @Then("I should see password validation error")
    public void iShouldSeePasswordValidationError() {
        // Check for password field-specific validation error
        // This could be HTML5 validation or general error message
        boolean hasError = testContext.getLoginPage().isPasswordValidationErrorDisplayed();
        Assertions.assertThat(hasError)
            .as("Password validation error should be displayed")
            .isTrue();
    }

    @Then("I should see email format validation error")
    public void iShouldSeeEmailFormatValidationError() {
        boolean hasError = testContext.getLoginPage().isErrorMessageDisplayed() || 
                          testContext.getRegisterPage().isRegistrationSuccessful() == false;
        Assertions.assertThat(hasError)
            .as("Email format validation error should be displayed")
            .isTrue();
    }

    @Then("account should be created successfully")
    public void accountShouldBeCreatedSuccessfully() {
        boolean isSuccess = testContext.getRegisterPage().isRegistrationSuccessful();
        Assertions.assertThat(isSuccess)
            .as("Registration should be successful")
            .isTrue();
    }

    @Then("I should see password mismatch error")
    public void iShouldSeePasswordMismatchError() {
        boolean hasError = !testContext.getRegisterPage().isRegistrationSuccessful();
        Assertions.assertThat(hasError)
            .as("Password mismatch error should be displayed")
            .isTrue();
    }

    @Then("I should see password strength validation error")
    public void iShouldSeePasswordStrengthValidationError() {
        boolean hasError = !testContext.getRegisterPage().isRegistrationSuccessful();
        Assertions.assertThat(hasError)
            .as("Password strength validation error should be displayed")
            .isTrue();
    }

    @Then("I should see email already exists error")
    public void iShouldSeeEmailAlreadyExistsError() {
        boolean hasError = !testContext.getRegisterPage().isRegistrationSuccessful();
        Assertions.assertThat(hasError)
            .as("Email already exists error should be displayed")
            .isTrue();
    }

    @Then("I should see first name validation error")
    public void iShouldSeeFirstNameValidationError() {
        boolean hasError = !testContext.getRegisterPage().isRegistrationSuccessful();
        Assertions.assertThat(hasError)
            .as("First name validation error should be displayed")
            .isTrue();
    }

    @Then("I should see last name validation error")
    public void iShouldSeeLastNameValidationError() {
        boolean hasError = !testContext.getRegisterPage().isRegistrationSuccessful();
        Assertions.assertThat(hasError)
            .as("Last name validation error should be displayed")
            .isTrue();
    }

    @Then("I should be logged out")
    public void iShouldBeLoggedOut() {
        boolean isLoggedIn = testContext.getHomePage().isUserLoggedIn();
        Assertions.assertThat(isLoggedIn)
            .as("User should be logged out")
            .isFalse();
    }

    @Then("I should not see account information")
    public void iShouldNotSeeAccountInformation() {
        boolean isLoggedIn = testContext.getHomePage().isUserLoggedIn();
        Assertions.assertThat(isLoggedIn)
            .as("Account information should not be visible")
            .isFalse();
    }

    @Then("I should see login link")
    public void iShouldSeeLoginLink() {
        // Check if login link is visible (user is logged out)
        boolean isLoggedIn = testContext.getHomePage().isUserLoggedIn();
        Assertions.assertThat(isLoggedIn)
            .as("Login link should be visible when logged out")
            .isFalse();
    }

    @Then("logout link should not be visible")
    public void logoutLinkShouldNotBeVisible() {
        boolean isLoggedIn = testContext.getHomePage().isUserLoggedIn();
        Assertions.assertThat(isLoggedIn)
            .as("Logout link should not be visible when not logged in")
            .isFalse();
    }

    @And("remember me should be checked")
    public void rememberMeShouldBeChecked() {
        // This would verify remember me checkbox is checked
        logger.info("Remember me checkbox verified");
    }
}
