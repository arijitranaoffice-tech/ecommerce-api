package com.ecommerce.mapper;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import org.mapstruct.*;

/**
 * Mapper for Seller Portal entity and DTO conversion.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SellerPortalMapper {

    @Mapping(target = "id", expression = "java(seller.getId().toString())")
    @Mapping(target = "userId", expression = "java(seller.getUser() != null ? seller.getUser().getId().toString() : null)")
    @Mapping(target = "email", expression = "java(seller.getUser() != null ? seller.getUser().getEmail() : null)")
    @Mapping(target = "firstName", expression = "java(seller.getUser() != null ? seller.getUser().getFirstName() : null)")
    @Mapping(target = "lastName", expression = "java(seller.getUser() != null ? seller.getUser().getLastName() : null)")
    @Mapping(target = "tier", expression = "java(seller.getTier().name())")
    @Mapping(target = "status", expression = "java(seller.getStatus().name())")
    @Mapping(target = "createdAt", expression = "java(seller.getCreatedAt() != null ? seller.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(seller.getUpdatedAt() != null ? seller.getUpdatedAt().toString() : null)")
    SellerDTO toSellerDTO(Seller seller);

    @Mapping(target = "id", expression = "java(sp.getId().toString())")
    @Mapping(target = "sellerId", expression = "java(sp.getSeller() != null ? sp.getSeller().getId().toString() : null)")
    @Mapping(target = "sellerName", expression = "java(sp.getSeller() != null ? sp.getSeller().getStoreName() : null)")
    @Mapping(target = "productId", expression = "java(sp.getProduct() != null ? sp.getProduct().getId().toString() : null)")
    @Mapping(target = "productName", expression = "java(sp.getProduct() != null ? sp.getProduct().getName() : null)")
    @Mapping(target = "productSku", expression = "java(sp.getProduct() != null ? sp.getProduct().getSku() : null)")
    @Mapping(target = "createdAt", expression = "java(sp.getCreatedAt() != null ? sp.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(sp.getUpdatedAt() != null ? sp.getUpdatedAt().toString() : null)")
    SellerProductDTO toSellerProductDTO(SellerProduct sp);

    @Mapping(target = "id", expression = "java(so.getId().toString())")
    @Mapping(target = "sellerId", expression = "java(so.getSeller() != null ? so.getSeller().getId().toString() : null)")
    @Mapping(target = "sellerName", expression = "java(so.getSeller() != null ? so.getSeller().getStoreName() : null)")
    @Mapping(target = "orderId", expression = "java(so.getOrder() != null ? so.getOrder().getId().toString() : null)")
    @Mapping(target = "orderNumber", expression = "java(so.getOrder() != null ? so.getOrder().getOrderNumber() : null)")
    @Mapping(target = "status", expression = "java(so.getStatus().name())")
    @Mapping(target = "items", expression = "java(mapSellerOrderItems(so.getItems()))")
    @Mapping(target = "createdAt", expression = "java(so.getCreatedAt() != null ? so.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(so.getUpdatedAt() != null ? so.getUpdatedAt().toString() : null)")
    SellerOrderDTO toSellerOrderDTO(SellerOrder so);

    @Mapping(target = "id", expression = "java(item.getId().toString())")
    @Mapping(target = "productId", expression = "java(item.getProduct() != null ? item.getProduct().getId().toString() : null)")
    @Mapping(target = "productName", expression = "java(item.getProduct() != null ? item.getProduct().getName() : null)")
    SellerOrderItemDTO toSellerOrderItemDTO(SellerOrderItem item);

    @Mapping(target = "id", expression = "java(shipment.getId().toString())")
    @Mapping(target = "orderId", expression = "java(shipment.getOrder() != null ? shipment.getOrder().getId().toString() : null)")
    @Mapping(target = "orderNumber", expression = "java(shipment.getOrder() != null ? shipment.getOrder().getOrderNumber() : null)")
    @Mapping(target = "status", expression = "java(shipment.getStatus().name())")
    @Mapping(target = "pickedUpAt", expression = "java(shipment.getPickedUpAt() != null ? shipment.getPickedUpAt().toString() : null)")
    @Mapping(target = "inTransitAt", expression = "java(shipment.getInTransitAt() != null ? shipment.getInTransitAt().toString() : null)")
    @Mapping(target = "outForDeliveryAt", expression = "java(shipment.getOutForDeliveryAt() != null ? shipment.getOutForDeliveryAt().toString() : null)")
    @Mapping(target = "deliveredAt", expression = "java(shipment.getDeliveredAt() != null ? shipment.getDeliveredAt().toString() : null)")
    @Mapping(target = "exceptionAt", expression = "java(shipment.getExceptionAt() != null ? shipment.getExceptionAt().toString() : null)")
    @Mapping(target = "createdAt", expression = "java(shipment.getCreatedAt() != null ? shipment.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(shipment.getUpdatedAt() != null ? shipment.getUpdatedAt().toString() : null)")
    ShipmentDTO toShipmentDTO(Shipment shipment);

    @Mapping(target = "id", expression = "java(tx.getId().toString())")
    @Mapping(target = "orderId", expression = "java(tx.getOrder() != null ? tx.getOrder().getId().toString() : null)")
    @Mapping(target = "userId", expression = "java(tx.getUser() != null ? tx.getUser().getId().toString() : null)")
    @Mapping(target = "capturedAt", expression = "java(tx.getCapturedAt() != null ? tx.getCapturedAt().toString() : null)")
    @Mapping(target = "refundedAt", expression = "java(tx.getRefundedAt() != null ? tx.getRefundedAt().toString() : null)")
    @Mapping(target = "createdAt", expression = "java(tx.getCreatedAt() != null ? tx.getCreatedAt().toString() : null)")
    PayPalTransactionDTO toPayPalTransactionDTO(PayPalTransaction tx);

    @Mapping(target = "id", expression = "java(log.getId().toString())")
    @Mapping(target = "sentAt", expression = "java(log.getSentAt() != null ? log.getSentAt().toString() : null)")
    @Mapping(target = "createdAt", expression = "java(log.getCreatedAt() != null ? log.getCreatedAt().toString() : null)")
    EmailLogDTO toEmailLogDTO(EmailLog log);

    default java.util.List<SellerOrderItemDTO> mapSellerOrderItems(java.util.List<SellerOrderItem> items) {
        if (items == null) return new java.util.ArrayList<>();
        return items.stream().map(this::toSellerOrderItemDTO).collect(java.util.stream.Collectors.toList());
    }
}
