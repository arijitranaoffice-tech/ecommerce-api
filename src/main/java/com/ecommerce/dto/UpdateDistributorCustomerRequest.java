package com.ecommerce.dto;

import com.ecommerce.entity.CustomerStatus;
import com.ecommerce.entity.CustomerTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request DTO for updating distributor customer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDistributorCustomerRequest {

    private String contactPerson;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private CustomerTier tier;

    private CustomerStatus status;

    private BigDecimal creditLimit;

    private String notes;
}
