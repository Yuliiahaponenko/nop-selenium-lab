package com.nopcommerce.pages.account;

import com.nopcommerce.core.base.BasePage;
import com.nopcommerce.utils.waits.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OrderHistoryPage extends BasePage {
    
    // Order list elements - no IDs available, using CSS
    private static final By ORDER_ROWS = By.cssSelector(".order-list .order-item");
    private static final By ORDER_NUMBER_LINKS = By.cssSelector(".order-number a");

    public List<WebElement> getOrderRows() {
        try {
            return Waiter.waitForElementsVisible(driver, ORDER_ROWS);
        } catch (Exception e) {
            return List.of();
        }
    }

    public boolean hasOrders() {
        return !getOrderRows().isEmpty();
    }

    public String getFirstOrderNumber() {
        List<WebElement> orders = getOrderRows();
        if (!orders.isEmpty()) {
            try {
                return orders.get(0).findElement(ORDER_NUMBER_LINKS).getText();
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }

    public boolean isOrderPresent(String orderNumber) {
        List<WebElement> orders = getOrderRows();
        for (WebElement order : orders) {
            try {
                String orderNum = order.findElement(ORDER_NUMBER_LINKS).getText();
                if (orderNum.contains(orderNumber)) {
                    return true;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return false;
    }
}
