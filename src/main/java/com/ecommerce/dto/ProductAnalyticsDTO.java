package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for product analytics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAnalyticsDTO {

    private Integer totalProducts;

    private Integer activeProducts;

    private Integer draftProducts;

    private Integer archivedProducts;

    private Integer outOfStockProducts;

    private Integer lowStockProducts;

    private Integer totalCategories;

    private Double averageProductRating;
}
