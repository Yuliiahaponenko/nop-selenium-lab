package com.nopcommerce.pages;

import com.nopcommerce.core.base.BasePage;
import com.nopcommerce.utils.waits.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);
    
    // Success notification - no ID available, using CSS
    private static final By SUCCESS_NOTIFICATION = By.cssSelector(".bar-notification.success, .bar-notification .content");
    
    // Shopping cart - try ID first, then CSS fallback
    private static final By SHOPPING_CART_LINK_ID = By.id("topcartlink");
    
    public void clickLogin() {
        click(By.linkText("Log in"));
    }

    public void clickRegister() {
        click(By.linkText("Register"));
    }

    public void clickLogout() {
        if (isElementVisible(By.linkText("Log out"))) {
            click(By.linkText("Log out"));
        }
    }

    public boolean isUserLoggedIn() {
        return isElementVisible(By.linkText("Log out"));
    }

    public void searchForProduct(String productName) {
        sendKeys(By.id("small-searchterms"), productName);
        // Search button: prefer ID, but DON'T wait 20s for an ID that doesn't exist.
        // This environment seems not to have `small-search-box-button`.
        if (!driver.findElements(By.id("small-search-box-button")).isEmpty()) {
            click(By.id("small-search-box-button"));
            return;
        }
        click(By.cssSelector("button[type='submit'].search-box-button"));
    }

    public void clickShoppingCart() {
        // Wait for success notification to disappear if present
        waitForNotificationToDisappear();
        
        // Try multiple locators for shopping cart - ID first, then CSS fallbacks
        WebElement cartElement = null;
        
        // Try ID first (most reliable)
        try {
            cartElement = Waiter.waitForElementClickable(driver, SHOPPING_CART_LINK_ID);
            logger.debug("Found shopping cart link by ID: topcartlink");
        } catch (Exception e) {
            logger.debug("Shopping cart link not found by ID, trying CSS: a.ico-cart");
            try {
                cartElement = Waiter.waitForElementClickable(driver, By.cssSelector("a.ico-cart"));
                logger.debug("Found shopping cart link by CSS: a.ico-cart");
            } catch (Exception e2) {
                logger.debug("Shopping cart link not found, trying cart-label");
                try {
                    cartElement = Waiter.waitForElementClickable(driver, By.cssSelector(".cart-label"));
                    logger.debug("Found shopping cart by class: cart-label");
                } catch (Exception e3) {
                    logger.debug("Shopping cart not found by class, trying span.cart-label");
                    cartElement = Waiter.waitForElementClickable(driver, By.cssSelector("span.cart-label"));
                }
            }
        }
        
        // Try normal click first
        try {
            cartElement.click();
            logger.info("Successfully clicked shopping cart");
        } catch (Exception e) {
            // If click is intercepted, use JavaScript click
            logger.warn("Normal click failed, using JavaScript click: {}", e.getMessage());
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartElement);
                logger.info("Successfully clicked shopping cart using JavaScript");
            } catch (Exception jsException) {
                logger.error("Failed to click shopping cart with both methods");
                throw new RuntimeException("Could not click shopping cart", jsException);
            }
        }
    }
    
    private void waitForNotificationToDisappear() {
        try {
            // Check if notification is visible
            if (isElementVisible(SUCCESS_NOTIFICATION)) {
                logger.debug("Success notification visible, waiting for it to disappear");
                // Wait for notification to become invisible (it auto-dismisses)
                Waiter.waitForElementInvisible(driver, SUCCESS_NOTIFICATION);
                logger.debug("Success notification disappeared");
            }
        } catch (Exception e) {
            logger.debug("No notification found or already disappeared: {}", e.getMessage());
        }
        
        // Small wait to ensure page is stable
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void navigateToHome() {
        String currentUrl = driver.getCurrentUrl();
        String baseUrl = currentUrl.split("/")[0] + "//" + currentUrl.split("/")[2];
        navigateTo(baseUrl);
    }
}
