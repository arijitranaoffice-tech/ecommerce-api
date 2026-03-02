package com.ecommerce.dto;

import com.ecommerce.entity.PaymentMethodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating PaymentMethod.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentMethodRequest {

    @NotNull(message = "Payment method type is required")
    private PaymentMethodType type;

    @NotBlank(message = "Provider is required")
    private String provider;

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    private String expiryDate;

    private String cardholderName;

    private String billingAddress;

    @Builder.Default
    private Boolean isDefault = false;
}
