package com.ecommerce.dto;

import com.ecommerce.entity.DistributorStatus;
import com.ecommerce.entity.DistributorTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Request DTO for updating a distributor.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDistributorRequest {

    private String companyName;

    private String businessLicenseNumber;

    private String taxId;

    private String businessAddress;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String phone;

    private BigDecimal commissionRate;

    private DistributorTier tier;

    private DistributorStatus status;

    private BigDecimal creditLimit;

    private LocalDate contractStartDate;

    private LocalDate contractEndDate;

    private String territoryDescription;

    private List<String> territories;
}
