package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for PayPal Order Request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayPalOrderRequest {

    private String orderId;

    private String amount;

    private String currency;

    private String description;
}
