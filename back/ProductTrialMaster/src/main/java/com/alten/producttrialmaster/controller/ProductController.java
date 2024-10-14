package com.alten.producttrialmaster.controller;

import com.alten.producttrialmaster.dto.ProductDTO;
import com.alten.producttrialmaster.enums.StorageTypeEnum;
import com.alten.producttrialmaster.exception.GetProductException;
import com.alten.producttrialmaster.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    List<ProductDTO> getAllProducts(@RequestParam StorageTypeEnum storageType) throws IOException {
        return productService.getAllProducts(storageType);
    }

    @GetMapping("/{id}")
     ResponseEntity<ProductDTO> getProductById(@RequestParam Long id, @RequestParam StorageTypeEnum storageType)
            throws IOException {
        ProductDTO product = productService.getProductById(id,storageType);
        return ResponseEntity.ok(product);
    }

    @PostMapping
     ResponseEntity<ProductDTO> createProduct(@Valid ProductDTO product, @RequestParam StorageTypeEnum storageType){
        ProductDTO createdProduct = productService.createProduct(product, storageType);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
     ResponseEntity<ProductDTO>  updateProduct(@PathVariable Long id, @RequestParam StorageTypeEnum storageType,
                                                     @Valid @RequestBody ProductDTO product) {
        ProductDTO updatedProduct = productService.updateProduct(id, product,storageType);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
     ResponseEntity<Void> deleteProduct(@RequestParam Long id, @RequestParam StorageTypeEnum storageType) throws IOException {
        boolean isDeleted = productService.deleteProduct(id,storageType);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
