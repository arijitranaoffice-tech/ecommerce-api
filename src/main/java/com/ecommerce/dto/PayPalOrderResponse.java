package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for PayPal Order Response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayPalOrderResponse {

    private String orderId;

    private String status;

    private BigDecimal amount;

    private String currency;

    private String approveUrl;

    private String createTime;
}
