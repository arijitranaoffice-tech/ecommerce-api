package com.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Request DTO for creating/updating seller product.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSellerProductRequest {

    @NotNull(message = "Product ID is required")
    private UUID productId;

    @NotNull(message = "Seller price is required")
    private BigDecimal sellerPrice;

    @NotNull(message = "Cost price is required")
    private BigDecimal costPrice;

    @Builder.Default
    private Integer stockQuantity = 0;

    private String sellerNotes;
}
