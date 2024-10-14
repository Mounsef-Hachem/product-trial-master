package com.alten.producttrialmaster.mapper;

import com.alten.producttrialmaster.dto.ProductDTO;
import com.alten.producttrialmaster.entity.Product;
import com.alten.producttrialmaster.enums.InventoryStatusEnum;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setCode(product.getCode());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImage());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setInternalReference(product.getInternalReference());
        dto.setShellId(product.getShellId());
        dto.setInventoryStatus(InventoryStatusEnum.valueOf(product.getInventoryStatus().toString()));
        dto.setRating(product.getRating());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setCode(dto.getCode());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setInternalReference(dto.getInternalReference());
        product.setShellId(dto.getShellId());
        product.setInventoryStatus(InventoryStatusEnum.valueOf(dto.getInventoryStatus().toString()));
        product.setRating(dto.getRating());
        product.setCreatedAt(dto.getCreatedAt());
        product.setUpdatedAt(dto.getUpdatedAt());
        return product;
    }
}

