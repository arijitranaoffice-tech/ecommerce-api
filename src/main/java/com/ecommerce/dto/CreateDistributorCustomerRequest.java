package com.ecommerce.dto;

import com.ecommerce.entity.CustomerTier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Request DTO for creating distributor customer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDistributorCustomerRequest {

    private UUID customerUserId;

    @NotBlank(message = "Company name is required")
    private String companyName;

    private String contactPerson;

    @NotBlank(message = "Email is required")
    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    @Builder.Default
    private CustomerTier tier = CustomerTier.STANDARD;

    @Builder.Default
    private BigDecimal creditLimit = BigDecimal.ZERO;
}
