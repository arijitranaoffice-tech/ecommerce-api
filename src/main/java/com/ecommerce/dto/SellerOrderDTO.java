package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for SellerOrder.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerOrderDTO {

    private String id;

    private String sellerId;

    private String sellerName;

    private String sellerOrderNumber;

    private String orderId;

    private String orderNumber;

    private String status;

    private BigDecimal subtotal;

    private BigDecimal commissionAmount;

    private BigDecimal sellerEarnings;

    private BigDecimal totalAmount;

    private List<SellerOrderItemDTO> items;

    private LocalDateTime approvedAt;

    private LocalDateTime shippedAt;

    private LocalDateTime deliveredAt;

    private String trackingNumber;

    private String carrier;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
