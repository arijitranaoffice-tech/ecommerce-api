package com.ecommerce.mapper;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.dto.CartItemDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Cart entity and DTO conversion.
 */
@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "id", expression = "java(cart.getId().toString())")
    @Mapping(target = "userId", expression = "java(cart.getUser() != null ? cart.getUser().getId().toString() : null)")
    @Mapping(target = "status", expression = "java(cart.getStatus() != null ? cart.getStatus().name() : null)")
    @Mapping(target = "items", expression = "java(mapCartItems(cart.getItems()))")
    @Mapping(target = "itemCount", expression = "java(cart.getItems().size())")
    @Mapping(target = "createdAt", expression = "java(cart.getCreatedAt() != null ? cart.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(cart.getUpdatedAt() != null ? cart.getUpdatedAt().toString() : null)")
    CartDTO toDTO(Cart cart);

    default List<CartItemDTO> mapCartItems(List<CartItem> items) {
        if (items == null) {
            return null;
        }
        return items.stream().map(this::toCartItemDTO).collect(Collectors.toList());
    }

    default CartItemDTO toCartItemDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId() != null ? item.getId().toString() : null);
        if (item.getProduct() != null) {
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setProductSku(item.getProduct().getSku());
            if (item.getProduct().getImages() != null && !item.getProduct().getImages().isEmpty()) {
                dto.setProductImage(item.getProduct().getImages().get(0));
            }
        }
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        dto.setCreatedAt(item.getCreatedAt() != null ? item.getCreatedAt().toString() : null);
        return dto;
    }
}
