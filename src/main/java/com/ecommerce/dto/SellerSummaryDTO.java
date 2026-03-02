package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for Seller Summary.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerSummaryDTO {

    private String tier;

    private String status;

    private BigDecimal commissionRate;

    private BigDecimal totalRevenue;

    private BigDecimal currentBalance;

    private Integer totalProducts;

    private Integer totalOrders;

    private Double averageRating;

    private Boolean isVerified;
}
