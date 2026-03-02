package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for sales analytics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesAnalyticsDTO {

    private BigDecimal dailySales;

    private BigDecimal weeklySales;

    private BigDecimal monthlySales;

    private BigDecimal yearlySales;

    private BigDecimal averageOrderValue;

    private Integer growthPercentage;

    private List<ChartDataPointDTO> salesTrend;

    private List<ChartDataPointDTO> revenueByCategory;
}
