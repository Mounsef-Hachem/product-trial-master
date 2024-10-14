package com.alten.producttrialmaster.repository;

import com.alten.producttrialmaster.entity.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JsonProductRepository {

    @Value("${product.file.path}")
    private String filePath;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Product> loadProducts() throws IOException {
        File file = new File(filePath);

        // If the file don't exist create new one with an empty list
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            objectMapper.writeValue(file, new ArrayList<>());
        }

        // Load products from the file
        return Optional.ofNullable(
                objectMapper.readValue(
                        file,
                        new TypeReference<List<Product>>() {}
                )).orElse(new ArrayList<>());
    }

    public void saveProducts(List<Product> productEntities) throws IOException {
        objectMapper.writeValue(new File(filePath), productEntities);
    }
}
