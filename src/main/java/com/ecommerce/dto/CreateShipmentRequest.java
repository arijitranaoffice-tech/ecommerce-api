package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO for creating a shipment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShipmentRequest {

    @NotNull(message = "Order ID is required")
    private UUID orderId;

    @NotBlank(message = "Tracking number is required")
    private String trackingNumber;

    @NotBlank(message = "Carrier is required")
    private String carrier;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    private String shipmentNotes;
}
