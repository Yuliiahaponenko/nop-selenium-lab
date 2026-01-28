package com.nopcommerce.utils.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestDataLoader {
    private static final Logger logger = LoggerFactory.getLogger(TestDataLoader.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.findAndRegisterModules();
    }

    public static <T> List<T> loadTestData(String filePath, Class<T> type) {
        try {
            File file = new File(filePath);
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            CollectionType collectionType = typeFactory.constructCollectionType(List.class, type);
            return objectMapper.readValue(file, collectionType);
        } catch (IOException e) {
            logger.error("Failed to load test data from: {}", filePath, e);
            throw new RuntimeException("Failed to load test data", e);
        }
    }

    public static <T> T loadSingleTestData(String filePath, Class<T> type) {
        try {
            File file = new File(filePath);
            return objectMapper.readValue(file, type);
        } catch (IOException e) {
            logger.error("Failed to load test data from: {}", filePath, e);
            throw new RuntimeException("Failed to load test data", e);
        }
    }
}
