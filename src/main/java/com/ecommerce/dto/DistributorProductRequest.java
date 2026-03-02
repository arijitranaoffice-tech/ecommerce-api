package com.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request DTO for distributor product operations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorProductRequest {

    @NotNull(message = "Distributor price is required")
    private BigDecimal distributorPrice;

    @Builder.Default
    private BigDecimal minimumOrderQuantity = BigDecimal.ONE;

    @Builder.Default
    private BigDecimal maximumOrderQuantity = BigDecimal.valueOf(1000);

    @Builder.Default
    private Integer stockAllocation = 0;

    @Builder.Default
    private Integer reservedStock = 0;

    @Builder.Default
    private Boolean active = true;

    private LocalDateTime validFrom;

    private LocalDateTime validUntil;

    private String notes;

    @Builder.Default
    private BigDecimal tier1Discount = BigDecimal.valueOf(5.0);

    @Builder.Default
    private BigDecimal tier2Discount = BigDecimal.valueOf(10.0);

    @Builder.Default
    private BigDecimal tier3Discount = BigDecimal.valueOf(15.0);

    @Builder.Default
    private Integer tier1Threshold = 10;

    @Builder.Default
    private Integer tier2Threshold = 50;

    @Builder.Default
    private Integer tier3Threshold = 100;
}
