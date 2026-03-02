package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.service.UserPortalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Controller for User Portal endpoints.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserPortalController {

    private final UserPortalService userPortalService;

    // Wishlist endpoints
    @GetMapping("/wishlist")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<WishlistDTO>> getWishlist(@RequestParam UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(userPortalService.getWishlist(userId)));
    }

    @PostMapping("/wishlist/add")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<WishlistDTO>> addToWishlist(
            @RequestParam UUID userId,
            @RequestParam UUID productId
    ) {
        return ResponseEntity.ok(ApiResponse.success("Product added to wishlist",
                userPortalService.addToWishlist(userId, productId)));
    }

    @DeleteMapping("/wishlist/remove")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(
            @RequestParam UUID userId,
            @RequestParam UUID productId
    ) {
        userPortalService.removeFromWishlist(userId, productId);
        return ResponseEntity.ok(ApiResponse.success("Product removed from wishlist", null));
    }

    @DeleteMapping("/wishlist/clear")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> clearWishlist(@RequestParam UUID userId) {
        userPortalService.clearWishlist(userId);
        return ResponseEntity.ok(ApiResponse.success("Wishlist cleared", null));
    }

    // Notification endpoints
    @GetMapping("/notifications")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Page<NotificationDTO>>> getNotifications(
            @RequestParam UUID userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(userPortalService.getUserNotifications(userId, pageable)));
    }

    @GetMapping("/notifications/unread")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getUnreadNotifications(@RequestParam UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(userPortalService.getUnreadNotifications(userId)));
    }

    @GetMapping("/notifications/unread-count")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(@RequestParam UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(userPortalService.getUnreadCount(userId)));
    }

    @PostMapping("/notifications/{id}/read")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> markAsRead(@PathVariable UUID id) {
        userPortalService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as read", null));
    }

    @PostMapping("/notifications/read-all")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(@RequestParam UUID userId) {
        userPortalService.markAllAsRead(userId);
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read", null));
    }

    // Payment Method endpoints
    @PostMapping("/payment-methods")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PaymentMethodDTO>> addPaymentMethod(
            @RequestParam UUID userId,
            @Valid @RequestBody CreatePaymentMethodRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Payment method added",
                userPortalService.addPaymentMethod(userId, request)));
    }

    @PutMapping("/payment-methods/{id}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PaymentMethodDTO>> updatePaymentMethod(
            @RequestParam UUID userId,
            @PathVariable UUID id,
            @Valid @RequestBody CreatePaymentMethodRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Payment method updated",
                userPortalService.updatePaymentMethod(userId, id, request)));
    }

    @DeleteMapping("/payment-methods/{id}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> removePaymentMethod(
            @RequestParam UUID userId,
            @PathVariable UUID id
    ) {
        userPortalService.removePaymentMethod(userId, id);
        return ResponseEntity.ok(ApiResponse.success("Payment method removed", null));
    }

    @GetMapping("/payment-methods")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<PaymentMethodDTO>>> getPaymentMethods(@RequestParam UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(userPortalService.getUserPaymentMethods(userId)));
    }

    @GetMapping("/payment-methods/default")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PaymentMethodDTO>> getDefaultPaymentMethod(@RequestParam UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(userPortalService.getDefaultPaymentMethod(userId)));
    }

    @PostMapping("/payment-methods/{id}/set-default")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> setDefaultPaymentMethod(
            @RequestParam UUID userId,
            @PathVariable UUID id
    ) {
        userPortalService.setDefaultPaymentMethod(userId, id);
        return ResponseEntity.ok(ApiResponse.success("Default payment method set", null));
    }

    // Coupon endpoints
    @PostMapping("/coupons/validate")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<CouponValidationResponse>> validateCoupon(
            @RequestParam String code,
            @RequestParam BigDecimal orderAmount
    ) {
        return ResponseEntity.ok(ApiResponse.success(userPortalService.validateCoupon(code, orderAmount)));
    }

    @GetMapping("/coupons/available")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<List<CouponDTO>>> getAvailableCoupons() {
        return ResponseEntity.ok(ApiResponse.success(userPortalService.getAvailableCoupons()));
    }
}
