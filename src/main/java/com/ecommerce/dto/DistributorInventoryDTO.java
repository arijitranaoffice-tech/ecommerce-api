package com.ecommerce.dto;

import com.ecommerce.entity.InventoryStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for DistributorInventory data transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorInventoryDTO {

    private String id;

    private String distributorId;

    private String distributorName;

    private String productId;

    private String productName;

    private String productSku;

    private String warehouseId;

    private String warehouseName;

    private Integer quantityOnHand;

    private Integer quantityAvailable;

    private Integer quantityReserved;

    private Integer quantityInTransit;

    private Integer reorderPoint;

    private Integer reorderQuantity;

    private LocalDateTime lastRestockedAt;

    private String location;

    private String bin;

    private InventoryStatus status;

    private String notes;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
