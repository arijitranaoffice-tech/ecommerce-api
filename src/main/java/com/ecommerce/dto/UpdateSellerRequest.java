package com.ecommerce.dto;

import com.ecommerce.entity.SellerStatus;
import com.ecommerce.entity.SellerTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Request DTO for updating a seller.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSellerRequest {

    private String storeName;

    private String storeDescription;

    private String businessLicenseNumber;

    private String taxId;

    private String businessAddress;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String phone;

    private String logoUrl;

    private String bannerUrl;

    private BigDecimal commissionRate;

    private SellerTier tier;

    private SellerStatus status;

    private List<String> categories;

    private String returnPolicy;

    private String shippingPolicy;

    private Boolean isVerified;
}
