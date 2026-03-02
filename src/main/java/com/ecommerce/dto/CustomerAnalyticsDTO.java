package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for customer analytics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAnalyticsDTO {

    private Integer totalCustomers;

    private Integer activeCustomers;

    private Integer newCustomersThisMonth;

    private Integer newCustomersThisWeek;

    private Double customerRetentionRate;

    private Double averageCustomerLifetimeValue;
}
