package com.alten.producttrialmaster.service;

import com.alten.producttrialmaster.dto.ProductDTO;
import com.alten.producttrialmaster.entity.Product;
import com.alten.producttrialmaster.enums.StorageTypeEnum;
import com.alten.producttrialmaster.exception.CreateProductException;
import com.alten.producttrialmaster.exception.GetProductException;
import com.alten.producttrialmaster.exception.UnsupportedStorageTypeException;
import com.alten.producttrialmaster.exception.UpdateProductException;
import com.alten.producttrialmaster.mapper.ProductMapper;
import com.alten.producttrialmaster.repository.JsonProductRepository;
import com.alten.producttrialmaster.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductService {

    public static final StorageTypeEnum DATABASE = StorageTypeEnum.DATABASE;
    private static final StorageTypeEnum JSON = StorageTypeEnum.JSON;
    private final ProductRepository productRepository;// For SQL ou NoSQL
    private final JsonProductRepository jsonProductRepository; // For JSON

    public List<ProductDTO> getAllProducts(StorageTypeEnum storageType) throws IOException {
        if (JSON.equals(storageType)) {

            return Optional.of(
                    jsonProductRepository.loadProducts()
                            .stream()
                            .map(ProductMapper::toDTO)
                            .collect(Collectors.toList())
            ).orElse(new ArrayList<>());

        } else if (DATABASE.equals(storageType)) {

            return Optional.of(
                    productRepository.findAll()
                            .stream()
                            .map(ProductMapper::toDTO)
                            .collect(Collectors.toList())
            ).orElse(new ArrayList<>());

        }else {

            throw new UnsupportedStorageTypeException("Unsupported storage type: " + storageType);
        }
    }

    public ProductDTO getProductById(Long id, StorageTypeEnum storageType) throws IOException {
        if (JSON.equals(storageType)) {
            return getAllProducts(JSON).stream().filter(p -> p.getId().equals(id)).findFirst()
                    .orElseThrow(()->new GetProductException("Product with ID " + id + " not found"));
        } else if (DATABASE.equals(storageType)) {
            return productRepository.findById(id).map(ProductMapper::toDTO)
                    .orElseThrow(()->new GetProductException("Product with ID " + id + " not found"));
        }else {
            throw new UnsupportedStorageTypeException("Unsupported storage type: " + storageType);
        }
    }

    public ProductDTO createProduct(ProductDTO product, StorageTypeEnum storageType) {
            handleCreateProduct(product, storageType)
                    .orElseThrow(() -> new CreateProductException("Failed to create product with code: " + product.getCode()));
        return product;
    }

    public ProductDTO updateProduct(Long id, ProductDTO updatedProduct, StorageTypeEnum storageType){
        return handleUpdateProduct(id,updatedProduct,storageType)
                .orElseThrow(()->new UpdateProductException("Failed to update product with id "+id));
    }

    public boolean deleteProduct(Long id, StorageTypeEnum storageType) throws IOException {
        if (JSON.equals(storageType)) {

            List<Product> products = jsonProductRepository.loadProducts();

            // Find product by id
            Optional<Product> productToDelete = products.stream()
                    .filter(product -> product.getId().equals(id))
                    .findFirst();

            // Delete the product if exist , update JSON Storage then return true
            if (productToDelete.isPresent()) {
                products.remove(productToDelete.get());
                jsonProductRepository.saveProducts(products);
                return true;
            }

            // Return false if the product not exist
            return false;

        } else if (DATABASE.equals(storageType)) {

            return productRepository.findById(id)
                    .map(product -> {
                        productRepository.delete(product);
                        return true;
                    })
                    .orElse(false);

        }else {
            throw new UnsupportedStorageTypeException("Unsupported storage type: " + storageType);
        }
    }


    // Handle create product (either JSON or DATABASE)
    private Optional<ProductDTO> handleCreateProduct(ProductDTO product, StorageTypeEnum storageType) {
        if (JSON.equals(storageType)) {
            return handleCreateProductOnJsonStorage(product);

        } else if (DATABASE.equals(storageType)) {
            return handleCreateProductOnDatabaseStorage(product);

        } else {
            throw new UnsupportedStorageTypeException("Unsupported storage type: " + storageType);
        }
    }

    // Handle update product (either JSON or DATABASE)
    private Optional<ProductDTO> handleUpdateProduct(Long id, ProductDTO product, StorageTypeEnum storageType){
        if (JSON.equals(storageType)) {
            return handleUpdateProductOnJsonStorage(id, product);

        } else if (DATABASE.equals(storageType)) {
            return handleUpdateProductOnDatabaseStorage(id, product);

        } else {
            throw new UnsupportedStorageTypeException("Unsupported storage type: " + storageType);
        }
    }

    // Handling create product on JSON storage
    private Optional<ProductDTO> handleCreateProductOnJsonStorage(ProductDTO product) {
        try {
            List<Product> products = jsonProductRepository.loadProducts();
            // Check for duplicate product code
            boolean exists = products.stream()
                    .anyMatch(existingProduct -> existingProduct.getCode().equals(product.getCode()));
            // If the product already exists, throw an exception
            if (exists) {
                throw new CreateProductException("Product with code " + product.getCode() + " already exists.");
            }
            // Set a new ID
            product.setId((long) (products.size() + 1));
            // Set createdAt
            product.setCreatedAt(Instant.now().getEpochSecond());
            // Add the product and save it back to JSON storage
            products.add(ProductMapper.toEntity(product));
            jsonProductRepository.saveProducts(products);

            // Return the created product
            return Optional.of(product);

        } catch (IOException e) {
            throw new CreateProductException("Error saving product to JSON file: " + e.getMessage());
        }
    }

    // Handling update product on JSON storage
    private Optional<ProductDTO> handleUpdateProductOnJsonStorage(Long id, ProductDTO updatedProduct) {
        try{

            List<Product> products = jsonProductRepository.loadProducts();

            List<Product> updatedProducts = products.stream()
                .map(
                    product -> product.getId().equals(id) ?
                        ProductMapper.toEntity(updatedProduct.withId(id)) : //ensure the ID remains the same
                        product
                    )
                .collect(Collectors.toList());

            jsonProductRepository.saveProducts(updatedProducts);

            return Optional.of(updatedProducts.stream()
                    .map(ProductMapper::toDTO)
                    .filter(product -> product.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new UpdateProductException("Product with ID " + id + " can't be updated on JSON file")));

        }catch (Exception e) {
            throw new RuntimeException("Error saving product to JSON file: " + e.getMessage());
        }

    }

    // Handling create product on Database storage
    private Optional<ProductDTO> handleCreateProductOnDatabaseStorage(ProductDTO product) {
        // Find product by code
        Optional<Product> existingProduct = Optional.ofNullable(productRepository.findByCode(product.getCode()));

        // If the product already exists, throw an exception
        if (existingProduct.isPresent()) {
            throw new CreateProductException("Product with code " + product.getCode() + " already exists in the database.");
        }

        // Otherwise, save the new product and return it
        productRepository.save(ProductMapper.toEntity(product));
        return Optional.of(product);
    }

    // Handling update product on Database storage
    private Optional<ProductDTO> handleUpdateProductOnDatabaseStorage(Long id, ProductDTO updatedProduct) {
        return Optional.ofNullable(productRepository
                .findById(id)
                .flatMap(product -> {
                    // Create a new product with the existent ID
                    Product updatedProductWithExistentId = ProductMapper.toEntity(updatedProduct.withId(id));

                    // Save and return the updated product
                    return Optional.of(productRepository.save(updatedProductWithExistentId)).map(ProductMapper::toDTO);

                })
                .orElseThrow(()->new UpdateProductException("Product with ID " + id + " can't be updated on database")));
    }
}
