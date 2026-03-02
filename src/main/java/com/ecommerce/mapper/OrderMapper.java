package com.ecommerce.mapper;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.dto.OrderItemDTO;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Order entity and DTO conversion.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface OrderMapper {

    @Mapping(target = "id", expression = "java(order.getId().toString())")
    @Mapping(target = "orderNumber", source = "orderNumber")
    @Mapping(target = "userId", expression = "java(order.getUser() != null ? order.getUser().getId().toString() : null)")
    @Mapping(target = "userEmail", expression = "java(order.getUser() != null ? order.getUser().getEmail() : null)")
    @Mapping(target = "status", expression = "java(order.getStatus() != null ? order.getStatus().name() : null)")
    @Mapping(target = "subtotal", source = "subtotal")
    @Mapping(target = "taxAmount", source = "taxAmount")
    @Mapping(target = "shippingAmount", source = "shippingAmount")
    @Mapping(target = "discountAmount", source = "discountAmount")
    @Mapping(target = "totalAmount", source = "totalAmount")
    @Mapping(target = "paymentStatus", expression = "java(order.getPaymentStatus() != null ? order.getPaymentStatus().name() : null)")
    @Mapping(target = "paymentMethod", expression = "java(order.getPaymentMethod() != null ? order.getPaymentMethod().name() : null)")
    @Mapping(target = "shippingAddress", source = "shippingAddress")
    @Mapping(target = "billingAddress", source = "billingAddress")
    @Mapping(target = "items", expression = "java(mapOrderItems(order.getItems()))")
    @Mapping(target = "customerNote", source = "customerNote")
    @Mapping(target = "shippedAt", source = "shippedAt")
    @Mapping(target = "deliveredAt", source = "deliveredAt")
    @Mapping(target = "cancelledAt", source = "cancelledAt")
    @Mapping(target = "cancellationReason", source = "cancellationReason")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    OrderDTO toDTO(Order order);

    default List<OrderItemDTO> mapOrderItems(List<OrderItem> items) {
        if (items == null) {
            return null;
        }
        return items.stream().map(this::toOrderItemDTO).collect(Collectors.toList());
    }

    default OrderItemDTO toOrderItemDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId() != null ? item.getId().toString() : null);
        if (item.getProduct() != null) {
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setProductSku(item.getProduct().getSku());
        }
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTaxAmount(item.getTaxAmount());
        dto.setDiscountAmount(item.getDiscountAmount());
        dto.setTotalPrice(item.getTotalPrice());
        return dto;
    }
}
