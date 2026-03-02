package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for top product in dashboard.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopProductDTO {

    private String id;

    private String name;

    private String sku;

    private Integer totalQuantitySold;

    private BigDecimal totalRevenue;
}
