package com.ecommerce.controller;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.dto.CartItemDTO;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for cart endpoints.
 */
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<CartDTO>> getCart(@RequestParam UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(cartService.getOrCreateCart(userId)));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartDTO>> addToCart(
            @RequestParam UUID userId,
            @Valid @RequestBody CartItemDTO itemDTO
    ) {
        return ResponseEntity.ok(ApiResponse.success("Item added to cart", cartService.addToCart(userId, itemDTO)));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartDTO>> updateCartItem(
            @RequestParam UUID userId,
            @PathVariable UUID itemId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(ApiResponse.success("Cart item updated", 
                cartService.updateCartItem(userId, itemId, quantity)));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<Void>> removeCartItem(
            @RequestParam UUID userId,
            @PathVariable UUID itemId
    ) {
        cartService.removeCartItem(userId, itemId);
        return ResponseEntity.ok(ApiResponse.success("Item removed from cart", null));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart(@RequestParam UUID userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.success("Cart cleared", null));
    }
}
