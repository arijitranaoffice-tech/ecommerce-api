package com.ecommerce.service;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.dto.CartItemDTO;

import java.util.UUID;

/**
 * Service interface for Cart operations.
 */
public interface CartService {

    CartDTO getOrCreateCart(UUID userId);

    CartDTO addToCart(UUID userId, CartItemDTO itemDTO);

    CartDTO updateCartItem(UUID userId, UUID itemId, int quantity);

    void removeCartItem(UUID userId, UUID itemId);

    void clearCart(UUID userId);

    CartDTO getCartById(UUID cartId);
}
