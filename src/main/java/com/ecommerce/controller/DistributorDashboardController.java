package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.service.DistributorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Controller for distributor dashboard endpoints.
 */
@RestController
@RequestMapping("/distributors/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DistributorDashboardController {

    private final DistributorDashboardService distributorDashboardService;

    /**
     * Get distributor dashboard data.
     */
    @GetMapping("/{distributorId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorDashboardDTO>> getDashboard(@PathVariable UUID distributorId) {
        return ResponseEntity.ok(ApiResponse.success(distributorDashboardService.getDashboardData(distributorId)));
    }

    /**
     * Get sales metrics.
     */
    @GetMapping("/{distributorId}/sales-metrics")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<SalesMetricsDTO>> getSalesMetrics(@PathVariable UUID distributorId) {
        return ResponseEntity.ok(ApiResponse.success(distributorDashboardService.getSalesMetrics(distributorId)));
    }

    /**
     * Get sales metrics by date range.
     */
    @GetMapping("/{distributorId}/sales-metrics/range")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<SalesMetricsDTO>> getSalesMetricsByRange(
            @PathVariable UUID distributorId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                distributorDashboardService.getSalesMetricsByRange(distributorId, startDate, endDate)));
    }

    /**
     * Get inventory metrics.
     */
    @GetMapping("/{distributorId}/inventory-metrics")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<InventoryMetricsDTO>> getInventoryMetrics(@PathVariable UUID distributorId) {
        return ResponseEntity.ok(ApiResponse.success(distributorDashboardService.getInventoryMetrics(distributorId)));
    }

    /**
     * Get commission metrics.
     */
    @GetMapping("/{distributorId}/commission-metrics")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<CommissionMetricsDTO>> getCommissionMetrics(@PathVariable UUID distributorId) {
        return ResponseEntity.ok(ApiResponse.success(distributorDashboardService.getCommissionMetrics(distributorId)));
    }

    /**
     * Get recent orders.
     */
    @GetMapping("/{distributorId}/recent-orders")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<java.util.List<RecentOrderDTO>>> getRecentOrders(
            @PathVariable UUID distributorId,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(ApiResponse.success(distributorDashboardService.getRecentOrders(distributorId, limit)));
    }

    /**
     * Get top products.
     */
    @GetMapping("/{distributorId}/top-products")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<java.util.List<TopProductDTO>>> getTopProducts(
            @PathVariable UUID distributorId,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(ApiResponse.success(distributorDashboardService.getTopProducts(distributorId, limit)));
    }

    /**
     * Get distributor summary.
     */
    @GetMapping("/{distributorId}/summary")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorSummaryDTO>> getDistributorSummary(@PathVariable UUID distributorId) {
        return ResponseEntity.ok(ApiResponse.success(distributorDashboardService.getDistributorSummary(distributorId)));
    }

    /**
     * Get low stock products.
     */
    @GetMapping("/{distributorId}/low-stock")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<java.util.List<DistributorInventoryDTO>>> getLowStockProducts(
            @PathVariable UUID distributorId
    ) {
        return ResponseEntity.ok(ApiResponse.success(distributorDashboardService.getLowStockProducts(distributorId)));
    }

    /**
     * Get pending orders count.
     */
    @GetMapping("/{distributorId}/pending-orders-count")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<Long>> getPendingOrdersCount(@PathVariable UUID distributorId) {
        return ResponseEntity.ok(ApiResponse.success(distributorDashboardService.getPendingOrdersCount(distributorId)));
    }

    /**
     * Get pending commissions total.
     */
    @GetMapping("/{distributorId}/pending-commissions")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<java.math.BigDecimal>> getPendingCommissionsTotal(@PathVariable UUID distributorId) {
        return ResponseEntity.ok(ApiResponse.success(distributorDashboardService.getPendingCommissionsTotal(distributorId)));
    }
}
