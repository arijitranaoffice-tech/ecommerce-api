package com.ecommerce.dto;

import com.ecommerce.entity.DistributorOrderStatus;
import com.ecommerce.entity.PaymentMethod;
import com.ecommerce.entity.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for DistributorOrder data transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorOrderDTO {

    private String id;

    private String distributorId;

    private String distributorName;

    private String distributorOrderNumber;

    private String orderId;

    private String orderNumber;

    private DistributorOrderStatus status;

    private BigDecimal subtotal;

    private BigDecimal taxAmount;

    private BigDecimal shippingAmount;

    private BigDecimal discountAmount;

    private BigDecimal totalAmount;

    private BigDecimal commissionAmount;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;

    private AddressDTO shippingAddress;

    private AddressDTO billingAddress;

    private String distributorNote;

    private String internalNote;

    private LocalDateTime approvedAt;

    private LocalDateTime shippedAt;

    private LocalDateTime deliveredAt;

    private LocalDateTime cancelledAt;

    private String cancellationReason;

    private String rejectionReason;

    private List<DistributorOrderItemDTO> items;

    private boolean isFulfillmentOrder;

    private String trackingNumber;

    private String carrier;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
