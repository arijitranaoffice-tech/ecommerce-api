package com.ecommerce.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * DTO for creating/updating products.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    private String sku;

    private String slug;

    @NotNull(message = "Category is required")
    private UUID categoryId;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @DecimalMin(value = "0.0", message = "Compare at price must be non-negative")
    private BigDecimal compareAtPrice;

    @NotNull(message = "Stock quantity is required")
    private Integer stockQuantity;

    private String brand;

    private List<String> images;

    private Double weight;

    private String weightUnit;

    private Double length;

    private Double width;

    private Double height;

    private String dimensionUnit;

    private Boolean trackInventory;

    private Boolean requiresShipping;

    private Boolean taxable;

    private String taxCode;
}
