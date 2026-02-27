package com.ecommerce.repository;

import com.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for CartItem entity.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    java.util.Optional<CartItem> findByCartIdAndProductId(@Param("cartId") UUID cartId, @Param("productId") UUID productId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId")
    List<CartItem> findAllByCartId(@Param("cartId") UUID cartId);

    long countByCartId(UUID cartId);

    void deleteAllByCartId(UUID cartId);
}
