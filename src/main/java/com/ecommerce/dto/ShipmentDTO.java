package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Shipment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {

    private String id;

    private String orderId;

    private String orderNumber;

    private String trackingNumber;

    private String carrier;

    private String status;

    private String shippingAddress;

    private String pickedUpAt;

    private String inTransitAt;

    private String outForDeliveryAt;

    private String deliveredAt;

    private String exceptionAt;

    private String exceptionReason;

    private String shipmentNotes;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
