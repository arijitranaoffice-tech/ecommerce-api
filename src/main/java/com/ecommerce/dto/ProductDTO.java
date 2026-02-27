package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for Product data transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String id;

    private String name;

    private String description;

    private String sku;

    private String slug;

    private String category;

    private BigDecimal price;

    private BigDecimal compareAtPrice;

    private Integer stockQuantity;

    private String status;

    private String brand;

    private List<String> images;

    private Double weight;

    private String weightUnit;

    private Double averageRating;

    private Integer reviewCount;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
