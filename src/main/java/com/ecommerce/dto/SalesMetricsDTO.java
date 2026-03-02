package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for Sales Metrics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesMetricsDTO {

    private BigDecimal todaySales;

    private BigDecimal weekSales;

    private BigDecimal monthSales;

    private BigDecimal quarterSales;

    private BigDecimal yearlySales;

    private BigDecimal totalSales;

    private Integer todayOrders;

    private Integer weekOrders;

    private Integer monthOrders;

    private Integer quarterOrders;

    private Integer yearOrders;

    private Integer totalOrders;

    private BigDecimal averageOrderValue;

    private String lastOrderDate;
}
