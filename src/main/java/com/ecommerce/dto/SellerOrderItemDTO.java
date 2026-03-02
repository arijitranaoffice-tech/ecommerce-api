package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for SellerOrderItem.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerOrderItemDTO {

    private String id;

    private String productId;

    private String productName;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal commissionAmount;

    private BigDecimal sellerEarnings;

    private BigDecimal totalPrice;
}
