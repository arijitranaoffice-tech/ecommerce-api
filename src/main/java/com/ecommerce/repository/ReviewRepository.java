package com.ecommerce.repository;

import com.ecommerce.entity.Review;
import com.ecommerce.entity.ReviewStatus;
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
 * Repository interface for Review entity.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    Page<Review> findByProductId(UUID productId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.approved = true")
    Page<Review> findApprovedReviewsByProductId(@Param("productId") UUID productId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId")
    Page<Review> findByUserId(@Param("userId") UUID userId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.user.id = :userId")
    Optional<Review> findByProductIdAndUserId(@Param("productId") UUID productId, @Param("userId") UUID userId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId AND r.approved = true")
    long countApprovedReviewsByProductId(@Param("productId") UUID productId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId AND r.approved = true")
    Double findAverageRatingByProductId(@Param("productId") UUID productId);

    List<Review> findByStatus(ReviewStatus status);
}
