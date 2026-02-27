package com.ecommerce.service.impl;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.dto.CartItemDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.CartStatus;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.CartMapper;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of CartService.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    public CartDTO getOrCreateCart(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        Cart cart = cartRepository.findActiveCartByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .status(CartStatus.ACTIVE)
                            .build();
                    return cartRepository.save(newCart);
                });

        return cartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO addToCart(UUID userId, CartItemDTO itemDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", itemDTO.getProductId()));

        if (product.getStockQuantity() < itemDTO.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }

        Cart cart = cartRepository.findActiveCartByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .status(CartStatus.ACTIVE)
                            .build();
                    return cartRepository.save(newCart);
                });

        // Check if item already exists in cart
        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + itemDTO.getQuantity();
            if (product.getStockQuantity() < newQuantity) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }
            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();
            cartItemRepository.save(newItem);
            cart.addItem(newItem);
        }

        cart.recalculateTotals();
        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toDTO(updatedCart);
    }

    @Override
    @Transactional
    public CartDTO updateCartItem(UUID userId, UUID itemId, int quantity) {
        if (quantity <= 0) {
            removeCartItem(userId, itemId);
            return getOrCreateCart(userId);
        }

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", itemId));

        Product product = item.getProduct();
        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        Cart cart = item.getCart();
        cart.recalculateTotals();
        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toDTO(updatedCart);
    }

    @Override
    @Transactional
    public void removeCartItem(UUID userId, UUID itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", itemId));

        Cart cart = item.getCart();
        cart.removeItem(item);
        cartItemRepository.delete(item);

        cart.recalculateTotals();
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(UUID userId) {
        Cart cart = cartRepository.findActiveCartByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", userId));

        cartItemRepository.deleteAllByCartId(cart.getId());
        cart.getItems().clear();
        cart.recalculateTotals();
        cartRepository.save(cart);
    }

    @Override
    public CartDTO getCartById(UUID cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", cartId));
        return cartMapper.toDTO(cart);
    }
}
