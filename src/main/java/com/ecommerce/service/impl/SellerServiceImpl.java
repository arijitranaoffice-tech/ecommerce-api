package com.ecommerce.service.impl;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.SellerPortalMapper;
import com.ecommerce.repository.*;
import com.ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Implementation of SellerService.
 */
@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final SellerProductRepository sellerProductRepository;
    private final SellerOrderRepository sellerOrderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SellerPortalMapper sellerPortalMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SellerDTO createSeller(CreateSellerRequest request) {
        if (sellerRepository.existsByUserEmail(request.getEmail())) {
            throw new RuntimeException("User with this email is already a seller");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .role(UserRole.SELLER)
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(user);

        String sellerCode = "SELLER-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Seller seller = Seller.builder()
                .user(user)
                .sellerCode(sellerCode)
                .storeName(request.getStoreName())
                .storeDescription(request.getStoreDescription())
                .businessLicenseNumber(request.getBusinessLicenseNumber())
                .taxId(request.getTaxId())
                .businessAddress(request.getBusinessAddress())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .phone(request.getPhone())
                .email(request.getEmail())
                .logoUrl(request.getLogoUrl())
                .bannerUrl(request.getBannerUrl())
                .commissionRate(request.getCommissionRate())
                .tier(request.getTier())
                .status(SellerStatus.PENDING)
                .totalRevenue(BigDecimal.ZERO)
                .currentBalance(BigDecimal.ZERO)
                .totalProducts(0)
                .totalOrders(0)
                .averageRating(0.0)
                .totalReviews(0)
                .isVerified(false)
                .categories(request.getCategories() != null ? request.getCategories() : new java.util.ArrayList<>())
                .returnPolicy(request.getReturnPolicy())
                .shippingPolicy(request.getShippingPolicy())
                .build();

        seller = sellerRepository.save(seller);
        return sellerPortalMapper.toSellerDTO(seller);
    }

    @Override
    @Transactional
    public SellerDTO updateSeller(UUID sellerId, UpdateSellerRequest request) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", sellerId));

        if (request.getStoreName() != null) seller.setStoreName(request.getStoreName());
        if (request.getStoreDescription() != null) seller.setStoreDescription(request.getStoreDescription());
        if (request.getBusinessLicenseNumber() != null) seller.setBusinessLicenseNumber(request.getBusinessLicenseNumber());
        if (request.getTaxId() != null) seller.setTaxId(request.getTaxId());
        if (request.getBusinessAddress() != null) seller.setBusinessAddress(request.getBusinessAddress());
        if (request.getCity() != null) seller.setCity(request.getCity());
        if (request.getState() != null) seller.setState(request.getState());
        if (request.getPostalCode() != null) seller.setPostalCode(request.getPostalCode());
        if (request.getCountry() != null) seller.setCountry(request.getCountry());
        if (request.getPhone() != null) seller.setPhone(request.getPhone());
        if (request.getLogoUrl() != null) seller.setLogoUrl(request.getLogoUrl());
        if (request.getBannerUrl() != null) seller.setBannerUrl(request.getBannerUrl());
        if (request.getCommissionRate() != null) seller.setCommissionRate(request.getCommissionRate());
        if (request.getTier() != null) seller.setTier(request.getTier());
        if (request.getStatus() != null) seller.setStatus(request.getStatus());
        if (request.getCategories() != null) seller.setCategories(request.getCategories());
        if (request.getReturnPolicy() != null) seller.setReturnPolicy(request.getReturnPolicy());
        if (request.getShippingPolicy() != null) seller.setShippingPolicy(request.getShippingPolicy());
        if (request.getIsVerified() != null) seller.setIsVerified(request.getIsVerified());

        seller = sellerRepository.save(seller);
        return sellerPortalMapper.toSellerDTO(seller);
    }

    @Override
    public SellerDTO getSellerById(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", sellerId));
        return sellerPortalMapper.toSellerDTO(seller);
    }

    @Override
    public SellerDTO getSellerByUserId(UUID userId) {
        Seller seller = sellerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller for user", userId));
        return sellerPortalMapper.toSellerDTO(seller);
    }

    @Override
    public Page<SellerDTO> getAllSellers(Pageable pageable) {
        return sellerRepository.findAll(pageable).map(sellerPortalMapper::toSellerDTO);
    }

    @Override
    public Page<SellerDTO> searchSellers(String keyword, Pageable pageable) {
        return sellerRepository.searchSellers(keyword, pageable).map(sellerPortalMapper::toSellerDTO);
    }

    @Override
    @Transactional
    public void approveSeller(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", sellerId));
        seller.setStatus(SellerStatus.ACTIVE);
        sellerRepository.save(seller);
    }

    @Override
    @Transactional
    public void rejectSeller(UUID sellerId, String reason) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", sellerId));
        seller.setStatus(SellerStatus.REJECTED);
        sellerRepository.save(seller);
    }

    @Override
    @Transactional
    public void verifySeller(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", sellerId));
        seller.setIsVerified(true);
        sellerRepository.save(seller);
    }

    @Override
    @Transactional
    public void deactivateSeller(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", sellerId));
        seller.setStatus(SellerStatus.INACTIVE);
        sellerRepository.save(seller);
    }

    @Override
    @Transactional
    public void deleteSeller(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", sellerId));
        sellerRepository.delete(seller);
    }

    @Override
    @Transactional
    public SellerProductDTO addProductToSeller(UUID sellerId, CreateSellerProductRequest request) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", sellerId));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", request.getProductId()));

        if (sellerProductRepository.existsBySellerIdAndProductId(sellerId, request.getProductId())) {
            throw new RuntimeException("Product is already added by this seller");
        }

        BigDecimal profitMargin = request.getSellerPrice().subtract(request.getCostPrice());

        SellerProduct sellerProduct = SellerProduct.builder()
                .seller(seller)
                .product(product)
                .sellerPrice(request.getSellerPrice())
                .costPrice(request.getCostPrice())
                .profitMargin(profitMargin)
                .stockQuantity(request.getStockQuantity())
                .reservedStock(0)
                .isActive(true)
                .isApproved(false)
                .sellerNotes(request.getSellerNotes())
                .build();

        sellerProduct = sellerProductRepository.save(sellerProduct);
        seller.addSellerProduct(sellerProduct);
        sellerRepository.save(seller);

        return sellerPortalMapper.toSellerProductDTO(sellerProduct);
    }

    @Override
    @Transactional
    public SellerProductDTO updateSellerProduct(UUID sellerProductId, CreateSellerProductRequest request) {
        SellerProduct sellerProduct = sellerProductRepository.findById(sellerProductId)
                .orElseThrow(() -> new ResourceNotFoundException("SellerProduct", sellerProductId));

        if (request.getSellerPrice() != null) sellerProduct.setSellerPrice(request.getSellerPrice());
        if (request.getCostPrice() != null) sellerProduct.setCostPrice(request.getCostPrice());
        if (request.getStockQuantity() != null) sellerProduct.setStockQuantity(request.getStockQuantity());
        if (request.getSellerNotes() != null) sellerProduct.setSellerNotes(request.getSellerNotes());

        BigDecimal profitMargin = sellerProduct.getSellerPrice().subtract(sellerProduct.getCostPrice());
        sellerProduct.setProfitMargin(profitMargin);

        sellerProduct = sellerProductRepository.save(sellerProduct);
        return sellerPortalMapper.toSellerProductDTO(sellerProduct);
    }

    @Override
    @Transactional
    public void removeProductFromSeller(UUID sellerId, UUID productId) {
        SellerProduct sellerProduct = sellerProductRepository.findBySellerIdAndProductId(sellerId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("SellerProduct", productId));
        sellerProductRepository.delete(sellerProduct);
    }

    @Override
    public Page<SellerProductDTO> getSellerProducts(UUID sellerId, Pageable pageable) {
        return sellerProductRepository.findBySellerId(sellerId, pageable)
                .map(sellerPortalMapper::toSellerProductDTO);
    }

    @Override
    @Transactional
    public SellerProductDTO approveSellerProduct(UUID sellerProductId) {
        SellerProduct sellerProduct = sellerProductRepository.findById(sellerProductId)
                .orElseThrow(() -> new ResourceNotFoundException("SellerProduct", sellerProductId));
        sellerProduct.setIsApproved(true);
        sellerProduct = sellerProductRepository.save(sellerProduct);
        return sellerPortalMapper.toSellerProductDTO(sellerProduct);
    }

    @Override
    @Transactional
    public SellerProductDTO rejectSellerProduct(UUID sellerProductId, String reason) {
        SellerProduct sellerProduct = sellerProductRepository.findById(sellerProductId)
                .orElseThrow(() -> new ResourceNotFoundException("SellerProduct", sellerProductId));
        sellerProduct.setIsApproved(false);
        sellerProduct.setRejectionReason(reason);
        sellerProduct = sellerProductRepository.save(sellerProduct);
        return sellerPortalMapper.toSellerProductDTO(sellerProduct);
    }

    @Override
    public SellerOrderDTO getSellerOrderById(UUID orderId) {
        SellerOrder sellerOrder = sellerOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("SellerOrder", orderId));
        return sellerPortalMapper.toSellerOrderDTO(sellerOrder);
    }

    @Override
    public Page<SellerOrderDTO> getSellerOrders(UUID sellerId, Pageable pageable) {
        return sellerOrderRepository.findBySellerId(sellerId, pageable)
                .map(sellerPortalMapper::toSellerOrderDTO);
    }

    @Override
    public SellerDashboardDTO getSellerDashboard(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", sellerId));

        BigDecimal totalEarnings = sellerOrderRepository.calculateTotalEarningsBySeller(sellerId)
                .orElse(BigDecimal.ZERO);
        long totalOrders = sellerOrderRepository.countTotalOrdersBySeller(sellerId);
        long activeProducts = sellerProductRepository.countActiveProductsBySeller(sellerId);

        SellerSummaryDTO summary = SellerSummaryDTO.builder()
                .tier(seller.getTier().name())
                .status(seller.getStatus().name())
                .commissionRate(seller.getCommissionRate())
                .totalRevenue(seller.getTotalRevenue())
                .currentBalance(seller.getCurrentBalance())
                .totalProducts((int) activeProducts)
                .totalOrders((int) totalOrders)
                .averageRating(seller.getAverageRating())
                .isVerified(seller.getIsVerified())
                .build();

        return SellerDashboardDTO.builder()
                .sellerId(seller.getId().toString())
                .storeName(seller.getStoreName())
                .summary(summary)
                .salesMetrics(SalesMetricsDTO.builder()
                        .totalSales(totalEarnings)
                        .totalOrders((int) totalOrders)
                        .build())
                .orderMetrics(OrderMetricsDTO.builder()
                        .totalOrders((int) totalOrders)
                        .build())
                .productMetrics(ProductMetricsDTO.builder()
                        .totalProducts((int) activeProducts)
                        .build())
                .build();
    }
}
