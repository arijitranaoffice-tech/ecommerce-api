package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating distributor inventory.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorInventoryUpdateRequest {

    private Integer quantityOnHand;

    private Integer quantityReserved;

    private Integer quantityInTransit;

    private Integer reorderPoint;

    private Integer reorderQuantity;

    private String location;

    private String bin;

    private String notes;
}
