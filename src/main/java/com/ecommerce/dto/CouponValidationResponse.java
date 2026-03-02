package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Response DTO for coupon validation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponValidationResponse {

    private Boolean valid;

    private String code;

    private String message;

    private BigDecimal discountAmount;

    private BigDecimal finalAmount;

    private String error;
}
