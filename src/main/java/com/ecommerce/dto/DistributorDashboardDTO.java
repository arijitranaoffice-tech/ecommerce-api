package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for distributor dashboard statistics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorDashboardDTO {

    private String distributorId;

    private String distributorName;

    private String distributorCode;

    private DistributorSummaryDTO summary;

    private SalesMetricsDTO salesMetrics;

    private InventoryMetricsDTO inventoryMetrics;

    private CommissionMetricsDTO commissionMetrics;

    private List<RecentOrderDTO> recentOrders;

    private List<TopProductDTO> topProducts;
}
