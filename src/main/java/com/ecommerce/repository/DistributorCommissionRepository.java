package com.ecommerce.repository;

import com.ecommerce.entity.DistributorCommission;
import com.ecommerce.entity.CommissionStatus;
import com.ecommerce.entity.CommissionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for DistributorCommission entity.
 */
@Repository
public interface DistributorCommissionRepository extends JpaRepository<DistributorCommission, UUID> {

    Page<DistributorCommission> findByDistributorId(UUID distributorId, Pageable pageable);

    Page<DistributorCommission> findByStatus(CommissionStatus status, Pageable pageable);

    Page<DistributorCommission> findByDistributorIdAndStatus(UUID distributorId, CommissionStatus status, Pageable pageable);

    List<DistributorCommission> findByDistributorId(UUID distributorId);

    @Query("SELECT SUM(dc.amount) FROM DistributorCommission dc WHERE dc.distributor.id = :distributorId AND dc.status = 'PENDING'")
    Optional<BigDecimal> calculatePendingCommissions(@Param("distributorId") UUID distributorId);

    @Query("SELECT SUM(dc.amount) FROM DistributorCommission dc WHERE dc.distributor.id = :distributorId AND dc.status = 'APPROVED'")
    Optional<BigDecimal> calculateApprovedCommissions(@Param("distributorId") UUID distributorId);

    @Query("SELECT SUM(dc.amount) FROM DistributorCommission dc WHERE dc.distributor.id = :distributorId AND dc.status = 'PAID'")
    Optional<BigDecimal> calculatePaidCommissions(@Param("distributorId") UUID distributorId);

    @Query("SELECT SUM(dc.amount) FROM DistributorCommission dc WHERE dc.distributor.id = :distributorId")
    Optional<BigDecimal> calculateTotalCommissions(@Param("distributorId") UUID distributorId);

    @Query("SELECT dc FROM DistributorCommission dc WHERE dc.distributor.id = :distributorId AND dc.status = 'PENDING'")
    List<DistributorCommission> findPendingCommissions(@Param("distributorId") UUID distributorId);

    @Query("SELECT dc FROM DistributorCommission dc WHERE dc.distributor.id = :distributorId AND dc.isPaid = false AND dc.status IN ('PENDING', 'APPROVED')")
    List<DistributorCommission> findUnpaidCommissions(@Param("distributorId") UUID distributorId);

    @Query("SELECT COUNT(dc) FROM DistributorCommission dc WHERE dc.distributor.id = :distributorId AND dc.status = 'PENDING'")
    long countPendingCommissions(@Param("distributorId") UUID distributorId);

    @Query("SELECT COUNT(dc) FROM DistributorCommission dc WHERE dc.distributor.id = :distributorId AND dc.status = 'PAID'")
    long countPaidCommissions(@Param("distributorId") UUID distributorId);

    @Query("SELECT dc FROM DistributorCommission dc WHERE dc.distributor.id = :distributorId AND dc.periodStartDate BETWEEN :startDate AND :endDate")
    List<DistributorCommission> findByPeriod(
            @Param("distributorId") UUID distributorId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT dc FROM DistributorCommission dc WHERE dc.type = :type AND dc.distributor.id = :distributorId")
    List<DistributorCommission> findByType(@Param("distributorId") UUID distributorId, @Param("type") CommissionType type);

    long countByDistributorId(UUID distributorId);
}
