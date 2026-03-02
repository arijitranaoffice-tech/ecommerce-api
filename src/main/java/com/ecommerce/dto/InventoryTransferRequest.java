package com.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO for inventory transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryTransferRequest {

    @NotNull(message = "Distributor ID is required")
    private UUID distributorId;

    @NotNull(message = "Product ID is required")
    private UUID productId;

    private UUID fromWarehouseId;

    @NotNull(message = "To warehouse ID is required")
    private UUID toWarehouseId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    private String reason;

    private String notes;
}
