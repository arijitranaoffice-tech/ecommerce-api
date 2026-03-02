package com.ecommerce.repository;

import com.ecommerce.entity.Banner;
import com.ecommerce.entity.BannerPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Banner entity.
 */
@Repository
public interface BannerRepository extends JpaRepository<Banner, UUID> {

    Page<Banner> findByIsActive(Boolean isActive, Pageable pageable);

    List<Banner> findByPosition(BannerPosition position);

    @Query("SELECT b FROM Banner b WHERE b.isActive = true " +
            "AND (b.validFrom IS NULL OR b.validFrom <= :date) " +
            "AND (b.validUntil IS NULL OR b.validUntil >= :date) " +
            "ORDER BY b.displayOrder ASC, b.createdAt DESC")
    List<Banner> findActiveBanners(@Param("date") LocalDate date);

    @Query("SELECT b FROM Banner b WHERE b.isActive = true AND b.position = :position " +
            "AND (b.validFrom IS NULL OR b.validFrom <= :date) " +
            "AND (b.validUntil IS NULL OR b.validUntil >= :date) " +
            "ORDER BY b.displayOrder ASC")
    List<Banner> findActiveBannersByPosition(@Param("position") BannerPosition position, @Param("date") LocalDate date);

    long countByIsActive(Boolean isActive);

    List<Banner> findByValidUntilBefore(LocalDate date);
}
