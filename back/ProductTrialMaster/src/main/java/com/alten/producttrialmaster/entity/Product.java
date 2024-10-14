package com.alten.producttrialmaster.entity;

import com.alten.producttrialmaster.enums.InventoryStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private Double price;
    private Integer quantity;
    private String internalReference;
    private Long shellId;

    @Enumerated(EnumType.STRING)
    private InventoryStatusEnum inventoryStatus;

    private Double rating;

    private Long createdAt;
    private Long updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now().getEpochSecond();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now().getEpochSecond();
    }

}
