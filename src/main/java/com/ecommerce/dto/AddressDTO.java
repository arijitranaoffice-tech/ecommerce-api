package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Address data transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String id;

    private String street;

    private String street2;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String type;

    private boolean isDefault;

    private String phone;

    @JsonProperty("createdAt")
    private String createdAt;
}
