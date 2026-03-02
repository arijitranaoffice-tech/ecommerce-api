package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for Shipment endpoints.
 */
@RestController
@RequestMapping("/shipments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<ShipmentDTO>> createShipment(@Valid @RequestBody CreateShipmentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Shipment created", shipmentService.createShipment(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<ShipmentDTO>> updateShipment(@PathVariable UUID id, @Valid @RequestBody UpdateShipmentStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Shipment updated", shipmentService.updateShipment(id, request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<ShipmentDTO>> getShipmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.getShipmentById(id)));
    }

    @GetMapping("/track/{trackingNumber}")
    public ResponseEntity<ApiResponse<ShipmentDTO>> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.getShipmentByTrackingNumber(trackingNumber)));
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<ShipmentDTO>> getShipmentByOrderId(@PathVariable UUID orderId) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.getShipmentByOrderId(orderId)));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<Page<ShipmentDTO>>> getAllShipments(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.getAllShipments(pageable)));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<Page<ShipmentDTO>>> getShipmentsByStatus(@PathVariable String status, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.getShipmentsByStatus(status, pageable)));
    }

    @GetMapping("/in-transit")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<List<ShipmentDTO>>> getInTransitShipments() {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.getInTransitShipments()));
    }

    @GetMapping("/out-for-delivery")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<List<ShipmentDTO>>> getOutForDeliveryShipments() {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.getOutForDeliveryShipments()));
    }

    @PostMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<Void>> updateShipmentStatus(@PathVariable UUID id, @RequestParam String status) {
        shipmentService.updateShipmentStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Status updated", null));
    }

    @PostMapping("/{id}/deliver")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<Void>> markAsDelivered(@PathVariable UUID id) {
        shipmentService.markAsDelivered(id);
        return ResponseEntity.ok(ApiResponse.success("Marked as delivered", null));
    }

    @PostMapping("/{id}/exception")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<Void>> markAsException(@PathVariable UUID id, @RequestParam String reason) {
        shipmentService.markAsException(id, reason);
        return ResponseEntity.ok(ApiResponse.success("Marked as exception", null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteShipment(@PathVariable UUID id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.ok(ApiResponse.success("Shipment deleted", null));
    }
}
