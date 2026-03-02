package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for Inventory Report.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReportDTO {

    private Integer totalProducts;

    private Integer totalValue;

    private Integer inStockProducts;

    private Integer lowStockProducts;

    private Integer outOfStockProducts;

    private BigDecimal inventoryTurnover;

    private Integer daysOfInventory;
}
