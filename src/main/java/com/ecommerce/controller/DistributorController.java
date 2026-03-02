package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.service.DistributorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for distributor endpoints.
 */
@RestController
@RequestMapping("/distributors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DistributorController {

    private final DistributorService distributorService;

    /**
     * Create a new distributor.
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<DistributorDTO>> createDistributor(
            @Valid @RequestBody CreateDistributorRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Distributor created successfully", distributorService.createDistributor(request)));
    }

    /**
     * Update distributor information.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorDTO>> updateDistributor(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDistributorRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Distributor updated successfully", distributorService.updateDistributor(id, request)));
    }

    /**
     * Get distributor by ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorDTO>> getDistributorById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.getDistributorById(id)));
    }

    /**
     * Get distributor by user ID.
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorDTO>> getDistributorByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.getDistributorByUserId(userId)));
    }

    /**
     * Get all distributors.
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Page<DistributorDTO>>> getAllDistributors(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.getAllDistributors(pageable)));
    }

    /**
     * Search distributors.
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Page<DistributorDTO>>> searchDistributors(
            @RequestParam String keyword,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.searchDistributors(keyword, pageable)));
    }

    /**
     * Activate distributor.
     */
    @PostMapping("/{id}/activate")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> activateDistributor(@PathVariable UUID id) {
        distributorService.activateDistributor(id);
        return ResponseEntity.ok(ApiResponse.success("Distributor activated successfully", null));
    }

    /**
     * Deactivate distributor.
     */
    @PostMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateDistributor(@PathVariable UUID id) {
        distributorService.deactivateDistributor(id);
        return ResponseEntity.ok(ApiResponse.success("Distributor deactivated successfully", null));
    }

    /**
     * Delete distributor.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteDistributor(@PathVariable UUID id) {
        distributorService.deleteDistributor(id);
        return ResponseEntity.ok(ApiResponse.success("Distributor deleted successfully", null));
    }

    /**
     * Assign product to distributor.
     */
    @PostMapping("/{distributorId}/products/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<DistributorProductDTO>> assignProductToDistributor(
            @PathVariable UUID distributorId,
            @PathVariable UUID productId,
            @Valid @RequestBody DistributorProductRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Product assigned successfully",
                distributorService.assignProductToDistributor(distributorId, productId, request)));
    }

    /**
     * Update distributor product.
     */
    @PutMapping("/products/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorProductDTO>> updateDistributorProduct(
            @PathVariable UUID id,
            @Valid @RequestBody DistributorProductRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Distributor product updated successfully",
                distributorService.updateDistributorProduct(id, request)));
    }

    /**
     * Remove product from distributor.
     */
    @DeleteMapping("/{distributorId}/products/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> removeProductFromDistributor(
            @PathVariable UUID distributorId,
            @PathVariable UUID productId
    ) {
        distributorService.removeProductFromDistributor(distributorId, productId);
        return ResponseEntity.ok(ApiResponse.success("Product removed successfully", null));
    }

    /**
     * Get distributor products.
     */
    @GetMapping("/{distributorId}/products")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<Page<DistributorProductDTO>>> getDistributorProducts(
            @PathVariable UUID distributorId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.getDistributorProducts(distributorId, pageable)));
    }

    /**
     * Create distributor order.
     */
    @PostMapping("/{distributorId}/orders")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorOrderDTO>> createDistributorOrder(
            @PathVariable UUID distributorId,
            @Valid @RequestBody CreateDistributorOrderRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Distributor order created successfully",
                distributorService.createDistributorOrder(distributorId, request)));
    }

    /**
     * Get distributor order by ID.
     */
    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorOrderDTO>> getDistributorOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.getDistributorOrderById(id)));
    }

    /**
     * Get distributor orders.
     */
    @GetMapping("/{distributorId}/orders")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<Page<DistributorOrderDTO>>> getDistributorOrders(
            @PathVariable UUID distributorId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.getDistributorOrders(distributorId, pageable)));
    }

    /**
     * Approve distributor order.
     */
    @PostMapping("/orders/{id}/approve")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<DistributorOrderDTO>> approveDistributorOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Order approved successfully",
                distributorService.approveDistributorOrder(id)));
    }

    /**
     * Reject distributor order.
     */
    @PostMapping("/orders/{id}/reject")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<DistributorOrderDTO>> rejectDistributorOrder(
            @PathVariable UUID id,
            @RequestParam String reason
    ) {
        return ResponseEntity.ok(ApiResponse.success("Order rejected successfully",
                distributorService.rejectDistributorOrder(id, reason)));
    }

    /**
     * Ship distributor order.
     */
    @PostMapping("/orders/{id}/ship")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<DistributorOrderDTO>> shipDistributorOrder(
            @PathVariable UUID id,
            @RequestParam String trackingNumber,
            @RequestParam String carrier
    ) {
        return ResponseEntity.ok(ApiResponse.success("Order shipped successfully",
                distributorService.shipDistributorOrder(id, trackingNumber, carrier)));
    }

    /**
     * Deliver distributor order.
     */
    @PostMapping("/orders/{id}/deliver")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<DistributorOrderDTO>> deliverDistributorOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Order delivered successfully",
                distributorService.deliverDistributorOrder(id)));
    }

    /**
     * Cancel distributor order.
     */
    @PostMapping("/orders/{id}/cancel")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorOrderDTO>> cancelDistributorOrder(
            @PathVariable UUID id,
            @RequestParam String reason
    ) {
        return ResponseEntity.ok(ApiResponse.success("Order cancelled successfully",
                distributorService.cancelDistributorOrder(id, reason)));
    }

    /**
     * Calculate commission for order.
     */
    @PostMapping("/{distributorId}/commissions/calculate")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<DistributorCommissionDTO>> calculateCommission(
            @PathVariable UUID distributorId,
            @RequestParam UUID orderId
    ) {
        return ResponseEntity.ok(ApiResponse.success("Commission calculated successfully",
                distributorService.calculateCommission(distributorId, orderId)));
    }

    /**
     * Get distributor commissions.
     */
    @GetMapping("/{distributorId}/commissions")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<Page<DistributorCommissionDTO>>> getDistributorCommissions(
            @PathVariable UUID distributorId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.getDistributorCommissions(distributorId, pageable)));
    }

    /**
     * Pay commission.
     */
    @PostMapping("/commissions/{id}/pay")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<DistributorCommissionDTO>> payCommission(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Commission paid successfully",
                distributorService.payCommission(id)));
    }

    /**
     * Get distributor inventory for product.
     */
    @GetMapping("/{distributorId}/inventory/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorInventoryDTO>> getDistributorInventory(
            @PathVariable UUID distributorId,
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.getDistributorInventory(distributorId, productId)));
    }

    /**
     * Get distributor inventories.
     */
    @GetMapping("/{distributorId}/inventory")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<Page<DistributorInventoryDTO>>> getDistributorInventories(
            @PathVariable UUID distributorId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.getDistributorInventories(distributorId, pageable)));
    }

    /**
     * Update distributor inventory.
     */
    @PutMapping("/inventory/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorInventoryDTO>> updateDistributorInventory(
            @PathVariable UUID id,
            @Valid @RequestBody DistributorInventoryUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Inventory updated successfully",
                distributorService.updateDistributorInventory(id, request)));
    }

    /**
     * Add distributor customer.
     */
    @PostMapping("/{distributorId}/customers")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorCustomerDTO>> addDistributorCustomer(
            @PathVariable UUID distributorId,
            @Valid @RequestBody CreateDistributorCustomerRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Customer added successfully",
                distributorService.addDistributorCustomer(distributorId, request)));
    }

    /**
     * Update distributor customer.
     */
    @PutMapping("/customers/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<DistributorCustomerDTO>> updateDistributorCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDistributorCustomerRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Customer updated successfully",
                distributorService.updateDistributorCustomer(id, request)));
    }

    /**
     * Get distributor customers.
     */
    @GetMapping("/{distributorId}/customers")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<Page<DistributorCustomerDTO>>> getDistributorCustomers(
            @PathVariable UUID distributorId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.getDistributorCustomers(distributorId, pageable)));
    }

    /**
     * Remove distributor customer.
     */
    @DeleteMapping("/{distributorId}/customers/{customerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<Void>> removeDistributorCustomer(
            @PathVariable UUID distributorId,
            @PathVariable UUID customerId
    ) {
        distributorService.removeDistributorCustomer(distributorId, customerId);
        return ResponseEntity.ok(ApiResponse.success("Customer removed successfully", null));
    }

    /**
     * Create warehouse.
     */
    @PostMapping("/{distributorId}/warehouses")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<WarehouseDTO>> createWarehouse(
            @PathVariable UUID distributorId,
            @Valid @RequestBody CreateWarehouseRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Warehouse created successfully",
                distributorService.createWarehouse(distributorId, request)));
    }

    /**
     * Update warehouse.
     */
    @PutMapping("/warehouses/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<WarehouseDTO>> updateWarehouse(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateWarehouseRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Warehouse updated successfully",
                distributorService.updateWarehouse(id, request)));
    }

    /**
     * Get distributor warehouses.
     */
    @GetMapping("/{distributorId}/warehouses")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<Page<WarehouseDTO>>> getDistributorWarehouses(
            @PathVariable UUID distributorId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(distributorService.getDistributorWarehouses(distributorId, pageable)));
    }

    /**
     * Transfer inventory.
     */
    @PostMapping("/inventory/transfer")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DISTRIBUTOR')")
    public ResponseEntity<ApiResponse<InventoryTransferDTO>> transferInventory(
            @Valid @RequestBody InventoryTransferRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Inventory transfer initiated successfully",
                distributorService.transferInventory(request)));
    }
}
