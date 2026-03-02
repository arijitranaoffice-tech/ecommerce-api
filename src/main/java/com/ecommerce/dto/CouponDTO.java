package com.ecommerce.dto;

import com.ecommerce.entity.DiscountType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for Coupon.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {

    private String id;

    private String code;

    private String description;

    private DiscountType discountType;

    private BigDecimal discountValue;

    private BigDecimal minPurchaseAmount;

    private BigDecimal maxDiscountAmount;

    private Integer usageLimit;

    private Integer usedCount;

    private Integer usageLimitPerUser;

    private Boolean isActive;

    private LocalDate validFrom;

    private LocalDate validUntil;

    private List<String> applicableCategories;

    private List<String> applicableProducts;

    private String termsAndConditions;

    @JsonProperty("createdAt")
    private String createdAt;
}
