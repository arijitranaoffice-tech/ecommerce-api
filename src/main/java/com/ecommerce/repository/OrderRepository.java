package com.ecommerce.repository;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Order entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findByOrderNumber(String orderNumber);

    boolean existsByOrderNumber(String orderNumber);

    Page<Order> findByUser(User user, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.user = :user AND o.status = :status")
    List<Order> findByUserAndStatus(@Param("user") User user, @Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    Page<Order> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.paymentStatus = :paymentStatus")
    List<Order> findPendingPaymentOrders(@Param("status") OrderStatus status, @Param("paymentStatus") com.ecommerce.entity.PaymentStatus paymentStatus);

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.shippedAt IS NULL")
    List<Order> findPendingShipmentOrders(@Param("status") OrderStatus status);

    @Query("""
        SELECT o FROM Order o
        WHERE (:status IS NULL OR o.status = :status)
        AND (:userId IS NULL OR o.user.id = :userId)
        ORDER BY o.createdAt DESC
    """)
    Page<Order> findOrdersWithFilters(
            @Param("status") OrderStatus status,
            @Param("userId") UUID userId,
            Pageable pageable
    );
}
