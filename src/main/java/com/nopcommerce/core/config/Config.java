package com.nopcommerce.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static final Properties properties = new Properties();
    private static Config instance;

    private Config() {
        loadProperties();
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private void loadProperties() {
        String env = System.getProperty("env", "local");
        String configFile = "src/test/resources/config/" + env + ".properties";
        
        try (InputStream input = new FileInputStream(configFile)) {
            properties.load(input);
            logger.info("Loaded configuration from: {}", configFile);
        } catch (IOException e) {
            logger.warn("Could not load config file: {}. Using defaults.", configFile);
        }

        // Override with system properties if provided
        overrideWithSystemProperties();
    }

    private void overrideWithSystemProperties() {
        String baseUrl = System.getProperty("baseUrl");
        if (baseUrl != null && !baseUrl.isEmpty()) {
            properties.setProperty("baseUrl", baseUrl);
        }

        String browser = System.getProperty("browser");
        if (browser != null && !browser.isEmpty()) {
            properties.setProperty("browser", browser);
        }

        String headless = System.getProperty("headless");
        if (headless != null && !headless.isEmpty()) {
            properties.setProperty("headless", headless);
        }
    }

    public String getBaseUrl() {
        return properties.getProperty("baseUrl", "https://nop-qa.portnov.com");
    }

    public String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicitWait", "10"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicitWait", "20"));
    }

    public String getTestUserEmail() {
        return properties.getProperty("testUserEmail", "testuser@example.com");
    }

    public String getTestUserPassword() {
        return properties.getProperty("testUserPassword", "Test123!");
    }

    public String getScreenshotPath() {
        return properties.getProperty("screenshotPath", "screenshots/");
    }

    public String getLogPath() {
        return properties.getProperty("logPath", "logs/");
    }
}
