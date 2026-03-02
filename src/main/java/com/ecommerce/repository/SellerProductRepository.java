package com.ecommerce.repository;

import com.ecommerce.entity.SellerProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for SellerProduct entity.
 */
@Repository
public interface SellerProductRepository extends JpaRepository<SellerProduct, UUID> {

    Page<SellerProduct> findBySellerId(UUID sellerId, Pageable pageable);

    Page<SellerProduct> findByProductId(UUID productId, Pageable pageable);

    List<SellerProduct> findBySellerId(UUID sellerId);

    Optional<SellerProduct> findBySellerIdAndProductId(UUID sellerId, UUID productId);

    boolean existsBySellerIdAndProductId(UUID sellerId, UUID productId);

    @Query("SELECT sp FROM SellerProduct sp WHERE sp.seller.id = :sellerId AND sp.isActive = true")
    Page<SellerProduct> findActiveProductsBySeller(@Param("sellerId") UUID sellerId, Pageable pageable);

    @Query("SELECT COUNT(sp) FROM SellerProduct sp WHERE sp.seller.id = :sellerId AND sp.isActive = true")
    long countActiveProductsBySeller(@Param("sellerId") UUID sellerId);

    @Query("SELECT sp FROM SellerProduct sp WHERE sp.seller.id = :sellerId AND sp.isApproved = false")
    List<SellerProduct> findPendingApprovalProducts(@Param("sellerId") UUID sellerId);
}
