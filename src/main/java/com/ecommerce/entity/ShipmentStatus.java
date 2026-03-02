package com.ecommerce.entity;

/**
 * Shipment status.
 */
public enum ShipmentStatus {
    PENDING,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    EXCEPTION,
    RETURNED,
    LOST
}
