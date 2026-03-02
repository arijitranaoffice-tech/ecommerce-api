package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for commission metrics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommissionMetricsDTO {

    private BigDecimal pendingCommissions;

    private BigDecimal approvedCommissions;

    private BigDecimal paidCommissions;

    private BigDecimal totalCommissions;

    private Integer pendingCount;

    private Integer approvedCount;

    private Integer paidCount;

    private Integer totalCount;

    private BigDecimal averageCommissionRate;

    private LocalDate lastPayoutDate;

    private BigDecimal nextPayoutAmount;
}
