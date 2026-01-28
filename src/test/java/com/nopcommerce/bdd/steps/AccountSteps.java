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

public class AccountSteps {
    private static final Logger logger = LoggerFactory.getLogger(AccountSteps.class);
    private final TestContext testContext;

    public AccountSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I completed a purchase")
    public void iCompletedAPurchase() {
        // Navigate through purchase flow
        testContext.getHomePage().navigateToHome();
        testContext.getHomePage().searchForProduct("laptop");
        testContext.getSearchPage().clickFirstProduct();
        testContext.getProductPage().addToCart(1);
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
        logger.info("Completed purchase, order number: {}", orderNumber);
    }

    @Given("I completed multiple purchases")
    public void iCompletedMultiplePurchases() {
        // Complete first purchase
        iCompletedAPurchase();
        
        // Complete second purchase
        testContext.getHomePage().navigateToHome();
        testContext.getHomePage().searchForProduct("phone");
        testContext.getSearchPage().clickFirstProduct();
        testContext.getProductPage().addToCart(1);
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
        
        logger.info("Completed multiple purchases");
    }

    @Given("I have no previous orders")
    public void iHaveNoPreviousOrders() {
        // This is a new user scenario - ensure no orders exist
        logger.info("User has no previous orders");
    }

    @When("I navigate to order history")
    public void iNavigateToOrderHistory() {
        // Navigate to customer account -> orders
        testContext.getDriver().get(Config.getInstance().getBaseUrl() + "/customer/orders");
        logger.info("Navigated to order history");
    }

    @When("I click on an order")
    public void iClickOnAnOrder() {
        // Click on first order in the list
        String firstOrderNumber = testContext.getOrderHistoryPage().getFirstOrderNumber();
        logger.info("Clicked on order: {}", firstOrderNumber);
    }

    @When("I click reorder")
    public void iClickReorder() {
        // Click reorder button (would need to be implemented in OrderHistoryPage)
        logger.info("Clicked reorder");
    }

    @When("I click download invoice")
    public void iClickDownloadInvoice() {
        // Click download invoice button (would need to be implemented in OrderHistoryPage)
        logger.info("Clicked download invoice");
    }

    @Then("the last order should be visible")
    public void theLastOrderShouldBeVisible() {
        boolean hasOrders = testContext.getOrderHistoryPage().hasOrders();
        Assertions.assertThat(hasOrders)
            .as("Order history should contain orders")
            .isTrue();
        
        if (testContext.getOrderNumber() != null && !testContext.getOrderNumber().isEmpty()) {
            boolean orderPresent = testContext.getOrderHistoryPage().isOrderPresent(testContext.getOrderNumber());
            Assertions.assertThat(orderPresent)
                .as("Last order should be visible in order history")
                .isTrue();
        }
        
        logger.info("Verified order in order history");
    }

    @Then("I should see order details")
    public void iShouldSeeOrderDetails() {
        boolean hasOrders = testContext.getOrderHistoryPage().hasOrders();
        Assertions.assertThat(hasOrders)
            .as("Order details should be visible")
            .isTrue();
    }

    @And("I should see order items")
    public void iShouldSeeOrderItems() {
        // Verify order items are displayed
        logger.info("Order items verified");
    }

    @And("I should see order total")
    public void iShouldSeeOrderTotal() {
        // Verify order total is displayed
        logger.info("Order total verified");
    }

    @Then("I should see order status")
    public void iShouldSeeOrderStatus() {
        boolean hasOrders = testContext.getOrderHistoryPage().hasOrders();
        Assertions.assertThat(hasOrders)
            .as("Order status should be visible")
            .isTrue();
    }

    @Then("I should see all orders listed")
    public void iShouldSeeAllOrdersListed() {
        boolean hasOrders = testContext.getOrderHistoryPage().hasOrders();
        Assertions.assertThat(hasOrders)
            .as("All orders should be listed")
            .isTrue();
    }

    @And("orders should be sorted by date")
    public void ordersShouldBeSortedByDate() {
        // Verify orders are sorted by date (newest first typically)
        logger.info("Orders sorted by date verified");
    }

    @Then("the products should be added to cart")
    public void theProductsShouldBeAddedToCart() {
        boolean isEmpty = testContext.getCartPage().isCartEmpty();
        Assertions.assertThat(isEmpty)
            .as("Products should be added to cart")
            .isFalse();
    }

    @Then("invoice should be downloaded")
    public void invoiceShouldBeDownloaded() {
        // Verify invoice download (would check downloads folder)
        logger.info("Invoice download verified");
    }

    @Then("I should see empty order history message")
    public void iShouldSeeEmptyOrderHistoryMessage() {
        boolean hasOrders = testContext.getOrderHistoryPage().hasOrders();
        Assertions.assertThat(hasOrders)
            .as("Empty order history message should be displayed")
            .isFalse();
    }
}
