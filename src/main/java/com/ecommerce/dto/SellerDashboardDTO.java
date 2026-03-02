package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Seller Dashboard.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDashboardDTO {

    private String sellerId;

    private String storeName;

    private SellerSummaryDTO summary;

    private SalesMetricsDTO salesMetrics;

    private OrderMetricsDTO orderMetrics;

    private ProductMetricsDTO productMetrics;
}
