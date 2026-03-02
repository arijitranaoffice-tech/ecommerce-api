package com.ecommerce.service.impl;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import com.ecommerce.mapper.DistributorMapper;
import com.ecommerce.repository.*;
import com.ecommerce.service.DistributorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of DistributorDashboardService.
 */
@Service
@RequiredArgsConstructor
public class DistributorDashboardServiceImpl implements DistributorDashboardService {

    private final DistributorRepository distributorRepository;
    private final DistributorOrderRepository distributorOrderRepository;
    private final DistributorCommissionRepository distributorCommissionRepository;
    private final DistributorInventoryRepository distributorInventoryRepository;
    private final DistributorCustomerRepository distributorCustomerRepository;
    private final WarehouseRepository warehouseRepository;
    private final DistributorProductRepository distributorProductRepository;
    private final DistributorMapper distributorMapper;

    @Override
    public DistributorDashboardDTO getDashboardData(UUID distributorId) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new RuntimeException("Distributor not found"));

        return DistributorDashboardDTO.builder()
                .distributorId(distributor.getId().toString())
                .distributorName(distributor.getCompanyName())
                .distributorCode(distributor.getDistributorCode())
                .summary(getDistributorSummary(distributorId))
                .salesMetrics(getSalesMetrics(distributorId))
                .inventoryMetrics(getInventoryMetrics(distributorId))
                .commissionMetrics(getCommissionMetrics(distributorId))
                .recentOrders(getRecentOrders(distributorId, 5))
                .topProducts(getTopProducts(distributorId, 5))
                .build();
    }

    @Override
    public DistributorSummaryDTO getDistributorSummary(UUID distributorId) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new RuntimeException("Distributor not found"));

        BigDecimal availableCredit = distributor.getCreditLimit().subtract(distributor.getCurrentBalance());
        long activeProducts = distributorProductRepository.countActiveProductsByDistributor(distributorId);
        long activeCustomers = distributorCustomerRepository.countActiveCustomers(distributorId);
        long totalOrders = distributorOrderRepository.countTotalOrdersByDistributor(distributorId);

        return DistributorSummaryDTO.builder()
                .tier(distributor.getTier().name())
                .status(distributor.getStatus().name())
                .commissionRate(distributor.getCommissionRate())
                .creditLimit(distributor.getCreditLimit())
                .currentBalance(distributor.getCurrentBalance())
                .availableCredit(availableCredit)
                .totalProducts((int) activeProducts)
                .totalCustomers((int) activeCustomers)
                .totalOrders((int) totalOrders)
                .build();
    }

    @Override
    public SalesMetricsDTO getSalesMetrics(UUID distributorId) {
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusWeeks(1);
        LocalDate monthAgo = today.minusMonths(1);
        LocalDate quarterAgo = today.minusMonths(3);
        LocalDate yearAgo = today.minusYears(1);

        BigDecimal todaySales = getRevenueByDateRange(distributorId, today.atStartOfDay(), today.atTime(LocalTime.MAX));
        BigDecimal weekSales = getRevenueByDateRange(distributorId, weekAgo.atStartOfDay(), today.atTime(LocalTime.MAX));
        BigDecimal monthSales = getRevenueByDateRange(distributorId, monthAgo.atStartOfDay(), today.atTime(LocalTime.MAX));
        BigDecimal quarterSales = getRevenueByDateRange(distributorId, quarterAgo.atStartOfDay(), today.atTime(LocalTime.MAX));
        BigDecimal yearSales = getRevenueByDateRange(distributorId, yearAgo.atStartOfDay(), today.atTime(LocalTime.MAX));
        BigDecimal totalSales = distributorOrderRepository.calculateTotalRevenueByDistributor(distributorId)
                .orElse(BigDecimal.ZERO);

        long totalOrders = countValidOrders(distributorOrderRepository.findByDistributorId(distributorId));
        long todayOrders = countValidOrdersByDateRange(distributorId, today.atStartOfDay(), today.atTime(LocalTime.MAX));
        long weekOrders = countValidOrdersByDateRange(distributorId, weekAgo.atStartOfDay(), today.atTime(LocalTime.MAX));
        long monthOrders = countValidOrdersByDateRange(distributorId, monthAgo.atStartOfDay(), today.atTime(LocalTime.MAX));
        long quarterOrders = countValidOrdersByDateRange(distributorId, quarterAgo.atStartOfDay(), today.atTime(LocalTime.MAX));
        long yearOrders = countValidOrdersByDateRange(distributorId, yearAgo.atStartOfDay(), today.atTime(LocalTime.MAX));

        BigDecimal averageOrderValue = totalOrders > 0 ? totalSales.divide(BigDecimal.valueOf(totalOrders), 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;

        return SalesMetricsDTO.builder()
                .todaySales(todaySales)
                .weekSales(weekSales)
                .monthSales(monthSales)
                .quarterSales(quarterSales)
                .yearSales(yearSales)
                .totalSales(totalSales)
                .todayOrders((int) todayOrders)
                .weekOrders((int) weekOrders)
                .monthOrders((int) monthOrders)
                .quarterOrders((int) quarterOrders)
                .yearOrders((int) yearOrders)
                .totalOrders((int) totalOrders)
                .averageOrderValue(averageOrderValue)
                .build();
    }

    @Override
    public SalesMetricsDTO getSalesMetricsByRange(UUID distributorId, LocalDate startDate, LocalDate endDate) {
        BigDecimal sales = getRevenueByDateRange(distributorId, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        long ordersCount = countValidOrdersByDateRange(distributorId, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        BigDecimal averageOrderValue = ordersCount > 0 ? sales.divide(BigDecimal.valueOf(ordersCount), 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;

        return SalesMetricsDTO.builder()
                .monthSales(sales)
                .monthOrders((int) ordersCount)
                .averageOrderValue(averageOrderValue)
                .build();
    }

    @Override
    public InventoryMetricsDTO getInventoryMetrics(UUID distributorId) {
        Integer totalQuantityOnHand = distributorInventoryRepository.calculateTotalQuantityOnHand(distributorId)
                .orElse(0);
        Integer totalQuantityAvailable = distributorInventoryRepository.calculateTotalQuantityAvailable(distributorId)
                .orElse(0);

        long lowStockCount = distributorInventoryRepository.countLowStockProducts(distributorId);
        long outOfStockCount = distributorInventoryRepository.countOutOfStockProducts(distributorId);

        List<DistributorInventory> inventories = distributorInventoryRepository.findByDistributorId(distributorId);
        long overstockCount = inventories.stream()
                .filter(inv -> inv.getQuantityAvailable() > (inv.getReorderPoint() * 5))
                .count();

        long activeWarehouses = warehouseRepository.countActiveWarehouses(distributorId);

        return InventoryMetricsDTO.builder()
                .totalProducts(inventories.size())
                .totalQuantityOnHand(totalQuantityOnHand)
                .totalQuantityAvailable(totalQuantityAvailable)
                .totalQuantityReserved(inventories.stream().mapToInt(DistributorInventory::getQuantityReserved).sum())
                .lowStockProducts((int) lowStockCount)
                .outOfStockProducts((int) outOfStockCount)
                .overstockProducts((int) overstockCount)
                .totalWarehouses((int) activeWarehouses)
                .build();
    }

    @Override
    public CommissionMetricsDTO getCommissionMetrics(UUID distributorId) {
        BigDecimal pendingCommissions = distributorCommissionRepository.calculatePendingCommissions(distributorId)
                .orElse(BigDecimal.ZERO);
        BigDecimal approvedCommissions = distributorCommissionRepository.calculateApprovedCommissions(distributorId)
                .orElse(BigDecimal.ZERO);
        BigDecimal paidCommissions = distributorCommissionRepository.calculatePaidCommissions(distributorId)
                .orElse(BigDecimal.ZERO);
        BigDecimal totalCommissions = distributorCommissionRepository.calculateTotalCommissions(distributorId)
                .orElse(BigDecimal.ZERO);

        long pendingCount = distributorCommissionRepository.countPendingCommissions(distributorId);
        long paidCount = distributorCommissionRepository.countPaidCommissions(distributorId);
        long totalCount = distributorCommissionRepository.countByDistributorId(distributorId);

        BigDecimal averageCommissionRate = totalCount > 0 ?
                totalCommissions.divide(BigDecimal.valueOf(totalCount), 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;

        return CommissionMetricsDTO.builder()
                .pendingCommissions(pendingCommissions)
                .approvedCommissions(approvedCommissions)
                .paidCommissions(paidCommissions)
                .totalCommissions(totalCommissions)
                .pendingCount((int) pendingCount)
                .approvedCount((int) (totalCount - pendingCount - paidCount))
                .paidCount((int) paidCount)
                .totalCount((int) totalCount)
                .averageCommissionRate(averageCommissionRate)
                .build();
    }

    @Override
    public List<RecentOrderDTO> getRecentOrders(UUID distributorId, int limit) {
        return distributorOrderRepository.findRecentOrdersByDistributor(distributorId,
                        org.springframework.data.domain.PageRequest.of(0, limit))
                .stream()
                .map(order -> RecentOrderDTO.builder()
                        .id(order.getId().toString())
                        .orderNumber(order.getDistributorOrderNumber())
                        .totalAmount(order.getTotalAmount())
                        .status(order.getStatus().name())
                        .createdAt(order.getCreatedAt() != null ? order.getCreatedAt().toString() : null)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<TopProductDTO> getTopProducts(UUID distributorId, int limit) {
        List<DistributorOrderItem> allItems = distributorOrderRepository.findByDistributorId(distributorId)
                .stream()
                .filter(order -> order.getStatus() != DistributorOrderStatus.CANCELLED &&
                        order.getStatus() != DistributorOrderStatus.REJECTED)
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.toList());

        Map<Product, Integer> productQuantityMap = allItems.stream()
                .collect(Collectors.groupingBy(
                        DistributorOrderItem::getProduct,
                        Collectors.summingInt(DistributorOrderItem::getQuantity)
                ));

        return productQuantityMap.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(limit)
                .map(entry -> {
                    Product product = entry.getKey();
                    int totalQuantity = entry.getValue();
                    BigDecimal totalRevenue = product.getPrice().multiply(BigDecimal.valueOf(totalQuantity));
                    return TopProductDTO.builder()
                            .id(product.getId().toString())
                            .name(product.getName())
                            .sku(product.getSku())
                            .totalQuantitySold(totalQuantity)
                            .totalRevenue(totalRevenue)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DistributorInventoryDTO> getLowStockProducts(UUID distributorId) {
        return distributorInventoryRepository.findLowStockInventory(distributorId)
                .stream()
                .map(distributorMapper::toInventoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long getPendingOrdersCount(UUID distributorId) {
        return (long) distributorOrderRepository.findByDistributorAndStatus(distributorId, DistributorOrderStatus.PENDING)
                .size();
    }

    @Override
    public BigDecimal getPendingCommissionsTotal(UUID distributorId) {
        return distributorCommissionRepository.calculatePendingCommissions(distributorId)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getRevenueByDateRange(UUID distributorId, LocalDateTime startDate, LocalDateTime endDate) {
        return distributorOrderRepository.calculateRevenueByDateRange(distributorId, startDate, endDate)
                .orElse(BigDecimal.ZERO);
    }

    private long countValidOrders(List<DistributorOrder> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() != DistributorOrderStatus.CANCELLED &&
                        order.getStatus() != DistributorOrderStatus.RETURNED &&
                        order.getStatus() != DistributorOrderStatus.REJECTED)
                .count();
    }

    private long countValidOrdersByDateRange(UUID distributorId, LocalDateTime startDate, LocalDateTime endDate) {
        return distributorOrderRepository.findByDistributorId(distributorId)
                .stream()
                .filter(order -> order.getCreatedAt() != null &&
                        !order.getCreatedAt().isBefore(startDate) &&
                        !order.getCreatedAt().isAfter(endDate) &&
                        order.getStatus() != DistributorOrderStatus.CANCELLED &&
                        order.getStatus() != DistributorOrderStatus.RETURNED &&
                        order.getStatus() != DistributorOrderStatus.REJECTED)
                .count();
    }
}
