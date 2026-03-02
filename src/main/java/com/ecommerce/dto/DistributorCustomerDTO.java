package com.ecommerce.dto;

import com.ecommerce.entity.CustomerStatus;
import com.ecommerce.entity.CustomerTier;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for DistributorCustomer data transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorCustomerDTO {

    private String id;

    private String distributorId;

    private String customerId;

    private String companyName;

    private String contactPerson;

    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private CustomerTier tier;

    private BigDecimal creditLimit;

    private BigDecimal currentBalance;

    private CustomerStatus status;

    private LocalDate lastOrderDate;

    private Integer totalOrders;

    private BigDecimal totalRevenue;

    private List<String> tags;

    private String notes;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
