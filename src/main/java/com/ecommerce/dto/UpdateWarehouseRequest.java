package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating a warehouse.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWarehouseRequest {

    private String address;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String phone;

    private String email;

    private String description;

    private Boolean isActive;
}
