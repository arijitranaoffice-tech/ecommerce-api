package com.ecommerce.service.impl;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.UserPortalMapper;
import com.ecommerce.repository.*;
import com.ecommerce.service.UserPortalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of UserPortalService.
 */
@Service
@RequiredArgsConstructor
public class UserPortalServiceImpl implements UserPortalService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final NotificationRepository notificationRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final CouponRepository couponRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserPortalMapper userPortalMapper;

    @Override
    @Transactional
    public WishlistDTO getOrCreateWishlist(UUID userId) {
        return wishlistRepository.findByUserId(userId)
                .map(userPortalMapper::toWishlistDTO)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User", userId));
                    Wishlist wishlist = Wishlist.builder()
                            .user(user)
                            .itemCount(0)
                            .build();
                    wishlist = wishlistRepository.save(wishlist);
                    return userPortalMapper.toWishlistDTO(wishlist);
                });
    }

    @Override
    @Transactional
    public WishlistDTO addToWishlist(UUID userId, UUID productId) {
        Wishlist wishlist = getOrCreateWishlistEntity(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        if (!wishlistItemRepository.existsByWishlistIdAndProductId(wishlist.getId(), productId)) {
            WishlistItem item = WishlistItem.builder()
                    .wishlist(wishlist)
                    .product(product)
                    .build();
            wishlistItemRepository.save(item);
            wishlist.setItemCount(wishlist.getItems().size() + 1);
            wishlistRepository.save(wishlist);
        }
        return userPortalMapper.toWishlistDTO(wishlist);
    }

    @Override
    @Transactional
    public void removeFromWishlist(UUID userId, UUID productId) {
        Wishlist wishlist = getOrCreateWishlistEntity(userId);
        WishlistItem item = wishlistItemRepository.findByWishlistIdAndProductId(wishlist.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("WishlistItem", productId));
        wishlist.removeItem(item);
        wishlistRepository.save(wishlist);
        wishlistItemRepository.delete(item);
    }

    @Override
    @Transactional
    public void clearWishlist(UUID userId) {
        Wishlist wishlist = getOrCreateWishlistEntity(userId);
        wishlist.getItems().clear();
        wishlist.setItemCount(0);
        wishlistRepository.save(wishlist);
    }

    @Override
    public WishlistDTO getWishlist(UUID userId) {
        Wishlist wishlist = getOrCreateWishlistEntity(userId);
        return userPortalMapper.toWishlistDTO(wishlist);
    }

    @Override
    public Page<NotificationDTO> getUserNotifications(UUID userId, Pageable pageable) {
        return notificationRepository.findByUserId(userId, pageable)
                .map(userPortalMapper::toNotificationDTO);
    }

    @Override
    public List<NotificationDTO> getUnreadNotifications(UUID userId) {
        return notificationRepository.findByUserIdAndRead(userId, false)
                .stream()
                .map(userPortalMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long getUnreadCount(UUID userId) {
        return notificationRepository.countUnreadByUserId(userId);
    }

    @Override
    @Transactional
    public void markAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", notificationId));
        notification.setRead(true);
        notification.setReadAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void markAllAsRead(UUID userId) {
        notificationRepository.markAllAsRead(userId);
    }

    @Override
    @Transactional
    public NotificationDTO createNotification(NotificationCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.getUserId()));
        Notification notification = Notification.builder()
                .user(user)
                .title(request.getTitle())
                .message(request.getMessage())
                .type(request.getType())
                .priority(request.getPriority())
                .actionUrl(request.getActionUrl())
                .relatedEntityType(request.getRelatedEntityType())
                .relatedEntityId(request.getRelatedEntityId())
                .build();
        notification = notificationRepository.save(notification);
        return userPortalMapper.toNotificationDTO(notification);
    }

    @Override
    @Transactional
    public PaymentMethodDTO addPaymentMethod(UUID userId, CreatePaymentMethodRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        if (request.getIsDefault()) {
            paymentMethodRepository.findByUserId(userId)
                    .forEach(pm -> {
                        pm.setIsDefault(false);
                        paymentMethodRepository.save(pm);
                    });
        }

        UserPaymentMethod paymentMethod = UserPaymentMethod.builder()
                .user(user)
                .type(request.getType())
                .provider(request.getProvider())
                .accountNumber(request.getAccountNumber())
                .expiryDate(request.getExpiryDate())
                .cardholderName(request.getCardholderName())
                .billingAddress(request.getBillingAddress())
                .isDefault(request.getIsDefault())
                .isActive(true)
                .build();
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return userPortalMapper.toPaymentMethodDTO(paymentMethod);
    }

    @Override
    @Transactional
    public PaymentMethodDTO updatePaymentMethod(UUID userId, UUID paymentMethodId, CreatePaymentMethodRequest request) {
        UserPaymentMethod paymentMethod = paymentMethodRepository.findByUserIdAndId(userId, paymentMethodId)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentMethod", paymentMethodId));

        if (request.getIsDefault()) {
            paymentMethodRepository.findByUserId(userId)
                    .forEach(pm -> {
                        if (!pm.getId().equals(paymentMethodId)) {
                            pm.setIsDefault(false);
                            paymentMethodRepository.save(pm);
                        }
                    });
        }

        paymentMethod.setType(request.getType());
        paymentMethod.setProvider(request.getProvider());
        paymentMethod.setAccountNumber(request.getAccountNumber());
        paymentMethod.setExpiryDate(request.getExpiryDate());
        paymentMethod.setCardholderName(request.getCardholderName());
        paymentMethod.setBillingAddress(request.getBillingAddress());
        paymentMethod.setIsDefault(request.getIsDefault());
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return userPortalMapper.toPaymentMethodDTO(paymentMethod);
    }

    @Override
    @Transactional
    public void removePaymentMethod(UUID userId, UUID paymentMethodId) {
        UserPaymentMethod paymentMethod = paymentMethodRepository.findByUserIdAndId(userId, paymentMethodId)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentMethod", paymentMethodId));
        paymentMethodRepository.delete(paymentMethod);
    }

    @Override
    public List<PaymentMethodDTO> getUserPaymentMethods(UUID userId) {
        return paymentMethodRepository.findByUserIdAndIsActive(userId, true)
                .stream()
                .map(userPortalMapper::toPaymentMethodDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentMethodDTO getDefaultPaymentMethod(UUID userId) {
        return paymentMethodRepository.findDefaultByUserId(userId)
                .map(userPortalMapper::toPaymentMethodDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public void setDefaultPaymentMethod(UUID userId, UUID paymentMethodId) {
        paymentMethodRepository.findByUserId(userId)
                .forEach(pm -> {
                    pm.setIsDefault(pm.getId().equals(paymentMethodId));
                    paymentMethodRepository.save(pm);
                });
    }

    @Override
    public CouponValidationResponse validateCoupon(String code, BigDecimal orderAmount) {
        Coupon coupon = couponRepository.findValidCouponByCode(code, LocalDate.now())
                .orElse(null);

        if (coupon == null) {
            return CouponValidationResponse.builder()
                    .valid(false)
                    .code(code)
                    .error("Invalid or expired coupon")
                    .build();
        }

        if (coupon.getMinPurchaseAmount() != null && orderAmount.compareTo(coupon.getMinPurchaseAmount()) < 0) {
            return CouponValidationResponse.builder()
                    .valid(false)
                    .code(code)
                    .error("Minimum purchase amount not met")
                    .build();
        }

        BigDecimal discountAmount = calculateDiscount(coupon, orderAmount);
        if (coupon.getMaxDiscountAmount() != null) {
            discountAmount = discountAmount.min(coupon.getMaxDiscountAmount());
        }

        return CouponValidationResponse.builder()
                .valid(true)
                .code(code)
                .discountAmount(discountAmount)
                .finalAmount(orderAmount.subtract(discountAmount))
                .build();
    }

    @Override
    public CouponDTO getCouponByCode(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", code));
        return userPortalMapper.toCouponDTO(coupon);
    }

    @Override
    public List<CouponDTO> getAvailableCoupons() {
        return couponRepository.findByIsActiveAndValidFromLessThanEqualAndValidUntilGreaterThanEqual(
                        true, LocalDate.now(), LocalDate.now())
                .stream()
                .map(userPortalMapper::toCouponDTO)
                .collect(Collectors.toList());
    }

    private Wishlist getOrCreateWishlistEntity(UUID userId) {
        return wishlistRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User", userId));
                    Wishlist wishlist = Wishlist.builder()
                            .user(user)
                            .itemCount(0)
                            .build();
                    return wishlistRepository.save(wishlist);
                });
    }

    private BigDecimal calculateDiscount(Coupon coupon, BigDecimal orderAmount) {
        if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
            return orderAmount.multiply(coupon.getDiscountValue())
                    .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        } else if (coupon.getDiscountType() == DiscountType.FIXED_AMOUNT) {
            return coupon.getDiscountValue().min(orderAmount);
        }
        return BigDecimal.ZERO;
    }
}
