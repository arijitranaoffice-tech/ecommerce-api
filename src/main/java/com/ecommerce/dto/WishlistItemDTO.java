package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for WishlistItem.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemDTO {

    private String id;

    private String productId;

    private String productName;

    private String productImage;

    private java.math.BigDecimal price;

    private Boolean inStock;

    private String notes;

    private Boolean prioritized;

    @JsonProperty("createdAt")
    private String createdAt;
}
