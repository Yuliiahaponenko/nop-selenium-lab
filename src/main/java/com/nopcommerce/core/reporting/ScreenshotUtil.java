package com.nopcommerce.core.reporting;

import com.nopcommerce.core.config.Config;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final Config config = Config.getInstance();

    public static String takeScreenshot(WebDriver driver, String scenarioName) {
        try {
            Path screenshotDir = Paths.get(config.getScreenshotPath());
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".png";
            Path screenshotPath = screenshotDir.resolve(fileName);

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshotFile.toPath(), screenshotPath);

            logger.info("Screenshot saved: {}", screenshotPath);
            return screenshotPath.toString();
        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
            return null;
        }
    }

    public static byte[] takeScreenshotAsBytes(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to take screenshot as bytes", e);
            return null;
        }
    }
}
