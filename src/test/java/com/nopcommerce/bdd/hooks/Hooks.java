package com.nopcommerce.bdd.hooks;

import com.nopcommerce.bdd.context.TestContext;
import com.nopcommerce.core.config.Config;
import com.nopcommerce.core.driver.DriverManager;
import com.nopcommerce.core.reporting.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        // Ensure driver is initialized
        DriverManager.getDriver();
        Config config = Config.getInstance();
        testContext.getDriver().get(config.getBaseUrl());
    }

    @Before(order = 1)
    public void clearCookies(Scenario scenario) {
        // Skip cookie clearing if scenario is tagged with @preserve-cookies
        if (scenario.getSourceTagNames().contains("@preserve-cookies")) {
            logger.debug("Skipping cookie clear for scenario with @preserve-cookies tag: {}", scenario.getName());
            return;
        }
        
        // Skip cookie clearing if scenario is tagged with @skip-cookie-clear
        if (scenario.getSourceTagNames().contains("@skip-cookie-clear")) {
            logger.debug("Skipping cookie clear for scenario with @skip-cookie-clear tag: {}", scenario.getName());
            return;
        }
        
        // Clear cookies for all other scenarios
        DriverManager.clearCookies();
        logger.debug("Cleared cookies before scenario: {}", scenario.getName());
    }

    @After(order = 1)
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotPath = ScreenshotUtil.takeScreenshot(
                testContext.getDriver(),
                scenario.getName()
            );
            if (screenshotPath != null) {
                try {
                    byte[] screenshotBytes = java.nio.file.Files.readAllBytes(
                        java.nio.file.Paths.get(screenshotPath)
                    );
                    scenario.attach(screenshotBytes, "image/png", "Screenshot on failure");
                    logger.info("Screenshot taken for failed scenario: {}", scenario.getName());
                } catch (Exception e) {
                    logger.error("Failed to attach screenshot", e);
                }
            }
        }
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        logger.info("Completed scenario: {} - Status: {}", 
            scenario.getName(), 
            scenario.getStatus()
        );
        // Note: Driver quit is handled by TestNG listener
    }
}
