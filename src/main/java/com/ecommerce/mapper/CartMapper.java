package com.ecommerce.mapper;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for Cart entity and DTO conversion.
 */
@Mapper
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "id", expression = "java(cart.getId().toString())")
    @Mapping(target = "userId", expression = "java(cart.getUser().getId().toString())")
    @Mapping(target = "status", expression = "java(cart.getStatus().name())")
    @Mapping(target = "itemCount", expression = "java(cart.getItems().size())")
    @Mapping(target = "createdAt", expression = "java(cart.getCreatedAt() != null ? cart.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(cart.getUpdatedAt() != null ? cart.getUpdatedAt().toString() : null)")
    CartDTO toDTO(Cart cart);
}
