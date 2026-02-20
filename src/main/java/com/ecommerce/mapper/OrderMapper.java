package com.ecommerce.mapper;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for Order entity and DTO conversion.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface OrderMapper {

    @Mapping(target = "id", expression = "java(order.getId().toString())")
    @Mapping(target = "userId", expression = "java(order.getUser().getId().toString())")
    @Mapping(target = "userEmail", expression = "java(order.getUser().getEmail())")
    @Mapping(target = "status", expression = "java(order.getStatus().name())")
    @Mapping(target = "paymentStatus", expression = "java(order.getPaymentStatus().name())")
    @Mapping(target = "paymentMethod", expression = "java(order.getPaymentMethod().name())")
    OrderDTO toDTO(Order order);
}
