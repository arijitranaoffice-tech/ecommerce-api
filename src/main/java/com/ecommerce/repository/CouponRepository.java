package com.ecommerce.repository;

import com.ecommerce.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Coupon entity.
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, UUID> {

    Optional<Coupon> findByCode(String code);

    Page<Coupon> findByIsActive(Boolean isActive, Pageable pageable);

    List<Coupon> findByIsActiveAndValidFromLessThanEqualAndValidUntilGreaterThanEqual(
            Boolean isActive, LocalDate startDate, LocalDate endDate);

    boolean existsByCode(String code);

    @Query("SELECT c FROM Coupon c WHERE c.code = :code AND c.isActive = true " +
            "AND c.validFrom <= :date AND c.validUntil >= :date " +
            "AND (c.usageLimit = 0 OR c.usedCount < c.usageLimit)")
    Optional<Coupon> findValidCouponByCode(@Param("code") String code, @Param("date") LocalDate date);

    @Modifying
    @Query("UPDATE Coupon c SET c.usedCount = c.usedCount + 1 WHERE c.id = :id")
    void incrementUsageCount(@Param("id") UUID id);

    @Query("SELECT COUNT(c) FROM Coupon c WHERE c.isActive = true")
    long countActiveCoupons();

    List<Coupon> findByValidUntilBefore(LocalDate date);
}
