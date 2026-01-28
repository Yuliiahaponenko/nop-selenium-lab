package com.nopcommerce.utils.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class UserFactory {
    private static final Logger logger = LoggerFactory.getLogger(UserFactory.class);
    private static final Random random = new Random();

    public static String generateUniqueEmail() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int randomNum = random.nextInt(10000);
        return String.format("testuser_%s_%d@example.com", timestamp, randomNum);
    }

    public static String generateUniqueName(String prefix) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
        int randomNum = random.nextInt(1000);
        return String.format("%s_%s_%d", prefix, timestamp, randomNum);
    }
}
