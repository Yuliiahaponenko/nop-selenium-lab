package com.nopcommerce.utils.waits;

import com.nopcommerce.core.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class Waiter {
    private static final Logger logger = LoggerFactory.getLogger(Waiter.class);
    private static final Config config = Config.getInstance();

    public static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
    }

    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        logger.debug("Waiting for element to be visible: {}", locator);
        return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementPresent(WebDriver driver, By locator) {
        logger.debug("Waiting for element to be present: {}", locator);
        return getWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static List<WebElement> waitForElementsVisible(WebDriver driver, By locator) {
        logger.debug("Waiting for elements to be visible: {}", locator);
        return getWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static boolean waitForTextToBePresent(WebDriver driver, By locator, String text) {
        logger.debug("Waiting for text '{}' to be present in: {}", text, locator);
        return getWait(driver).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static boolean waitForUrlContains(WebDriver driver, String urlFragment) {
        logger.debug("Waiting for URL to contain: {}", urlFragment);
        return getWait(driver).until(ExpectedConditions.urlContains(urlFragment));
    }

    public static boolean waitForElementInvisible(WebDriver driver, By locator) {
        logger.debug("Waiting for element to be invisible: {}", locator);
        return getWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
