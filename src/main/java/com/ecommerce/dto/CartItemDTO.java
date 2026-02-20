package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for CartItem data transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private String id;

    private UUID productId;

    private String productName;

    private String productSku;

    private String productImage;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    @JsonProperty("createdAt")
    private String createdAt;
}
