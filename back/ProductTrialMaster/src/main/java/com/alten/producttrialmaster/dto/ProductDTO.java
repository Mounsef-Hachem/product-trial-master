package com.alten.producttrialmaster.dto;

import com.alten.producttrialmaster.enums.InventoryStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotEmpty
    private String code;
    @NotEmpty
    private String name;
    private String description;
    private String image;
    @NotEmpty
    private String category;
    @Min(value = 0, message = "Price can't be negative.")
    private Double price;
    @Min(value = 0, message = "Quantity can't be negative.")
    private Integer quantity;
    private String internalReference;
    private Long shellId;
    @NotNull(message = "Inventory status is required.")
    private InventoryStatusEnum inventoryStatus;
    @Min(value = 0, message = "Rating can't be negative.")
    @Max(value = 5, message = "Rating can't be more than 5.")
    private Double rating;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long  createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long updatedAt;

    public ProductDTO withId(Long newId) {
        return ProductDTO.builder()
                .id(newId)
                .code(this.code)
                .name(this.name)
                .description(this.description)
                .image(this.image)
                .category(this.category)
                .price(this.price)
                .quantity(this.quantity)
                .internalReference(this.internalReference)
                .shellId(this.shellId)
                .inventoryStatus(this.inventoryStatus)
                .rating(this.rating)

                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)

                .build();
    }
}
