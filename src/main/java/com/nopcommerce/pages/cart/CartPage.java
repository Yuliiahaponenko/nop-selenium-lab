package com.nopcommerce.pages.cart;

import com.nopcommerce.core.base.BasePage;
import com.nopcommerce.utils.waits.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CartPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);
    
    // Cart items - no IDs available, using CSS
    private static final By CART_ITEMS = By.cssSelector(".cart-item-row");
    
    // Quantity input - try ID first, then CSS fallback
    private static final By QUANTITY_INPUT_ID = By.id("itemquantity");
    private static final By QUANTITY_INPUT = By.cssSelector("input.qty-input");
    
    // Update cart button - try ID first, then CSS fallback
    private static final By UPDATE_CART_BUTTON_ID = By.id("updatecart");
    private static final By UPDATE_CART_BUTTON = By.cssSelector("button.update-cart-button");
    
    // Remove button - no ID available, using CSS
    private static final By REMOVE_BUTTON = By.cssSelector("button.remove-btn");
    
    // Empty cart message - no ID available, using CSS
    private static final By EMPTY_CART_MESSAGE = By.cssSelector(".no-data");
    
    // Checkout button - using ID
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    
    // Terms checkbox - using ID
    private static final By TERMS_CHECKBOX = By.id("termsofservice");
    
    // Cart total - no ID available, using CSS
    private static final By CART_TOTAL = By.cssSelector(".order-total .value-summary");

    public List<WebElement> getCartItems() {
        try {
            return Waiter.waitForElementsVisible(driver, CART_ITEMS);
        } catch (Exception e) {
            return List.of();
        }
    }

    public boolean isCartEmpty() {
        return isElementVisible(EMPTY_CART_MESSAGE) || getCartItems().isEmpty();
    }

    public void updateQuantity(int quantity) {
        if (!isCartEmpty()) {
            // Try ID first, then CSS fallback
            WebElement quantityInput = null;
            try {
                quantityInput = driver.findElement(QUANTITY_INPUT_ID);
            } catch (Exception e) {
                quantityInput = driver.findElement(QUANTITY_INPUT);
            }
            quantityInput.clear();
            quantityInput.sendKeys(String.valueOf(quantity));
            
            // Try ID first, then CSS fallback
            if (isElementVisible(UPDATE_CART_BUTTON_ID)) {
                click(UPDATE_CART_BUTTON_ID);
            } else {
                click(UPDATE_CART_BUTTON);
            }
        }
    }

    public void removeItem() {
        if (!isCartEmpty() && isElementVisible(REMOVE_BUTTON)) {
            click(REMOVE_BUTTON);
        }
    }

    public void clickCheckout() {
        // Accept terms if checkbox is present
        if (isElementVisible(TERMS_CHECKBOX)) {
            try {
                WebElement termsCheckbox = Waiter.waitForElementClickable(driver, TERMS_CHECKBOX);
                if (!termsCheckbox.isSelected()) {
                    termsCheckbox.click();
                    logger.debug("Accepted terms and conditions");
                }
            } catch (Exception e) {
                logger.warn("Could not click terms checkbox: {}", e.getMessage());
            }
        }
        
        // Click checkout button
        click(CHECKOUT_BUTTON);
        logger.info("Clicked checkout button, navigating to checkout page");
        
        // Wait a moment for navigation to start
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getCartTotal() {
        if (isElementVisible(CART_TOTAL)) {
            return getText(CART_TOTAL);
        }
        return "";
    }

    public int getCartItemCount() {
        return getCartItems().size();
    }
}
