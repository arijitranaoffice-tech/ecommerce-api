package com.ecommerce.repository;

import com.ecommerce.entity.SellerOrder;
import com.ecommerce.entity.SellerOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for SellerOrder entity.
 */
@Repository
public interface SellerOrderRepository extends JpaRepository<SellerOrder, UUID> {

    Optional<SellerOrder> findBySellerOrderNumber(String sellerOrderNumber);

    Page<SellerOrder> findBySellerId(UUID sellerId, Pageable pageable);

    Page<SellerOrder> findByStatus(SellerOrderStatus status, Pageable pageable);

    List<SellerOrder> findBySellerId(UUID sellerId);

    @Query("SELECT SUM(so.sellerEarnings) FROM SellerOrder so WHERE so.seller.id = :sellerId")
    Optional<BigDecimal> calculateTotalEarningsBySeller(@Param("sellerId") UUID sellerId);

    @Query("SELECT COUNT(so) FROM SellerOrder so WHERE so.seller.id = :sellerId")
    long countTotalOrdersBySeller(@Param("sellerId") UUID sellerId);

    @Query("SELECT so FROM SellerOrder so WHERE so.seller.id = :sellerId AND so.status = :status")
    List<SellerOrder> findBySellerAndStatus(@Param("sellerId") UUID sellerId, @Param("status") SellerOrderStatus status);
}
