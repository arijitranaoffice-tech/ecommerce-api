package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Customer Report.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReportDTO {

    private Integer totalCustomers;

    private Integer newCustomers;

    private Integer activeCustomers;

    private Double retentionRate;

    private Double averageOrderValue;

    private Double customerLifetimeValue;
}
