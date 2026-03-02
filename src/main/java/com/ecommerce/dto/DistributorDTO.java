package com.ecommerce.dto;

import com.ecommerce.entity.DistributorStatus;
import com.ecommerce.entity.DistributorTier;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
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
 * DTO for Distributor data transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorDTO {

    private String id;

    private String userId;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private String distributorCode;

    private String companyName;

    private String businessLicenseNumber;

    private String taxId;

    private String businessAddress;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private BigDecimal commissionRate;

    private DistributorTier tier;

    private DistributorStatus status;

    private BigDecimal creditLimit;

    private BigDecimal currentBalance;

    private LocalDate contractStartDate;

    private LocalDate contractEndDate;

    private String territoryDescription;

    private List<String> territories;

    private Integer totalProducts;

    private Integer totalOrders;

    private BigDecimal totalRevenue;

    private BigDecimal totalCommissions;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
