package com.ecommerce.repository;

import com.ecommerce.entity.PayPalTransaction;
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
 * Repository for PayPalTransaction entity.
 */
@Repository
public interface PayPalTransactionRepository extends JpaRepository<PayPalTransaction, UUID> {

    Optional<PayPalTransaction> findByPaypalOrderId(String paypalOrderId);

    Optional<PayPalTransaction> findByPaypalPaymentId(String paypalPaymentId);

    Page<PayPalTransaction> findByUserId(UUID userId, Pageable pageable);

    List<PayPalTransaction> findByStatus(String status);

    @Query("SELECT SUM(pt.amount) FROM PayPalTransaction pt WHERE pt.user.id = :userId AND pt.status = 'COMPLETED'")
    Optional<BigDecimal> calculateTotalPaymentsByUser(@Param("userId") UUID userId);

    @Query("SELECT pt FROM PayPalTransaction pt WHERE pt.order.id = :orderId")
    List<PayPalTransaction> findByOrderId(@Param("orderId") UUID orderId);

    @Query("SELECT SUM(pt.amount) FROM PayPalTransaction pt WHERE pt.status = 'COMPLETED' AND pt.capturedAt BETWEEN :startDate AND :endDate")
    Optional<BigDecimal> calculateTotalRevenueByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
