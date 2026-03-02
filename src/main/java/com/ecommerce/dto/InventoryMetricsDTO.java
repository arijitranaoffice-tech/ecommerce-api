package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for inventory metrics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryMetricsDTO {

    private Integer totalProducts;

    private Integer totalQuantityOnHand;

    private Integer totalQuantityAvailable;

    private Integer totalQuantityReserved;

    private Integer lowStockProducts;

    private Integer outOfStockProducts;

    private Integer overstockProducts;

    private Integer totalWarehouses;
}
