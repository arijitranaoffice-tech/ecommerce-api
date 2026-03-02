package com.ecommerce.repository;

import com.ecommerce.entity.DistributorOrder;
import com.ecommerce.entity.DistributorOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for DistributorOrder entity.
 */
@Repository
public interface DistributorOrderRepository extends JpaRepository<DistributorOrder, UUID> {

    Optional<DistributorOrder> findByDistributorOrderNumber(String distributorOrderNumber);

    Page<DistributorOrder> findByDistributorId(UUID distributorId, Pageable pageable);

    Page<DistributorOrder> findByStatus(DistributorOrderStatus status, Pageable pageable);

    Page<DistributorOrder> findByDistributorIdAndStatus(UUID distributorId, DistributorOrderStatus status, Pageable pageable);

    List<DistributorOrder> findByDistributorId(UUID distributorId);

    @Query("SELECT do FROM DistributorOrder do WHERE do.distributor.id = :distributorId ORDER BY do.createdAt DESC")
    Page<DistributorOrder> findRecentOrdersByDistributor(@Param("distributorId") UUID distributorId, Pageable pageable);

    @Query("SELECT SUM(do.totalAmount) FROM DistributorOrder do WHERE do.distributor.id = :distributorId")
    Optional<BigDecimal> calculateTotalRevenueByDistributor(@Param("distributorId") UUID distributorId);

    @Query("SELECT COUNT(do) FROM DistributorOrder do WHERE do.distributor.id = :distributorId")
    long countTotalOrdersByDistributor(@Param("distributorId") UUID distributorId);

    @Query("SELECT do FROM DistributorOrder do WHERE do.distributor.id = :distributorId AND do.status = :status")
    List<DistributorOrder> findByDistributorAndStatus(@Param("distributorId") UUID distributorId, @Param("status") DistributorOrderStatus status);

    @Query("SELECT SUM(do.totalAmount) FROM DistributorOrder do WHERE do.distributor.id = :distributorId AND do.createdAt BETWEEN :startDate AND :endDate")
    Optional<BigDecimal> calculateRevenueByDateRange(
            @Param("distributorId") UUID distributorId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT do FROM DistributorOrder do WHERE do.order.id = :orderId")
    List<DistributorOrder> findByOrderId(@Param("orderId") UUID orderId);
}
