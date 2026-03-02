package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for recent order in dashboard.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentOrderDTO {

    private String id;

    private String orderNumber;

    private BigDecimal totalAmount;

    private String status;

    private String createdAt;
}
