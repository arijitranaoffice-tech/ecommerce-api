package com.ecommerce.mapper;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import org.mapstruct.*;

/**
 * Mapper for User Portal entity and DTO conversion.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserPortalMapper {

    @Mapping(target = "id", expression = "java(wishlist.getId().toString())")
    @Mapping(target = "userId", expression = "java(wishlist.getUser() != null ? wishlist.getUser().getId().toString() : null)")
    @Mapping(target = "items", expression = "java(mapWishlistItems(wishlist.getItems()))")
    WishlistDTO toWishlistDTO(Wishlist wishlist);

    @Mapping(target = "id", expression = "java(item.getId().toString())")
    @Mapping(target = "productId", expression = "java(item.getProduct() != null ? item.getProduct().getId().toString() : null)")
    @Mapping(target = "productName", expression = "java(item.getProduct() != null ? item.getProduct().getName() : null)")
    @Mapping(target = "productImage", expression = "java(item.getProduct() != null && !item.getProduct().getImages().isEmpty() ? item.getProduct().getImages().get(0) : null)")
    @Mapping(target = "price", expression = "java(item.getProduct() != null ? item.getProduct().getPrice() : null)")
    @Mapping(target = "inStock", expression = "java(item.getProduct() != null && item.getProduct().getStockQuantity() > 0)")
    WishlistItemDTO toWishlistItemDTO(WishlistItem item);

    @Mapping(target = "id", expression = "java(notification.getId().toString())")
    @Mapping(target = "userId", expression = "java(notification.getUser() != null ? notification.getUser().getId().toString() : null)")
    @Mapping(target = "readAt", expression = "java(notification.getReadAt() != null ? notification.getReadAt().toString() : null)")
    @Mapping(target = "createdAt", expression = "java(notification.getCreatedAt() != null ? notification.getCreatedAt().toString() : null)")
    NotificationDTO toNotificationDTO(Notification notification);

    @Mapping(target = "id", expression = "java(pm.getId().toString())")
    @Mapping(target = "userId", expression = "java(pm.getUser() != null ? pm.getUser().getId().toString() : null)")
    @Mapping(target = "lastUsedAt", expression = "java(pm.getLastUsedAt() != null ? pm.getLastUsedAt().toString() : null)")
    @Mapping(target = "createdAt", expression = "java(pm.getCreatedAt() != null ? pm.getCreatedAt().toString() : null)")
    PaymentMethodDTO toPaymentMethodDTO(UserPaymentMethod pm);

    @Mapping(target = "id", expression = "java(coupon.getId().toString())")
    @Mapping(target = "createdAt", expression = "java(coupon.getCreatedAt() != null ? coupon.getCreatedAt().toString() : null)")
    CouponDTO toCouponDTO(Coupon coupon);

    default java.util.List<WishlistItemDTO> mapWishlistItems(java.util.List<WishlistItem> items) {
        if (items == null) return new java.util.ArrayList<>();
        return items.stream().map(this::toWishlistItemDTO).collect(java.util.stream.Collectors.toList());
    }
}
