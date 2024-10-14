package com.alten.producttrialmaster.repository;

import com.alten.producttrialmaster.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByCode(String code);
}
