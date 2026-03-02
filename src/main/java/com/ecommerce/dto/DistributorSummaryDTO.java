package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for distributor summary.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorSummaryDTO {

    private String tier;

    private String status;

    private BigDecimal commissionRate;

    private BigDecimal creditLimit;

    private BigDecimal currentBalance;

    private BigDecimal availableCredit;

    private Integer totalProducts;

    private Integer totalCustomers;

    private Integer totalOrders;
}
