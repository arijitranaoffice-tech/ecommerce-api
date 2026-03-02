package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for overview statistics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverviewStatsDTO {

    private BigDecimal totalRevenue;

    private BigDecimal todayRevenue;

    private Integer totalOrders;

    private Integer pendingOrders;

    private Integer totalProducts;

    private Integer lowStockProducts;

    private Integer totalCustomers;

    private Integer newCustomersToday;
}
