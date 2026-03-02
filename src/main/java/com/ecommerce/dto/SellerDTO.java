package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Seller.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {

    private String id;

    private String userId;

    private String email;

    private String firstName;

    private String lastName;

    private String sellerCode;

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

    private java.math.BigDecimal commissionRate;

    private String tier;

    private String status;

    private java.math.BigDecimal totalRevenue;

    private java.math.BigDecimal currentBalance;

    private Integer totalProducts;

    private Integer totalOrders;

    private Double averageRating;

    private Integer totalReviews;

    private Boolean isVerified;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
