package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for Seller Portal endpoints.
 */
@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SellerDTO>> registerSeller(@Valid @RequestBody CreateSellerRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Seller registered successfully", sellerService.createSeller(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<SellerDTO>> getSellerById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(sellerService.getSellerById(id)));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<SellerDTO>> getSellerByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(sellerService.getSellerByUserId(userId)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Page<SellerDTO>>> getAllSellers(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(sellerService.getAllSellers(pageable)));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Page<SellerDTO>>> searchSellers(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(sellerService.searchSellers(keyword, pageable)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<SellerDTO>> updateSeller(@PathVariable UUID id, @Valid @RequestBody UpdateSellerRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Seller updated", sellerService.updateSeller(id, request)));
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> approveSeller(@PathVariable UUID id) {
        sellerService.approveSeller(id);
        return ResponseEntity.ok(ApiResponse.success("Seller approved", null));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> rejectSeller(@PathVariable UUID id, @RequestParam String reason) {
        sellerService.rejectSeller(id, reason);
        return ResponseEntity.ok(ApiResponse.success("Seller rejected", null));
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> verifySeller(@PathVariable UUID id) {
        sellerService.verifySeller(id);
        return ResponseEntity.ok(ApiResponse.success("Seller verified", null));
    }

    @PostMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateSeller(@PathVariable UUID id) {
        sellerService.deactivateSeller(id);
        return ResponseEntity.ok(ApiResponse.success("Seller deactivated", null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSeller(@PathVariable UUID id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.ok(ApiResponse.success("Seller deleted", null));
    }

    @PostMapping("/{sellerId}/products")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<SellerProductDTO>> addProduct(@PathVariable UUID sellerId, @Valid @RequestBody CreateSellerProductRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Product added", sellerService.addProductToSeller(sellerId, request)));
    }

    @PutMapping("/products/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<SellerProductDTO>> updateProduct(@PathVariable UUID id, @Valid @RequestBody CreateSellerProductRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Product updated", sellerService.updateSellerProduct(id, request)));
    }

    @DeleteMapping("/{sellerId}/products/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<Void>> removeProduct(@PathVariable UUID sellerId, @PathVariable UUID productId) {
        sellerService.removeProductFromSeller(sellerId, productId);
        return ResponseEntity.ok(ApiResponse.success("Product removed", null));
    }

    @GetMapping("/{sellerId}/products")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<Page<SellerProductDTO>>> getSellerProducts(@PathVariable UUID sellerId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(sellerService.getSellerProducts(sellerId, pageable)));
    }

    @PostMapping("/products/{id}/approve")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<SellerProductDTO>> approveProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Product approved", sellerService.approveSellerProduct(id)));
    }

    @PostMapping("/products/{id}/reject")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<SellerProductDTO>> rejectProduct(@PathVariable UUID id, @RequestParam String reason) {
        return ResponseEntity.ok(ApiResponse.success("Product rejected", sellerService.rejectSellerProduct(id, reason)));
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<SellerOrderDTO>> getSellerOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(sellerService.getSellerOrderById(id)));
    }

    @GetMapping("/{sellerId}/orders")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<Page<SellerOrderDTO>>> getSellerOrders(@PathVariable UUID sellerId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(sellerService.getSellerOrders(sellerId, pageable)));
    }

    @GetMapping("/{sellerId}/dashboard")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<SellerDashboardDTO>> getSellerDashboard(@PathVariable UUID sellerId) {
        return ResponseEntity.ok(ApiResponse.success(sellerService.getSellerDashboard(sellerId)));
    }
}
