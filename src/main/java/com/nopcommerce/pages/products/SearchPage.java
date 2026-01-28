package com.nopcommerce.pages.products;

import com.nopcommerce.core.base.BasePage;
import com.nopcommerce.utils.waits.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPage extends BasePage {
    
    // Product items - no IDs available, using CSS
    private static final By PRODUCT_ITEMS = By.cssSelector(".product-item");
    private static final By PRODUCT_TITLE = By.cssSelector(".product-title a");
    private static final By NO_RESULTS_MESSAGE = By.cssSelector(".no-result");

    public List<WebElement> getProductItems() {
        return Waiter.waitForElementsVisible(driver, PRODUCT_ITEMS);
    }

    public boolean hasSearchResults() {
        try {
            List<WebElement> products = getProductItems();
            return !products.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFirstProduct() {
        List<WebElement> products = getProductItems();
        if (!products.isEmpty()) {
            WebElement firstProduct = products.get(0);
            WebElement productLink = firstProduct.findElement(PRODUCT_TITLE);
            
            // Scroll into view and click
            try {
                ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", productLink);
                Thread.sleep(500); // Small wait after scroll
            } catch (Exception e) {
                logger.warn("Could not scroll to product: {}", e.getMessage());
            }
            
            productLink.click();
            logger.info("Clicked on first product from search results");
            
            // Wait a moment for navigation
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            throw new RuntimeException("No products found in search results to click");
        }
    }

    public String getFirstProductTitle() {
        List<WebElement> products = getProductItems();
        if (!products.isEmpty()) {
            return products.get(0).findElement(PRODUCT_TITLE).getText();
        }
        return "";
    }

    public boolean isNoResultsMessageDisplayed() {
        return isElementVisible(NO_RESULTS_MESSAGE);
    }
}
