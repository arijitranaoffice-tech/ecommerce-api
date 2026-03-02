package com.ecommerce.service;

import com.ecommerce.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Seller operations.
 */
public interface SellerService {

    SellerDTO createSeller(CreateSellerRequest request);

    SellerDTO updateSeller(UUID sellerId, UpdateSellerRequest request);

    SellerDTO getSellerById(UUID sellerId);

    SellerDTO getSellerByUserId(UUID userId);

    Page<SellerDTO> getAllSellers(Pageable pageable);

    Page<SellerDTO> searchSellers(String keyword, Pageable pageable);

    void approveSeller(UUID sellerId);

    void rejectSeller(UUID sellerId, String reason);

    void verifySeller(UUID sellerId);

    void deactivateSeller(UUID sellerId);

    void deleteSeller(UUID sellerId);

    SellerProductDTO addProductToSeller(UUID sellerId, CreateSellerProductRequest request);

    SellerProductDTO updateSellerProduct(UUID sellerProductId, CreateSellerProductRequest request);

    void removeProductFromSeller(UUID sellerId, UUID productId);

    Page<SellerProductDTO> getSellerProducts(UUID sellerId, Pageable pageable);

    SellerProductDTO approveSellerProduct(UUID sellerProductId);

    SellerProductDTO rejectSellerProduct(UUID sellerProductId, String reason);

    SellerOrderDTO getSellerOrderById(UUID orderId);

    Page<SellerOrderDTO> getSellerOrders(UUID sellerId, Pageable pageable);

    SellerDashboardDTO getSellerDashboard(UUID sellerId);
}
