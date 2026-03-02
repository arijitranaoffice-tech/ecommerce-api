package com.ecommerce.service;

import com.ecommerce.dto.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for Distributor Dashboard operations.
 */
public interface DistributorDashboardService {

    DistributorDashboardDTO getDashboardData(UUID distributorId);

    DistributorSummaryDTO getDistributorSummary(UUID distributorId);

    SalesMetricsDTO getSalesMetrics(UUID distributorId);

    SalesMetricsDTO getSalesMetricsByRange(UUID distributorId, LocalDate startDate, LocalDate endDate);

    InventoryMetricsDTO getInventoryMetrics(UUID distributorId);

    CommissionMetricsDTO getCommissionMetrics(UUID distributorId);

    List<RecentOrderDTO> getRecentOrders(UUID distributorId, int limit);

    List<TopProductDTO> getTopProducts(UUID distributorId, int limit);

    List<DistributorInventoryDTO> getLowStockProducts(UUID distributorId);

    Long getPendingOrdersCount(UUID distributorId);

    java.math.BigDecimal getPendingCommissionsTotal(UUID distributorId);
}
