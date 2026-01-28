package com.nopcommerce.pages.products;

import com.nopcommerce.core.base.BasePage;
import com.nopcommerce.utils.waits.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ProductPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ProductPage.class);
    
    // Product title and price - no IDs available, using CSS
    private static final By PRODUCT_TITLE = By.cssSelector(".product-name h1, .product-title h1");
    private static final By PRODUCT_PRICE = By.cssSelector(".product-price span, .actual-price");
    
    // Add-to-cart button - ID first, then name, then CSS fallback
    private static final By ADD_TO_CART_BUTTON_ID = By.id("add-to-cart-button");
    private static final By ADD_TO_CART_BUTTON_NAME = By.name("addtocart");
    private static final By ADD_TO_CART_BUTTON_CSS = By.cssSelector("button.add-to-cart-button, input[value='Add to cart'], button[type='submit'][id*='add-to-cart'], #add-to-cart-button-1, button[id*='add-to-cart']");
    
    // Quantity input - using ID
    private static final By QUANTITY_INPUT = By.id("addtocart_EnteredQuantity");
    
    // Success message - no ID available, using CSS
    private static final By SUCCESS_MESSAGE = By.cssSelector(".bar-notification.success, .bar-notification .content");
    
    // Shopping cart link - try ID first
    private static final By SHOPPING_CART_LINK_ID = By.id("topcartlink");
    private static final By SHOPPING_CART_LINK = By.cssSelector("a.ico-cart");

    public String getProductTitle() {
        return getText(PRODUCT_TITLE);
    }

    public String getProductPrice() {
        return getText(PRODUCT_PRICE);
    }

    public void setQuantity(int quantity) {
        try {
            if (isElementVisible(QUANTITY_INPUT)) {
                sendKeys(QUANTITY_INPUT, String.valueOf(quantity));
                logger.info("Set quantity to: {}", quantity);
            }
        } catch (Exception e) {
            logger.warn("Could not set quantity, continuing with default: {}", e.getMessage());
        }
    }

    public void clickAddToCart() {
        // Wait for page to be ready first
        waitForPageToLoad();
        
        // Try multiple locators in order of preference with shorter timeouts for fallbacks
        WebElement addToCartButton = null;
        Exception lastException = null;
        
        // Try ID first (most common in nopCommerce) - full timeout
        try {
            addToCartButton = Waiter.waitForElementClickable(driver, ADD_TO_CART_BUTTON_ID);
            logger.debug("Found add-to-cart button by ID: add-to-cart-button");
        } catch (Exception e) {
            lastException = e;
            logger.debug("Add-to-cart button not found by ID after full wait, trying name attribute");
        }
        
        // Try name attribute - shorter timeout (5 seconds)
        if (addToCartButton == null) {
            try {
                addToCartButton = waitForElementClickableWithTimeout(ADD_TO_CART_BUTTON_NAME, 5);
                logger.debug("Found add-to-cart button by name: addtocart");
            } catch (Exception e) {
                lastException = e;
                logger.debug("Add-to-cart button not found by name, trying CSS selectors");
            }
        }
        
        // Try CSS selector (multiple variations) - shorter timeout (5 seconds)
        if (addToCartButton == null) {
            try {
                addToCartButton = waitForElementClickableWithTimeout(ADD_TO_CART_BUTTON_CSS, 5);
                logger.debug("Found add-to-cart button by CSS selector");
            } catch (Exception e) {
                lastException = e;
                logger.error("Could not find add-to-cart button with any locator. Current URL: {}", driver.getCurrentUrl());
                
                // Provide helpful error message with debugging info
                String errorMsg = String.format(
                    "Add-to-cart button not found on product page. URL: %s. " +
                    "Tried locators: id='add-to-cart-button', name='addtocart', and CSS selectors. " +
                    "Please verify: 1) Product page has loaded, 2) Product is in stock, 3) Button exists on page.",
                    driver.getCurrentUrl()
                );
                throw new RuntimeException(errorMsg, lastException);
            }
        }
        
        if (addToCartButton != null) {
            addToCartButton.click();
            logger.info("Successfully clicked add-to-cart button");
        }
    }
    
    private WebElement waitForElementClickableWithTimeout(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(
            driver, 
            Duration.ofSeconds(timeoutSeconds)
        );
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void addToCart(int quantity) {
        // Wait for page to be ready
        waitForPageToLoad();
        
        if (quantity > 1) {
            setQuantity(quantity);
        }
        clickAddToCart();
        
        // Wait for success notification to appear and then disappear (if it appears)
        waitForAddToCartSuccess();
    }
    
    private void waitForAddToCartSuccess() {
        try {
            // Wait a moment for notification to appear
            Thread.sleep(500);
            
            // Check if success message is visible
            if (isElementVisible(SUCCESS_MESSAGE)) {
                logger.debug("Add-to-cart success message displayed, waiting for auto-dismiss");
                // Wait for it to auto-dismiss (nopCommerce notifications auto-close after a few seconds)
                try {
                    Waiter.waitForElementInvisible(driver, SUCCESS_MESSAGE);
                    logger.debug("Success notification auto-dismissed");
                } catch (Exception e) {
                    logger.debug("Notification did not auto-dismiss within timeout, continuing");
                }
            } else {
                logger.debug("No success notification found, product may have been added silently");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.debug("Success notification handling: {}", e.getMessage());
        }
        
        // Small wait to ensure page is stable after notification
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void waitForPageToLoad() {
        // Wait for product title to be visible (indicates page loaded)
        try {
            Waiter.waitForElementVisible(driver, PRODUCT_TITLE);
            logger.debug("Product page loaded - title visible");
        } catch (Exception e) {
            logger.warn("Product title not found, page may not be fully loaded: {}", e.getMessage());
        }
        
        // Wait a moment for any dynamic content to load
        try {
            Thread.sleep(1000); // Increased wait for page stability
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isAddToCartSuccessMessageDisplayed() {
        return isElementVisible(SUCCESS_MESSAGE);
    }

    public void clickShoppingCartLink() {
        // Try ID first, then CSS fallback
        if (isElementVisible(SHOPPING_CART_LINK_ID)) {
            click(SHOPPING_CART_LINK_ID);
        } else if (isElementVisible(SHOPPING_CART_LINK)) {
            click(SHOPPING_CART_LINK);
        }
    }
}
