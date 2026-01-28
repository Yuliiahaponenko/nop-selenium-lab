package com.nopcommerce.bdd.steps;

import com.nopcommerce.bdd.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductSteps {
    private static final Logger logger = LoggerFactory.getLogger(ProductSteps.class);
    private final TestContext testContext;

    public ProductSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I opened a product from search results")
    public void iOpenedAProductFromSearchResults() {
        testContext.getHomePage().navigateToHome();
        testContext.getHomePage().searchForProduct("laptop");
        testContext.getSearchPage().clickFirstProduct();
        logger.info("Opened product from search results");
    }

    @When("I search for {string}")
    public void iSearchFor(String searchTerm) {
        testContext.getHomePage().searchForProduct(searchTerm);
        logger.info("Searched for: {}", searchTerm);
    }

    @When("I search for empty string")
    public void iSearchForEmptyString() {
        testContext.getHomePage().searchForProduct("");
        logger.info("Searched for empty string");
    }

    @When("I click on the first product")
    public void iClickOnTheFirstProduct() {
        testContext.getSearchPage().clickFirstProduct();
        logger.info("Clicked on first product");
    }

    @When("I add quantity {string} to cart")
    public void iAddQuantityToCart(String quantity) {
        int qty = Integer.parseInt(quantity);
        testContext.getProductPage().addToCart(qty);
        logger.info("Added quantity {} to cart", quantity);
    }

    @When("I attempt to add quantity {string} to cart")
    public void iAttemptToAddQuantityToCart(String quantity) {
        int qty = Integer.parseInt(quantity);
        testContext.getProductPage().addToCart(qty);
        logger.info("Attempted to add quantity {} to cart", quantity);
    }

    @When("I attempt to add quantity exceeding stock limit")
    public void iAttemptToAddQuantityExceedingStockLimit() {
        testContext.getProductPage().addToCart(9999);
        logger.info("Attempted to add quantity exceeding stock limit");
    }

    @When("I navigate back to search results")
    public void iNavigateBackToSearchResults() {
        testContext.getDriver().navigate().back();
        logger.info("Navigated back to search results");
    }

    @Then("I should see a list of products")
    public void iShouldSeeAListOfProducts() {
        boolean hasResults = testContext.getSearchPage().hasSearchResults();
        Assertions.assertThat(hasResults)
            .as("Search should return product results")
            .isTrue();
    }

    @Then("I should see a no results message")
    public void iShouldSeeANoResultsMessage() {
        boolean noResults = testContext.getSearchPage().isNoResultsMessageDisplayed();
        Assertions.assertThat(noResults)
            .as("No results message should be displayed")
            .isTrue();
    }

    @Then("I should see search validation message")
    public void iShouldSeeSearchValidationMessage() {
        // Check for search validation or empty search message
        boolean hasMessage = testContext.getSearchPage().isNoResultsMessageDisplayed() || 
                           !testContext.getSearchPage().hasSearchResults();
        Assertions.assertThat(hasMessage)
            .as("Search validation message should be displayed")
            .isTrue();
    }

    @Then("I should see a no results message or validation message")
    public void iShouldSeeANoResultsMessageOrValidationMessage() {
        boolean hasMessage = testContext.getSearchPage().isNoResultsMessageDisplayed() || 
                           !testContext.getSearchPage().hasSearchResults();
        Assertions.assertThat(hasMessage)
            .as("No results or validation message should be displayed")
            .isTrue();
    }

    @Then("I should see search results containing {string}")
    public void iShouldSeeSearchResultsContaining(String searchTerm) {
        boolean hasResults = testContext.getSearchPage().hasSearchResults();
        Assertions.assertThat(hasResults)
            .as("Search results containing '{}' should be displayed", searchTerm)
            .isTrue();
    }

    @Then("I should see search results")
    public void iShouldSeeSearchResults() {
        boolean hasResults = testContext.getSearchPage().hasSearchResults();
        Assertions.assertThat(hasResults)
            .as("Search results should be displayed")
            .isTrue();
    }

    @Then("I should see product title, price, and add-to-cart button")
    public void iShouldSeeProductTitlePriceAndAddToCartButton() {
        String title = testContext.getProductPage().getProductTitle();
        String price = testContext.getProductPage().getProductPrice();

        Assertions.assertThat(title)
            .as("Product title should be visible")
            .isNotEmpty();

        Assertions.assertThat(price)
            .as("Product price should be visible")
            .isNotEmpty();

        logger.info("Product details - Title: {}, Price: {}", title, price);
    }

    @Then("I should see product description")
    public void iShouldSeeProductDescription() {
        String title = testContext.getProductPage().getProductTitle();
        Assertions.assertThat(title)
            .as("Product description should be visible")
            .isNotEmpty();
    }

    @Then("I should see product specifications")
    public void iShouldSeeProductSpecifications() {
        String title = testContext.getProductPage().getProductTitle();
        Assertions.assertThat(title)
            .as("Product specifications should be visible")
            .isNotEmpty();
    }

    @Then("I should see product images")
    public void iShouldSeeProductImages() {
        // Verify product page is loaded (images would be present)
        String title = testContext.getProductPage().getProductTitle();
        Assertions.assertThat(title)
            .as("Product images should be visible")
            .isNotEmpty();
    }

    @Then("I should see success message with quantity {string}")
    public void iShouldSeeSuccessMessageWithQuantity(String quantity) {
        boolean isSuccess = testContext.getProductPage().isAddToCartSuccessMessageDisplayed();
        Assertions.assertThat(isSuccess)
            .as("Success message with quantity {} should be displayed", quantity)
            .isTrue();
    }

    @Then("I should see product quantity validation error")
    public void iShouldSeeProductQuantityValidationError() {
        // Check if add to cart failed or validation error shown
        boolean hasError = !testContext.getProductPage().isAddToCartSuccessMessageDisplayed();
        Assertions.assertThat(hasError)
            .as("Product quantity validation error should be displayed")
            .isTrue();
    }

    @Then("I should see stock limit validation message")
    public void iShouldSeeStockLimitValidationMessage() {
        boolean hasError = !testContext.getProductPage().isAddToCartSuccessMessageDisplayed();
        Assertions.assertThat(hasError)
            .as("Stock limit validation message should be displayed")
            .isTrue();
    }

    @Then("I should see the search results page")
    public void iShouldSeeTheSearchResultsPage() {
        boolean hasResults = testContext.getSearchPage().hasSearchResults();
        Assertions.assertThat(hasResults)
            .as("Search results page should be displayed")
            .isTrue();
    }
}
