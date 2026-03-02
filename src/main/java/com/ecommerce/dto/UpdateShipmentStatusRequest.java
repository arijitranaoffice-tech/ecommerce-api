package com.ecommerce.dto;

import com.ecommerce.entity.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating shipment status.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShipmentStatusRequest {

    private ShipmentStatus status;

    private String trackingNumber;

    private String carrier;

    private String exceptionReason;

    private String shipmentNotes;
}
