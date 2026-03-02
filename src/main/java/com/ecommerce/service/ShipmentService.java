package com.ecommerce.service;

import com.ecommerce.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Shipment operations.
 */
public interface ShipmentService {

    ShipmentDTO createShipment(CreateShipmentRequest request);

    ShipmentDTO updateShipment(UUID shipmentId, UpdateShipmentStatusRequest request);

    ShipmentDTO getShipmentById(UUID shipmentId);

    ShipmentDTO getShipmentByTrackingNumber(String trackingNumber);

    ShipmentDTO getShipmentByOrderId(UUID orderId);

    Page<ShipmentDTO> getAllShipments(Pageable pageable);

    Page<ShipmentDTO> getShipmentsByStatus(String status, Pageable pageable);

    List<ShipmentDTO> getInTransitShipments();

    List<ShipmentDTO> getOutForDeliveryShipments();

    void updateShipmentStatus(UUID shipmentId, String status);

    void markAsDelivered(UUID shipmentId);

    void markAsException(UUID shipmentId, String reason);

    void deleteShipment(UUID shipmentId);
}
