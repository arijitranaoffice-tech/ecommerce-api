package com.ecommerce.dto;

import com.ecommerce.entity.TransferStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for InventoryTransfer data transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryTransferDTO {

    private String id;

    private String distributorId;

    private String productId;

    private String productName;

    private String fromWarehouseId;

    private String fromWarehouseName;

    private String toWarehouseId;

    private String toWarehouseName;

    private Integer quantity;

    private TransferStatus status;

    private LocalDateTime transferDate;

    private LocalDateTime receivedDate;

    private String reason;

    private String notes;

    private String trackingNumber;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
