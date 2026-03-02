package com.ecommerce.service;

import com.ecommerce.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for User Portal operations.
 */
public interface UserPortalService {

    // Wishlist operations
    WishlistDTO getOrCreateWishlist(UUID userId);

    WishlistDTO addToWishlist(UUID userId, UUID productId);

    void removeFromWishlist(UUID userId, UUID productId);

    void clearWishlist(UUID userId);

    WishlistDTO getWishlist(UUID userId);

    // Notification operations
    Page<NotificationDTO> getUserNotifications(UUID userId, Pageable pageable);

    List<NotificationDTO> getUnreadNotifications(UUID userId);

    long getUnreadCount(UUID userId);

    void markAsRead(UUID notificationId);

    void markAllAsRead(UUID userId);

    NotificationDTO createNotification(NotificationCreateRequest request);

    // Payment Method operations
    PaymentMethodDTO addPaymentMethod(UUID userId, CreatePaymentMethodRequest request);

    PaymentMethodDTO updatePaymentMethod(UUID userId, UUID paymentMethodId, CreatePaymentMethodRequest request);

    void removePaymentMethod(UUID userId, UUID paymentMethodId);

    List<PaymentMethodDTO> getUserPaymentMethods(UUID userId);

    PaymentMethodDTO getDefaultPaymentMethod(UUID userId);

    void setDefaultPaymentMethod(UUID userId, UUID paymentMethodId);

    // Coupon operations
    CouponValidationResponse validateCoupon(String code, BigDecimal orderAmount);

    CouponDTO getCouponByCode(String code);

    List<CouponDTO> getAvailableCoupons();
}
