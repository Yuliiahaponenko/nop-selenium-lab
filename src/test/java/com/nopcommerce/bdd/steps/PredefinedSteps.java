package com.nopcommerce.bdd.steps;

import com.nopcommerce.bdd.context.TestContext;
import com.nopcommerce.utils.waits.Waiter;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PredefinedSteps {
    private static final Logger logger = LoggerFactory.getLogger(PredefinedSteps.class);
    private final TestContext testContext;
    private WebDriver driver;

    public PredefinedSteps(TestContext testContext) {
        this.testContext = testContext;
        this.driver = testContext.getDriver();
    }

    // Navigation Steps
    @Given("I open url {string}")
    public void iOpenUrl(String url) {
        driver.get(url);
        // Wait for page to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("Opened URL: {}", url);
    }

    @Given("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver.navigate().to(url);
        logger.info("Navigated to URL: {}", url);
    }

    @When("I refresh the page")
    public void iRefreshThePage() {
        driver.navigate().refresh();
        logger.info("Page refreshed");
    }

    @When("I navigate back")
    public void iNavigateBack() {
        driver.navigate().back();
        logger.info("Navigated back");
    }

    @When("I navigate forward")
    public void iNavigateForward() {
        driver.navigate().forward();
        logger.info("Navigated forward");
    }

    // Page Title Steps
    @Then("I should see page title as {string}")
    public void iShouldSeePageTitleAs(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assertions.assertThat(actualTitle)
            .as("Page title should match")
            .isEqualTo(expectedTitle);
        logger.info("Verified page title: {}", actualTitle);
    }

    @Then("I should see page title contains {string}")
    public void iShouldSeePageTitleContains(String expectedTitlePart) {
        String actualTitle = driver.getTitle();
        Assertions.assertThat(actualTitle)
            .as("Page title should contain text")
            .contains(expectedTitlePart);
        logger.info("Verified page title contains: {}", expectedTitlePart);
    }

    @Then("I should see page url contains {string}")
    public void iShouldSeePageUrlContains(String expectedUrlPart) {
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertThat(actualUrl)
            .as("Page URL should contain text")
            .contains(expectedUrlPart);
        logger.info("Verified URL contains: {}", expectedUrlPart);
    }

    // Element Presence Steps
    @Then("element with xpath {string} should be present")
    public void elementWithXpathShouldBePresent(String xpath) {
        WebElement element = Waiter.waitForElementPresent(driver, By.xpath(xpath));
        Assertions.assertThat(element)
            .as("Element should be present: " + xpath)
            .isNotNull();
        logger.info("Element present: {}", xpath);
    }

    @Then("element with id {string} should be present")
    public void elementWithIdShouldBePresent(String id) {
        WebElement element = Waiter.waitForElementPresent(driver, By.id(id));
        Assertions.assertThat(element)
            .as("Element should be present: " + id)
            .isNotNull();
        logger.info("Element with id present: {}", id);
    }

    @Then("element with css {string} should be present")
    public void elementWithCssShouldBePresent(String css) {
        WebElement element = Waiter.waitForElementPresent(driver, By.cssSelector(css));
        Assertions.assertThat(element)
            .as("Element should be present: " + css)
            .isNotNull();
        logger.info("Element with css present: {}", css);
    }

    @Then("element with xpath {string} should not be present")
    public void elementWithXpathShouldNotBePresent(String xpath) {
        try {
            // Wait a bit for page to stabilize
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        Assertions.assertThat(elements)
            .as("Element should not be present: " + xpath)
            .isEmpty();
        logger.info("Verified element not present: {}", xpath);
    }

    @Then("element with id {string} should not be present")
    public void elementWithIdShouldNotBePresent(String id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        List<WebElement> elements = driver.findElements(By.id(id));
        Assertions.assertThat(elements)
            .as("Element should not be present: " + id)
            .isEmpty();
        logger.info("Verified element with id not present: {}", id);
    }

    @Then("element with css {string} should not be present")
    public void elementWithCssShouldNotBePresent(String css) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        List<WebElement> elements = driver.findElements(By.cssSelector(css));
        Assertions.assertThat(elements)
            .as("Element should not be present: " + css)
            .isEmpty();
        logger.info("Verified element with css not present: {}", css);
    }

    // Element Visibility Steps
    @Then("element with xpath {string} should be displayed")
    public void elementWithXpathShouldBeDisplayed(String xpath) {
        WebElement element = Waiter.waitForElementVisible(driver, By.xpath(xpath));
        Assertions.assertThat(element.isDisplayed())
            .as("Element should be visible: " + xpath)
            .isTrue();
        logger.info("Element displayed: {}", xpath);
    }

    @Then("element with id {string} should be displayed")
    public void elementWithIdShouldBeDisplayed(String id) {
        WebElement element = Waiter.waitForElementVisible(driver, By.id(id));
        Assertions.assertThat(element.isDisplayed())
            .as("Element should be visible: " + id)
            .isTrue();
        logger.info("Element with id displayed: {}", id);
    }

    @Then("element with css {string} should be displayed")
    public void elementWithCssShouldBeDisplayed(String css) {
        WebElement element = Waiter.waitForElementVisible(driver, By.cssSelector(css));
        Assertions.assertThat(element.isDisplayed())
            .as("Element should be visible: " + css)
            .isTrue();
        logger.info("Element with css displayed: {}", css);
    }

    // Text Input Steps
    @When("I type {string} into element with xpath {string}")
    public void iTypeIntoElementWithXpath(String text, String xpath) {
        WebElement element = Waiter.waitForElementVisible(driver, By.xpath(xpath));
        element.clear();
        element.sendKeys(text);
        logger.info("Typed '{}' into element: {}", text, xpath);
    }

    @When("I type {string} into element with id {string}")
    public void iTypeIntoElementWithId(String text, String id) {
        WebElement element = Waiter.waitForElementVisible(driver, By.id(id));
        element.clear();
        element.sendKeys(text);
        logger.info("Typed '{}' into element with id: {}", text, id);
    }

    @When("I type {string} into element with css {string}")
    public void iTypeIntoElementWithCss(String text, String css) {
        WebElement element = Waiter.waitForElementVisible(driver, By.cssSelector(css));
        element.clear();
        element.sendKeys(text);
        logger.info("Typed '{}' into element with css: {}", text, css);
    }

    @When("I clear element with xpath {string}")
    public void iClearElementWithXpath(String xpath) {
        WebElement element = Waiter.waitForElementVisible(driver, By.xpath(xpath));
        element.clear();
        logger.info("Cleared element: {}", xpath);
    }

    @When("I clear element with id {string}")
    public void iClearElementWithId(String id) {
        WebElement element = Waiter.waitForElementVisible(driver, By.id(id));
        element.clear();
        logger.info("Cleared element with id: {}", id);
    }

    // Click Steps
    @When("I click on element with xpath {string}")
    public void iClickOnElementWithXpath(String xpath) {
        WebElement element = Waiter.waitForElementClickable(driver, By.xpath(xpath));
        element.click();
        logger.info("Clicked on element: {}", xpath);
    }

    @When("I click on element with id {string}")
    public void iClickOnElementWithId(String id) {
        try {
            WebElement element = Waiter.waitForElementClickable(driver, By.id(id));
            element.click();
            logger.info("Clicked on element with id: {}", id);
        } catch (Exception e) {
            logger.warn("Failed to click element with id: {}, trying CSS fallback", id);
            // Fallback: try CSS selector
            try {
                WebElement element = Waiter.waitForElementClickable(driver, By.cssSelector("#" + id));
                element.click();
                logger.info("Clicked on element with CSS: #{}", id);
            } catch (Exception e2) {
                logger.error("Failed to click element with id {} using both methods", id);
                throw new RuntimeException("Could not click element with id: " + id, e2);
            }
        }
    }

    @When("I click on element with css {string}")
    public void iClickOnElementWithCss(String css) {
        WebElement element = Waiter.waitForElementClickable(driver, By.cssSelector(css));
        element.click();
        logger.info("Clicked on element with css: {}", css);
    }

    @Then("I click on element using JavaScript with xpath {string}")
    public void iClickOnElementUsingJavaScriptWithXpath(String xpath) {
        WebElement element = Waiter.waitForElementPresent(driver, By.xpath(xpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        logger.info("Clicked using JavaScript on element: {}", xpath);
    }

    @Then("I click on element using JavaScript with id {string}")
    public void iClickOnElementUsingJavaScriptWithId(String id) {
        WebElement element = Waiter.waitForElementPresent(driver, By.id(id));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        logger.info("Clicked using JavaScript on element with id: {}", id);
    }

    @Then("I click on element using JavaScript with css {string}")
    public void iClickOnElementUsingJavaScriptWithCss(String css) {
        WebElement element = Waiter.waitForElementPresent(driver, By.cssSelector(css));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        logger.info("Clicked using JavaScript on element with css: {}", css);
    }

    // Wait Steps
    @Then("I wait for {int} seconds")
    public void iWaitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            logger.info("Waited for {} seconds", seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Wait interrupted", e);
        }
    }

    @Then("I wait for element with xpath {string} to be present")
    public void iWaitForElementWithXpathToBePresent(String xpath) {
        Waiter.waitForElementPresent(driver, By.xpath(xpath));
        logger.info("Waited for element to be present: {}", xpath);
    }

    @Then("I wait for element with id {string} to be present")
    public void iWaitForElementWithIdToBePresent(String id) {
        Waiter.waitForElementPresent(driver, By.id(id));
        logger.info("Waited for element with id to be present: {}", id);
    }

    @Then("I wait for element with css {string} to be present")
    public void iWaitForElementWithCssToBePresent(String css) {
        Waiter.waitForElementPresent(driver, By.cssSelector(css));
        logger.info("Waited for element with css to be present: {}", css);
    }

    @Then("I wait for element with xpath {string} to be visible")
    public void iWaitForElementWithXpathToBeVisible(String xpath) {
        Waiter.waitForElementVisible(driver, By.xpath(xpath));
        logger.info("Waited for element to be visible: {}", xpath);
    }

    @Then("I wait for element with xpath {string} to be clickable")
    public void iWaitForElementWithXpathToBeClickable(String xpath) {
        Waiter.waitForElementClickable(driver, By.xpath(xpath));
        logger.info("Waited for element to be clickable: {}", xpath);
    }

    @Then("I wait for element with xpath {string} to disappear")
    public void iWaitForElementWithXpathToDisappear(String xpath) {
        Waiter.waitForElementInvisible(driver, By.xpath(xpath));
        logger.info("Waited for element to disappear: {}", xpath);
    }

    // Text Verification Steps
    @Then("element with xpath {string} should contain text {string}")
    public void elementWithXpathShouldContainText(String xpath, String expectedText) {
        WebElement element = Waiter.waitForElementVisible(driver, By.xpath(xpath));
        String actualText = element.getText();
        Assertions.assertThat(actualText)
            .as("Element text should contain: " + expectedText)
            .contains(expectedText);
        logger.info("Verified element contains text: {}", expectedText);
    }

    @Then("element with id {string} should contain text {string}")
    public void elementWithIdShouldContainText(String id, String expectedText) {
        WebElement element = Waiter.waitForElementVisible(driver, By.id(id));
        String actualText = element.getText();
        Assertions.assertThat(actualText)
            .as("Element text should contain: " + expectedText)
            .contains(expectedText);
        logger.info("Verified element with id contains text: {}", expectedText);
    }

    @Then("element with css {string} should contain text {string}")
    public void elementWithCssShouldContainText(String css, String expectedText) {
        WebElement element = Waiter.waitForElementVisible(driver, By.cssSelector(css));
        String actualText = element.getText();
        Assertions.assertThat(actualText)
            .as("Element text should contain: " + expectedText)
            .contains(expectedText);
        logger.info("Verified element with css contains text: {}", expectedText);
    }

    @Then("element with xpath {string} should have text {string}")
    public void elementWithXpathShouldHaveText(String xpath, String expectedText) {
        WebElement element = Waiter.waitForElementVisible(driver, By.xpath(xpath));
        String actualText = element.getText();
        Assertions.assertThat(actualText)
            .as("Element text should match")
            .isEqualTo(expectedText);
        logger.info("Verified element text matches: {}", expectedText);
    }

    @Then("element with id {string} should have text {string}")
    public void elementWithIdShouldHaveText(String id, String expectedText) {
        WebElement element = Waiter.waitForElementVisible(driver, By.id(id));
        String actualText = element.getText();
        Assertions.assertThat(actualText)
            .as("Element text should match")
            .isEqualTo(expectedText);
        logger.info("Verified element with id text matches: {}", expectedText);
    }

    // Attribute Verification Steps
    @Then("element with xpath {string} should have attribute {string} with value {string}")
    public void elementWithXpathShouldHaveAttributeWithValue(String xpath, String attribute, String expectedValue) {
        WebElement element = Waiter.waitForElementPresent(driver, By.xpath(xpath));
        String actualValue = element.getAttribute(attribute);
        Assertions.assertThat(actualValue)
            .as("Element attribute should match")
            .isEqualTo(expectedValue);
        logger.info("Verified element attribute '{}' = '{}'", attribute, expectedValue);
    }

    @Then("element with id {string} should have attribute {string} with value {string}")
    public void elementWithIdShouldHaveAttributeWithValue(String id, String attribute, String expectedValue) {
        WebElement element = Waiter.waitForElementPresent(driver, By.id(id));
        String actualValue = element.getAttribute(attribute);
        Assertions.assertThat(actualValue)
            .as("Element attribute should match")
            .isEqualTo(expectedValue);
        logger.info("Verified element with id attribute '{}' = '{}'", attribute, expectedValue);
    }

    // Checkbox and Radio Steps
    @When("I check checkbox with xpath {string}")
    public void iCheckCheckboxWithXpath(String xpath) {
        WebElement element = Waiter.waitForElementClickable(driver, By.xpath(xpath));
        if (!element.isSelected()) {
            element.click();
            logger.info("Checked checkbox: {}", xpath);
        }
    }

    @When("I check checkbox with id {string}")
    public void iCheckCheckboxWithId(String id) {
        WebElement element = Waiter.waitForElementClickable(driver, By.id(id));
        if (!element.isSelected()) {
            element.click();
            logger.info("Checked checkbox with id: {}", id);
        }
    }

    @When("I uncheck checkbox with xpath {string}")
    public void iUncheckCheckboxWithXpath(String xpath) {
        WebElement element = Waiter.waitForElementClickable(driver, By.xpath(xpath));
        if (element.isSelected()) {
            element.click();
            logger.info("Unchecked checkbox: {}", xpath);
        }
    }

    @Then("checkbox with xpath {string} should be selected")
    public void checkboxWithXpathShouldBeSelected(String xpath) {
        WebElement element = Waiter.waitForElementPresent(driver, By.xpath(xpath));
        Assertions.assertThat(element.isSelected())
            .as("Checkbox should be selected")
            .isTrue();
        logger.info("Verified checkbox is selected: {}", xpath);
    }

    @Then("checkbox with id {string} should be selected")
    public void checkboxWithIdShouldBeSelected(String id) {
        WebElement element = Waiter.waitForElementPresent(driver, By.id(id));
        Assertions.assertThat(element.isSelected())
            .as("Checkbox should be selected")
            .isTrue();
        logger.info("Verified checkbox with id is selected: {}", id);
    }

    // Dropdown Steps
    @When("I select {string} from dropdown with xpath {string}")
    public void iSelectFromDropdownWithXpath(String optionText, String xpath) {
        WebElement element = Waiter.waitForElementVisible(driver, By.xpath(xpath));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(element);
        select.selectByVisibleText(optionText);
        logger.info("Selected '{}' from dropdown: {}", optionText, xpath);
    }

    @When("I select {string} from dropdown with id {string}")
    public void iSelectFromDropdownWithId(String optionText, String id) {
        WebElement element = Waiter.waitForElementVisible(driver, By.id(id));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(element);
        select.selectByVisibleText(optionText);
        logger.info("Selected '{}' from dropdown with id: {}", optionText, id);
    }

    @When("I select option by index {int} from dropdown with xpath {string}")
    public void iSelectOptionByIndexFromDropdownWithXpath(int index, String xpath) {
        WebElement element = Waiter.waitForElementVisible(driver, By.xpath(xpath));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(element);
        select.selectByIndex(index);
        logger.info("Selected option at index {} from dropdown: {}", index, xpath);
    }

    // Link Steps
    @When("I click on link text {string}")
    public void iClickOnLinkText(String linkText) {
        WebElement element = Waiter.waitForElementClickable(driver, By.linkText(linkText));
        element.click();
        logger.info("Clicked on link: {}", linkText);
    }

    @When("I click on partial link text {string}")
    public void iClickOnPartialLinkText(String partialLinkText) {
        WebElement element = Waiter.waitForElementClickable(driver, By.partialLinkText(partialLinkText));
        element.click();
        logger.info("Clicked on partial link: {}", partialLinkText);
    }

    // JavaScript Steps
    @When("I execute JavaScript {string}")
    public void iExecuteJavaScript(String script) {
        ((JavascriptExecutor) driver).executeScript(script);
        logger.info("Executed JavaScript: {}", script);
    }

    @When("I scroll to element with xpath {string}")
    public void iScrollToElementWithXpath(String xpath) {
        WebElement element = Waiter.waitForElementPresent(driver, By.xpath(xpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        logger.info("Scrolled to element: {}", xpath);
    }

    @When("I scroll to element with id {string}")
    public void iScrollToElementWithId(String id) {
        WebElement element = Waiter.waitForElementPresent(driver, By.id(id));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        logger.info("Scrolled to element with id: {}", id);
    }

    // Count Steps
    @Then("I should see {int} elements with xpath {string}")
    public void iShouldSeeElementsWithXpath(int expectedCount, String xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        Assertions.assertThat(elements.size())
            .as("Number of elements should match")
            .isEqualTo(expectedCount);
        logger.info("Verified {} elements found", expectedCount);
    }

    @Then("I should see at least {int} elements with xpath {string}")
    public void iShouldSeeAtLeastElementsWithXpath(int minCount, String xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        Assertions.assertThat(elements.size())
            .as("Number of elements should be at least " + minCount)
            .isGreaterThanOrEqualTo(minCount);
        logger.info("Verified at least {} elements found", minCount);
    }
}
