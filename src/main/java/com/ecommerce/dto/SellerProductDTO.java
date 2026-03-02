package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for SellerProduct.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerProductDTO {

    private String id;

    private String sellerId;

    private String sellerName;

    private String productId;

    private String productName;

    private String productSku;

    private BigDecimal sellerPrice;

    private BigDecimal costPrice;

    private BigDecimal profitMargin;

    private Integer stockQuantity;

    private Integer reservedStock;

    private Boolean isActive;

    private Boolean isApproved;

    private String rejectionReason;

    private String sellerNotes;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
