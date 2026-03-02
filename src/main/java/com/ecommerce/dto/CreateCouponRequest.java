package com.ecommerce.dto;

import com.ecommerce.entity.DiscountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Request DTO for creating Coupon.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCouponRequest {

    @NotBlank(message = "Coupon code is required")
    private String code;

    private String description;

    @NotNull(message = "Discount type is required")
    private DiscountType discountType;

    @NotNull(message = "Discount value is required")
    private BigDecimal discountValue;

    private BigDecimal minPurchaseAmount;

    private BigDecimal maxDiscountAmount;

    @Builder.Default
    private Integer usageLimit = 0;

    @Builder.Default
    private Integer usageLimitPerUser = 1;

    private LocalDate validFrom;

    private LocalDate validUntil;

    private List<String> applicableCategories;

    private List<String> applicableProducts;

    private List<String> excludedProducts;

    private String termsAndConditions;

    @Builder.Default
    private Boolean isActive = true;
}
