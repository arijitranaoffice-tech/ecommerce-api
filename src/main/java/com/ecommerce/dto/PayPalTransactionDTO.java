package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for PayPal Transaction.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayPalTransactionDTO {

    private String id;

    private String orderId;

    private String userId;

    private String paypalOrderId;

    private String paypalPaymentId;

    private String status;

    private java.math.BigDecimal amount;

    private String currency;

    private String payerEmail;

    private String payerName;

    private String capturedAt;

    private String refundedAt;

    @JsonProperty("createdAt")
    private String createdAt;
}
