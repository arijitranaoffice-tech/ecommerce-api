package com.ecommerce.dto;

import com.ecommerce.entity.PaymentMethodType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for PaymentMethod.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDTO {

    private String id;

    private String userId;

    private PaymentMethodType type;

    private String provider;

    private String accountNumber;

    private String expiryDate;

    private String cardholderName;

    private Boolean isDefault;

    private Boolean isActive;

    private String lastUsedAt;

    @JsonProperty("createdAt")
    private String createdAt;
}
