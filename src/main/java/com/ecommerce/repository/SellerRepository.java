package com.ecommerce.repository;

import com.ecommerce.entity.Seller;
import com.ecommerce.entity.SellerStatus;
import com.ecommerce.entity.SellerTier;
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
 * Repository for Seller entity.
 */
@Repository
public interface SellerRepository extends JpaRepository<Seller, UUID> {

    Optional<Seller> findBySellerCode(String sellerCode);

    Optional<Seller> findByUserId(UUID userId);

    Optional<Seller> findByUserEmail(String email);

    boolean existsByUserEmail(String email);

    boolean existsBySellerCode(String sellerCode);

    boolean existsByUserId(UUID userId);

    Page<Seller> findByStatus(SellerStatus status, Pageable pageable);

    Page<Seller> findByTier(SellerTier tier, Pageable pageable);

    @Query("SELECT s FROM Seller s WHERE s.storeName LIKE %:keyword% OR s.user.firstName LIKE %:keyword%")
    Page<Seller> searchSellers(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT s FROM Seller s WHERE s.status = 'ACTIVE'")
    Page<Seller> findActiveSellers(Pageable pageable);

    @Query("SELECT COUNT(s) FROM Seller s WHERE s.status = 'ACTIVE'")
    long countActiveSellers();

    @Query("SELECT s FROM Seller s WHERE s.isVerified = true AND s.status = 'ACTIVE'")
    List<Seller> findVerifiedSellers();
}
