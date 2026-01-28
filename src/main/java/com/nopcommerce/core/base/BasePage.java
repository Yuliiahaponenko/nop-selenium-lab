package com.nopcommerce.core.base;

import com.nopcommerce.core.driver.DriverManager;
import com.nopcommerce.utils.waits.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {
    protected WebDriver driver;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    protected void click(By locator) {
        WebElement element = Waiter.waitForElementClickable(driver, locator);
        try {
            element.click();
            logger.debug("Clicked element: {}", locator);
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            // If click is intercepted (e.g., by overlay), try JavaScript click
            logger.warn("Click intercepted for {}, trying JavaScript click: {}", locator, e.getMessage());
            try {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                logger.debug("Successfully clicked element using JavaScript: {}", locator);
            } catch (Exception jsException) {
                logger.error("JavaScript click also failed for: {}", locator);
                throw new RuntimeException("Could not click element: " + locator, jsException);
            }
        }
    }

    protected void sendKeys(By locator, String text) {
        WebElement element = Waiter.waitForElementVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
        logger.debug("Entered text '{}' into: {}", text, locator);
    }

    protected String getText(By locator) {
        WebElement element = Waiter.waitForElementVisible(driver, locator);
        return element.getText();
    }

    protected boolean isElementVisible(By locator) {
        try {
            Waiter.waitForElementVisible(driver, locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void navigateTo(String url) {
        driver.get(url);
        logger.info("Navigated to: {}", url);
    }
}
