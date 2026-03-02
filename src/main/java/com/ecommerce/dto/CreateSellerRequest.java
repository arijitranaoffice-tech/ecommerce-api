package com.ecommerce.dto;

import com.ecommerce.entity.SellerTier;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Request DTO for creating a seller.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSellerRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String phone;

    @NotBlank(message = "Store name is required")
    private String storeName;

    private String storeDescription;

    private String businessLicenseNumber;

    private String taxId;

    private String businessAddress;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String logoUrl;

    private String bannerUrl;

    @Builder.Default
    private BigDecimal commissionRate = BigDecimal.valueOf(10.0);

    @Builder.Default
    private SellerTier tier = SellerTier.BASIC;

    private List<String> categories;

    private String returnPolicy;

    private String shippingPolicy;
}
