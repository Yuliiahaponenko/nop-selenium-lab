package com.nopcommerce.core.driver;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            setDriver(DriverFactory.createDriver());
        }
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            logger.info("Driver quit successfully");
        }
    }

    public static void clearCookies() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.manage().deleteAllCookies();
            logger.debug("Cleared all cookies");
        }
    }
}
