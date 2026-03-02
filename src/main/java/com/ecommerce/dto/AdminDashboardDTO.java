package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Admin Dashboard analytics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDTO {

    private OverviewStatsDTO overviewStats;

    private SalesAnalyticsDTO salesAnalytics;

    private ProductAnalyticsDTO productAnalytics;

    private CustomerAnalyticsDTO customerAnalytics;

    private OrderAnalyticsDTO orderAnalytics;

    private RecentActivitiesDTO recentActivities;
}
