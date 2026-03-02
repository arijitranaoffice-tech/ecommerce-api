package com.ecommerce.repository;

import com.ecommerce.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for WishlistItem entity.
 */
@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, UUID> {

    boolean existsByWishlistIdAndProductId(UUID wishlistId, UUID productId);

    Optional<WishlistItem> findByWishlistIdAndProductId(UUID wishlistId, UUID productId);

    void deleteByWishlistId(UUID wishlistId);
}
