package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for DistributorProduct data transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorProductDTO {

    private String id;

    private String distributorId;

    private String distributorName;

    private String productId;

    private String productName;

    private String productSku;

    private java.math.BigDecimal distributorPrice;

    private java.math.BigDecimal minimumOrderQuantity;

    private java.math.BigDecimal maximumOrderQuantity;

    private Integer stockAllocation;

    private Integer reservedStock;

    private Boolean active;

    private java.time.LocalDateTime validFrom;

    private java.time.LocalDateTime validUntil;

    private String notes;

    private java.math.BigDecimal tier1Discount;

    private java.math.BigDecimal tier2Discount;

    private java.math.BigDecimal tier3Discount;

    private Integer tier1Threshold;

    private Integer tier2Threshold;

    private Integer tier3Threshold;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
