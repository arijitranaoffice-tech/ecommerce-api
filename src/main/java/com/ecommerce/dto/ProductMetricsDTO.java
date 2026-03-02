package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Product Metrics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductMetricsDTO {

    private Integer totalProducts;

    private Integer activeProducts;

    private Integer pendingApprovalProducts;

    private Integer lowStockProducts;

    private Integer outOfStockProducts;
}
