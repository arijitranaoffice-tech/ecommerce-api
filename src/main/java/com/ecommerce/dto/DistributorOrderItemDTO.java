package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for DistributorOrderItem data transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorOrderItemDTO {

    private String id;

    private String productId;

    private String productName;

    private String productSku;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal discountPercent;

    private BigDecimal taxRate;

    private BigDecimal totalPrice;

    private Integer fulfilledQuantity;

    private Integer returnedQuantity;

    private String notes;
}
