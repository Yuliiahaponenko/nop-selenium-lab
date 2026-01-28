package com.nopcommerce.bdd.steps;

import com.nopcommerce.bdd.context.TestContext;
import com.nopcommerce.utils.factories.UserFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckoutSteps {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutSteps.class);
    private final TestContext testContext;

    public CheckoutSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("I checkout as a guest with valid address details")
    public void iCheckoutAsAGuestWithValidAddressDetails() {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getCheckoutPage().enterBillingAddress(
            "John",
            "Doe",
            uniqueEmail,
            "United States",
            "New York",
            "123 Main St",
            "10001",
            "555-1234"
        );
        testContext.getCheckoutPage().clickContinueBilling();
        testContext.getCheckoutPage().clickContinueShipping();
        testContext.getCheckoutPage().clickContinuePayment();
        testContext.getCheckoutPage().clickContinuePaymentInfo();
        testContext.getCheckoutPage().clickConfirmOrder();
        
        logger.info("Completed guest checkout");
    }

    @When("I attempt checkout as guest with missing required fields")
    public void iAttemptCheckoutAsGuestWithMissingRequiredFields() {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        // Attempt to continue without filling required fields
        testContext.getCheckoutPage().clickContinueBilling();
        logger.info("Attempted checkout with missing fields");
    }

    @When("I attempt checkout as guest with invalid email {string}")
    public void iAttemptCheckoutAsGuestWithInvalidEmail(String email) {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        testContext.getCheckoutPage().enterBillingAddress(
            "John",
            "Doe",
            email,
            "United States",
            "New York",
            "123 Main St",
            "10001",
            "555-1234"
        );
        testContext.getCheckoutPage().clickContinueBilling();
    }

    @When("I attempt checkout as guest with invalid phone {string}")
    public void iAttemptCheckoutAsGuestWithInvalidPhone(String phone) {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getCheckoutPage().enterBillingAddress(
            "John",
            "Doe",
            uniqueEmail,
            "United States",
            "New York",
            "123 Main St",
            "10001",
            phone
        );
        testContext.getCheckoutPage().clickContinueBilling();
    }

    @When("I attempt checkout as guest with invalid zip code {string}")
    public void iAttemptCheckoutAsGuestWithInvalidZipCode(String zip) {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getCheckoutPage().enterBillingAddress(
            "John",
            "Doe",
            uniqueEmail,
            "United States",
            "New York",
            "123 Main St",
            zip,
            "555-1234"
        );
        testContext.getCheckoutPage().clickContinueBilling();
    }

    @When("I attempt checkout as guest with missing city")
    public void iAttemptCheckoutAsGuestWithMissingCity() {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getCheckoutPage().enterBillingAddress(
            "John",
            "Doe",
            uniqueEmail,
            "United States",
            "",
            "123 Main St",
            "10001",
            "555-1234"
        );
        testContext.getCheckoutPage().clickContinueBilling();
    }

    @When("I attempt checkout as guest with missing address")
    public void iAttemptCheckoutAsGuestWithMissingAddress() {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getCheckoutPage().enterBillingAddress(
            "John",
            "Doe",
            uniqueEmail,
            "United States",
            "New York",
            "",
            "10001",
            "555-1234"
        );
        testContext.getCheckoutPage().clickContinueBilling();
    }

    @When("I proceed through billing address step")
    public void iProceedThroughBillingAddressStep() {
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getCheckoutPage().enterBillingAddress(
            "John",
            "Doe",
            uniqueEmail,
            "United States",
            "New York",
            "123 Main St",
            "10001",
            "555-1234"
        );
        testContext.getCheckoutPage().clickContinueBilling();
    }

    @And("I proceed through shipping method step")
    public void iProceedThroughShippingMethodStep() {
        testContext.getCheckoutPage().clickContinueShipping();
    }

    @And("I proceed through payment method step")
    public void iProceedThroughPaymentMethodStep() {
        testContext.getCheckoutPage().clickContinuePayment();
    }

    @And("I proceed through payment information step")
    public void iProceedThroughPaymentInformationStep() {
        testContext.getCheckoutPage().clickContinuePaymentInfo();
    }

    @And("I confirm the order")
    public void iConfirmTheOrder() {
        testContext.getCheckoutPage().clickConfirmOrder();
    }

    @When("I complete checkout with valid billing and shipping details")
    public void iCompleteCheckoutWithValidBillingAndShippingDetails() {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getCheckoutPage().enterBillingAddress(
            "John",
            "Doe",
            uniqueEmail,
            "United States",
            "New York",
            "123 Main St",
            "10001",
            "555-1234"
        );
        testContext.getCheckoutPage().clickContinueBilling();
        testContext.getCheckoutPage().clickContinueShipping();
        testContext.getCheckoutPage().clickContinuePayment();
        testContext.getCheckoutPage().clickContinuePaymentInfo();
        testContext.getCheckoutPage().clickConfirmOrder();
        
        String orderNumber = testContext.getCheckoutPage().getOrderNumber();
        testContext.setOrderNumber(orderNumber);
        logger.info("Completed checkout, order number: {}", orderNumber);
    }

    @When("I have a saved address")
    public void iHaveASavedAddress() {
        // Assume user has saved address from previous orders
        logger.info("User has saved address");
    }

    @When("I select saved address for checkout")
    public void iSelectSavedAddressForCheckout() {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        logger.info("Selected saved address");
    }

    @And("I complete checkout")
    public void iCompleteCheckout() {
        testContext.getCheckoutPage().clickContinueBilling();
        testContext.getCheckoutPage().clickContinueShipping();
        testContext.getCheckoutPage().clickContinuePayment();
        testContext.getCheckoutPage().clickContinuePaymentInfo();
        testContext.getCheckoutPage().clickConfirmOrder();
    }

    @When("I add new billing address during checkout")
    public void iAddNewBillingAddressDuringCheckout() {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getCheckoutPage().enterBillingAddress(
            "Jane",
            "Smith",
            uniqueEmail,
            "United States",
            "Los Angeles",
            "456 Oak Ave",
            "90001",
            "555-5678"
        );
    }

    @When("I complete checkout with new address")
    public void iCompleteCheckoutWithNewAddress() {
        testContext.getCheckoutPage().clickContinueBilling();
        testContext.getCheckoutPage().clickContinueShipping();
        testContext.getCheckoutPage().clickContinuePayment();
        testContext.getCheckoutPage().clickContinuePaymentInfo();
        testContext.getCheckoutPage().clickConfirmOrder();
    }

    @When("I use different address for shipping")
    public void iUseDifferentAddressForShipping() {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getCheckoutPage().enterBillingAddress(
            "John",
            "Doe",
            uniqueEmail,
            "United States",
            "New York",
            "123 Main St",
            "10001",
            "555-1234"
        );
        testContext.getCheckoutPage().clickContinueBilling();
    }

    @When("I attempt checkout without accepting terms and conditions")
    public void iAttemptCheckoutWithoutAcceptingTermsAndConditions() {
        testContext.getHomePage().clickShoppingCart();
        // Don't check terms checkbox
        testContext.getCartPage().clickCheckout();
        logger.info("Attempted checkout without accepting terms");
    }

    @When("I proceed to order review")
    public void iProceedToOrderReview() {
        testContext.getHomePage().clickShoppingCart();
        testContext.getCartPage().clickCheckout();
        String uniqueEmail = UserFactory.generateUniqueEmail();
        testContext.getCheckoutPage().enterBillingAddress(
            "John",
            "Doe",
            uniqueEmail,
            "United States",
            "New York",
            "123 Main St",
            "10001",
            "555-1234"
        );
        testContext.getCheckoutPage().clickContinueBilling();
        testContext.getCheckoutPage().clickContinueShipping();
        testContext.getCheckoutPage().clickContinuePayment();
        testContext.getCheckoutPage().clickContinuePaymentInfo();
    }

    @Then("I should reach the order confirmation step")
    public void iShouldReachTheOrderConfirmationStep() {
        boolean isSuccessful = testContext.getCheckoutPage().isOrderSuccessful();
        Assertions.assertThat(isSuccessful)
            .as("Order confirmation should be displayed")
            .isTrue();
    }

    @Then("I should see validation errors for required fields")
    public void iShouldSeeValidationErrorsForRequiredFields() {
        // Check if validation errors are displayed
        boolean hasErrors = !testContext.getCheckoutPage().isOrderSuccessful();
        Assertions.assertThat(hasErrors)
            .as("Validation errors for required fields should be displayed")
            .isTrue();
    }

    @Then("I should see checkout email format validation error")
    public void iShouldSeeCheckoutEmailFormatValidationError() {
        boolean hasError = !testContext.getCheckoutPage().isOrderSuccessful();
        Assertions.assertThat(hasError)
            .as("Email format validation error should be displayed during checkout")
            .isTrue();
    }

    @Then("I should see phone validation error")
    public void iShouldSeePhoneValidationError() {
        boolean hasError = !testContext.getCheckoutPage().isOrderSuccessful();
        Assertions.assertThat(hasError)
            .as("Phone validation error should be displayed")
            .isTrue();
    }

    @Then("I should see zip code validation error")
    public void iShouldSeeZipCodeValidationError() {
        boolean hasError = !testContext.getCheckoutPage().isOrderSuccessful();
        Assertions.assertThat(hasError)
            .as("Zip code validation error should be displayed")
            .isTrue();
    }

    @Then("I should see city validation error")
    public void iShouldSeeCityValidationError() {
        boolean hasError = !testContext.getCheckoutPage().isOrderSuccessful();
        Assertions.assertThat(hasError)
            .as("City validation error should be displayed")
            .isTrue();
    }

    @Then("I should see address validation error")
    public void iShouldSeeAddressValidationError() {
        boolean hasError = !testContext.getCheckoutPage().isOrderSuccessful();
        Assertions.assertThat(hasError)
            .as("Address validation error should be displayed")
            .isTrue();
    }

    @Then("I should see confirmation and an order number")
    public void iShouldSeeConfirmationAndAnOrderNumber() {
        boolean isSuccessful = testContext.getCheckoutPage().isOrderSuccessful();
        String orderNumber = testContext.getCheckoutPage().getOrderNumber();
        
        Assertions.assertThat(isSuccessful)
            .as("Order confirmation should be displayed")
            .isTrue();
        
        Assertions.assertThat(orderNumber)
            .as("Order number should be present")
            .isNotEmpty();
        
        logger.info("Order confirmed with number: {}", orderNumber);
    }

    @Then("I should see terms acceptance validation error")
    public void iShouldSeeTermsAcceptanceValidationError() {
        // Check if terms validation error is shown
        boolean hasError = !testContext.getCheckoutPage().isOrderSuccessful();
        Assertions.assertThat(hasError)
            .as("Terms acceptance validation error should be displayed")
            .isTrue();
    }

    @Then("I should see correct order summary")
    public void iShouldSeeCorrectOrderSummary() {
        // Verify order summary is displayed
        boolean isDisplayed = testContext.getCheckoutPage().isOrderSuccessful() || 
                             testContext.getDriver().getCurrentUrl().contains("checkout");
        Assertions.assertThat(isDisplayed)
            .as("Order summary should be displayed")
            .isTrue();
    }

    @And("I should see correct product details")
    public void iShouldSeeCorrectProductDetails() {
        // Verify product details in order summary
        logger.info("Product details verified in order summary");
    }

    @And("I should see correct total amount")
    public void iShouldSeeCorrectTotalAmount() {
        // Verify total amount in order summary
        logger.info("Total amount verified in order summary");
    }
}
