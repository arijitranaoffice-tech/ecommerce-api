package com.ecommerce.dto;

import com.ecommerce.entity.DistributorStatus;
import com.ecommerce.entity.DistributorTier;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Request DTO for creating a distributor.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDistributorRequest {

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

    @NotBlank(message = "Company name is required")
    private String companyName;

    private String businessLicenseNumber;

    private String taxId;

    private String businessAddress;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    @Builder.Default
    private BigDecimal commissionRate = BigDecimal.valueOf(5.0);

    @Builder.Default
    private DistributorTier tier = DistributorTier.BRONZE;

    private BigDecimal creditLimit;

    private LocalDate contractStartDate;

    private LocalDate contractEndDate;

    private String territoryDescription;

    private List<String> territories;
}
