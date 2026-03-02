package com.ecommerce.service.impl;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.*;
import com.ecommerce.service.AdminPortalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of AdminPortalService.
 */
@Service
@RequiredArgsConstructor
public class AdminPortalServiceImpl implements AdminPortalService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BannerRepository bannerRepository;
    private final CouponRepository couponRepository;
    private final AdminActivityLogRepository adminActivityLogRepository;

    @Override
    public AdminDashboardDTO getDashboardData() {
        return AdminDashboardDTO.builder()
                .overviewStats(getOverviewStats())
                .salesAnalytics(getSalesAnalytics())
                .productAnalytics(getProductAnalytics())
                .customerAnalytics(getCustomerAnalytics())
                .orderAnalytics(getOrderAnalytics())
                .recentActivities(getRecentActivities())
                .build();
    }

    @Override
    public OverviewStatsDTO getOverviewStats() {
        BigDecimal totalRevenue = orderRepository.findAll().stream()
                .filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDate today = LocalDate.now();
        BigDecimal todayRevenue = orderRepository.findAll().stream()
                .filter(o -> o.getCreatedAt().toLocalDate().equals(today))
                .filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalOrders = orderRepository.count();
        long pendingOrders = orderRepository.findByStatus(OrderStatus.PENDING, Pageable.unpaged()).getTotalElements();
        long totalProducts = productRepository.count();
        long lowStockProducts = productRepository.findLowStockProducts().size();
        long totalCustomers = userRepository.countByRole(UserRole.CUSTOMER);

        return OverviewStatsDTO.builder()
                .totalRevenue(totalRevenue)
                .todayRevenue(todayRevenue)
                .totalOrders((int) totalOrders)
                .pendingOrders((int) pendingOrders)
                .totalProducts((int) totalProducts)
                .lowStockProducts((int) lowStockProducts)
                .totalCustomers((int) totalCustomers)
                .newCustomersToday(0)
                .build();
    }

    @Override
    public SalesAnalyticsDTO getSalesAnalytics() {
        return SalesAnalyticsDTO.builder()
                .dailySales(BigDecimal.ZERO)
                .weeklySales(BigDecimal.ZERO)
                .monthlySales(BigDecimal.ZERO)
                .yearlySales(BigDecimal.ZERO)
                .averageOrderValue(BigDecimal.ZERO)
                .growthPercentage(0)
                .salesTrend(new ArrayList<>())
                .revenueByCategory(new ArrayList<>())
                .build();
    }

    @Override
    public ProductAnalyticsDTO getProductAnalytics() {
        long totalProducts = productRepository.count();
        long activeProducts = productRepository.findAllByStatus(ProductStatus.ACTIVE, Pageable.unpaged()).getTotalElements();
        long draftProducts = productRepository.findAllByStatus(ProductStatus.DRAFT, Pageable.unpaged()).getTotalElements();
        long outOfStock = productRepository.findOutOfStockProducts().size();
        long lowStock = productRepository.findLowStockProducts().size();
        long totalCategories = categoryRepository.count();

        return ProductAnalyticsDTO.builder()
                .totalProducts((int) totalProducts)
                .activeProducts((int) activeProducts)
                .draftProducts((int) draftProducts)
                .archivedProducts(0)
                .outOfStockProducts((int) outOfStock)
                .lowStockProducts((int) lowStock)
                .totalCategories((int) totalCategories)
                .averageProductRating(0.0)
                .build();
    }

    @Override
    public CustomerAnalyticsDTO getCustomerAnalytics() {
        long totalCustomers = userRepository.countByRole(UserRole.CUSTOMER);

        return CustomerAnalyticsDTO.builder()
                .totalCustomers((int) totalCustomers)
                .activeCustomers((int) totalCustomers)
                .newCustomersThisMonth(0)
                .newCustomersThisWeek(0)
                .customerRetentionRate(0.0)
                .averageCustomerLifetimeValue(0.0)
                .build();
    }

    @Override
    public OrderAnalyticsDTO getOrderAnalytics() {
        long totalOrders = orderRepository.count();
        long pending = orderRepository.findByStatus(OrderStatus.PENDING, Pageable.unpaged()).getTotalElements();
        long processing = orderRepository.findByStatus(OrderStatus.PROCESSING, Pageable.unpaged()).getTotalElements();
        long shipped = orderRepository.findByStatus(OrderStatus.SHIPPED, Pageable.unpaged()).getTotalElements();
        long delivered = orderRepository.findByStatus(OrderStatus.DELIVERED, Pageable.unpaged()).getTotalElements();
        long cancelled = orderRepository.findByStatus(OrderStatus.CANCELLED, Pageable.unpaged()).getTotalElements();
        long returned = orderRepository.findByStatus(OrderStatus.RETURNED, Pageable.unpaged()).getTotalElements();

        return OrderAnalyticsDTO.builder()
                .totalOrders((int) totalOrders)
                .pendingOrders((int) pending)
                .processingOrders((int) processing)
                .shippedOrders((int) shipped)
                .deliveredOrders((int) delivered)
                .cancelledOrders((int) cancelled)
                .returnedOrders((int) returned)
                .fulfillmentRate(totalOrders > 0 ? (double) delivered / totalOrders * 100 : 0)
                .cancellationRate(totalOrders > 0 ? (double) cancelled / totalOrders * 100 : 0)
                .build();
    }

    private RecentActivitiesDTO getRecentActivities() {
        return RecentActivitiesDTO.builder()
                .recentOrders(new ArrayList<>())
                .recentActivities(new ArrayList<>())
                .lowStockProducts(new ArrayList<>())
                .newCustomers(new ArrayList<>())
                .build();
    }

    @Override
    @Transactional
    public BannerDTO createBanner(CreateBannerRequest request) {
        Banner banner = Banner.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .mobileImageUrl(request.getMobileImageUrl())
                .actionUrl(request.getActionUrl())
                .position(request.getPosition())
                .displayOrder(request.getDisplayOrder())
                .isActive(true)
                .validFrom(request.getValidFrom())
                .validUntil(request.getValidUntil())
                .targetAudience(request.getTargetAudience())
                .clickCount(0)
                .impressionCount(0)
                .build();
        banner = bannerRepository.save(banner);
        return mapToBannerDTO(banner);
    }

    @Override
    @Transactional
    public BannerDTO updateBanner(UUID bannerId, CreateBannerRequest request) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new ResourceNotFoundException("Banner", bannerId));

        if (request.getTitle() != null) banner.setTitle(request.getTitle());
        if (request.getDescription() != null) banner.setDescription(request.getDescription());
        if (request.getImageUrl() != null) banner.setImageUrl(request.getImageUrl());
        if (request.getMobileImageUrl() != null) banner.setMobileImageUrl(request.getMobileImageUrl());
        if (request.getActionUrl() != null) banner.setActionUrl(request.getActionUrl());
        if (request.getPosition() != null) banner.setPosition(request.getPosition());
        if (request.getDisplayOrder() != null) banner.setDisplayOrder(request.getDisplayOrder());
        if (request.getValidFrom() != null) banner.setValidFrom(request.getValidFrom());
        if (request.getValidUntil() != null) banner.setValidUntil(request.getValidUntil());

        banner = bannerRepository.save(banner);
        return mapToBannerDTO(banner);
    }

    @Override
    @Transactional
    public void deleteBanner(UUID bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new ResourceNotFoundException("Banner", bannerId));
        bannerRepository.delete(banner);
    }

    @Override
    public Page<BannerDTO> getAllBanners(Pageable pageable) {
        return bannerRepository.findAll(pageable).map(this::mapToBannerDTO);
    }

    @Override
    public BannerDTO getBannerById(UUID bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new ResourceNotFoundException("Banner", bannerId));
        return mapToBannerDTO(banner);
    }

    @Override
    public List<BannerDTO> getActiveBanners() {
        return bannerRepository.findActiveBanners(LocalDate.now())
                .stream()
                .map(this::mapToBannerDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void incrementBannerImpression(UUID bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new ResourceNotFoundException("Banner", bannerId));
        banner.setImpressionCount(banner.getImpressionCount() + 1);
        bannerRepository.save(banner);
    }

    @Override
    @Transactional
    public void incrementBannerClick(UUID bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new ResourceNotFoundException("Banner", bannerId));
        banner.setClickCount(banner.getClickCount() + 1);
        bannerRepository.save(banner);
    }

    @Override
    @Transactional
    public CouponDTO createCoupon(CreateCouponRequest request) {
        Coupon coupon = Coupon.builder()
                .code(request.getCode())
                .description(request.getDescription())
                .discountType(request.getDiscountType())
                .discountValue(request.getDiscountValue())
                .minPurchaseAmount(request.getMinPurchaseAmount())
                .maxDiscountAmount(request.getMaxDiscountAmount())
                .usageLimit(request.getUsageLimit())
                .usedCount(0)
                .usageLimitPerUser(request.getUsageLimitPerUser())
                .isActive(true)
                .validFrom(request.getValidFrom())
                .validUntil(request.getValidUntil())
                .applicableCategories(request.getApplicableCategories() != null ? request.getApplicableCategories() : new ArrayList<>())
                .applicableProducts(request.getApplicableProducts() != null ? request.getApplicableProducts() : new ArrayList<>())
                .excludedProducts(request.getExcludedProducts() != null ? request.getExcludedProducts() : new ArrayList<>())
                .termsAndConditions(request.getTermsAndConditions())
                .build();
        coupon = couponRepository.save(coupon);
        return mapToCouponDTO(coupon);
    }

    @Override
    @Transactional
    public CouponDTO updateCoupon(UUID couponId, CreateCouponRequest request) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", couponId));

        if (request.getDescription() != null) coupon.setDescription(request.getDescription());
        if (request.getDiscountType() != null) coupon.setDiscountType(request.getDiscountType());
        if (request.getDiscountValue() != null) coupon.setDiscountValue(request.getDiscountValue());
        if (request.getMinPurchaseAmount() != null) coupon.setMinPurchaseAmount(request.getMinPurchaseAmount());
        if (request.getMaxDiscountAmount() != null) coupon.setMaxDiscountAmount(request.getMaxDiscountAmount());
        if (request.getUsageLimit() != null) coupon.setUsageLimit(request.getUsageLimit());
        if (request.getUsageLimitPerUser() != null) coupon.setUsageLimitPerUser(request.getUsageLimitPerUser());
        if (request.getValidFrom() != null) coupon.setValidFrom(request.getValidFrom());
        if (request.getValidUntil() != null) coupon.setValidUntil(request.getValidUntil());
        if (request.getIsActive() != null) coupon.setIsActive(request.getIsActive());

        coupon = couponRepository.save(coupon);
        return mapToCouponDTO(coupon);
    }

    @Override
    @Transactional
    public void deleteCoupon(UUID couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", couponId));
        couponRepository.delete(coupon);
    }

    @Override
    public Page<CouponDTO> getAllCoupons(Pageable pageable) {
        return couponRepository.findAll(pageable).map(this::mapToCouponDTO);
    }

    @Override
    public CouponDTO getCouponById(UUID couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", couponId));
        return mapToCouponDTO(coupon);
    }

    @Override
    @Transactional
    public void deactivateExpiredCoupons() {
        couponRepository.findByValidUntilBefore(LocalDate.now())
                .forEach(coupon -> {
                    coupon.setIsActive(false);
                    couponRepository.save(coupon);
                });
    }

    @Override
    public Page<AdminActivityLogDTO> getActivityLogs(Pageable pageable) {
        return adminActivityLogRepository.findAll(pageable).map(this::mapToActivityLogDTO);
    }

    @Override
    public Page<AdminActivityLogDTO> getActivityLogsByUser(UUID userId, Pageable pageable) {
        return adminActivityLogRepository.findByAdminUserId(userId, pageable).map(this::mapToActivityLogDTO);
    }

    @Override
    public Page<AdminActivityLogDTO> getActivityLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return adminActivityLogRepository.findByDateRange(startDate, endDate, pageable).map(this::mapToActivityLogDTO);
    }

    @Override
    @Transactional
    public AdminActivityLogDTO logActivity(ActivityLogRequest request) {
        // For now, return a basic log - in production, get actual user from context
        AdminActivityLog log = AdminActivityLog.builder()
                .action(request.getAction())
                .entityType(request.getEntityType())
                .entityId(request.getEntityId())
                .description(request.getDescription())
                .changes(request.getChanges())
                .ipAddress(request.getIpAddress() != null ? request.getIpAddress() : "unknown")
                .userAgent(request.getUserAgent())
                .status(ActivityStatus.SUCCESS)
                .metadata(request.getMetadata())
                .build();
        log = adminActivityLogRepository.save(log);
        return mapToActivityLogDTO(log);
    }

    @Override
    @Transactional
    public void cleanupOldActivityLogs(LocalDateTime beforeDate) {
        adminActivityLogRepository.deleteByCreatedAtBefore(beforeDate);
    }

    @Override
    public SalesReportDTO generateSalesReport(LocalDate startDate, LocalDate endDate) {
        return SalesReportDTO.builder()
                .startDate(startDate)
                .endDate(endDate)
                .totalRevenue(BigDecimal.ZERO)
                .totalOrders(0)
                .averageOrderValue(BigDecimal.ZERO)
                .totalItemsSold(0)
                .salesByCategory(new ArrayList<>())
                .salesByDate(new ArrayList<>())
                .topProducts(new ArrayList<>())
                .build();
    }

    @Override
    public InventoryReportDTO generateInventoryReport() {
        return InventoryReportDTO.builder()
                .totalProducts(0)
                .totalValue(0)
                .inStockProducts(0)
                .lowStockProducts(0)
                .outOfStockProducts(0)
                .inventoryTurnover(BigDecimal.ZERO)
                .daysOfInventory(0)
                .build();
    }

    @Override
    public CustomerReportDTO generateCustomerReport(LocalDate startDate, LocalDate endDate) {
        return CustomerReportDTO.builder()
                .totalCustomers(0)
                .newCustomers(0)
                .activeCustomers(0)
                .retentionRate(0.0)
                .averageOrderValue(0.0)
                .customerLifetimeValue(0.0)
                .build();
    }

    private BannerDTO mapToBannerDTO(Banner banner) {
        return BannerDTO.builder()
                .id(banner.getId().toString())
                .title(banner.getTitle())
                .description(banner.getDescription())
                .imageUrl(banner.getImageUrl())
                .mobileImageUrl(banner.getMobileImageUrl())
                .actionUrl(banner.getActionUrl())
                .position(banner.getPosition())
                .displayOrder(banner.getDisplayOrder())
                .isActive(banner.getIsActive())
                .validFrom(banner.getValidFrom())
                .validUntil(banner.getValidUntil())
                .clickCount(banner.getClickCount())
                .impressionCount(banner.getImpressionCount())
                .createdAt(banner.getCreatedAt() != null ? banner.getCreatedAt().toString() : null)
                .build();
    }

    private CouponDTO mapToCouponDTO(Coupon coupon) {
        return CouponDTO.builder()
                .id(coupon.getId().toString())
                .code(coupon.getCode())
                .description(coupon.getDescription())
                .discountType(coupon.getDiscountType())
                .discountValue(coupon.getDiscountValue())
                .minPurchaseAmount(coupon.getMinPurchaseAmount())
                .maxDiscountAmount(coupon.getMaxDiscountAmount())
                .usageLimit(coupon.getUsageLimit())
                .usedCount(coupon.getUsedCount())
                .usageLimitPerUser(coupon.getUsageLimitPerUser())
                .isActive(coupon.getIsActive())
                .validFrom(coupon.getValidFrom())
                .validUntil(coupon.getValidUntil())
                .applicableCategories(coupon.getApplicableCategories())
                .applicableProducts(coupon.getApplicableProducts())
                .createdAt(coupon.getCreatedAt() != null ? coupon.getCreatedAt().toString() : null)
                .build();
    }

    private AdminActivityLogDTO mapToActivityLogDTO(AdminActivityLog log) {
        return AdminActivityLogDTO.builder()
                .id(log.getId().toString())
                .action(log.getAction())
                .entityType(log.getEntityType())
                .entityId(log.getEntityId())
                .description(log.getDescription())
                .changes(log.getChanges())
                .ipAddress(log.getIpAddress())
                .userAgent(log.getUserAgent())
                .status(log.getStatus())
                .errorMessage(log.getErrorMessage())
                .createdAt(log.getCreatedAt() != null ? log.getCreatedAt().toString() : null)
                .build();
    }
}
