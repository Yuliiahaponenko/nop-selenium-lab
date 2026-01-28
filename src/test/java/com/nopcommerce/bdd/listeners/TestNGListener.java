package com.nopcommerce.bdd.listeners;

import com.nopcommerce.core.driver.DriverManager;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestNGListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestNGListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: {}", result.getName());
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        DriverManager.quitDriver();
        logger.info("Test suite finished, driver quit");
    }
}
