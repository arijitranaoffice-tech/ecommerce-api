package com.ecommerce.service;

import com.ecommerce.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for Admin Portal operations.
 */
public interface AdminPortalService {

    // Admin Dashboard
    AdminDashboardDTO getDashboardData();

    OverviewStatsDTO getOverviewStats();

    SalesAnalyticsDTO getSalesAnalytics();

    ProductAnalyticsDTO getProductAnalytics();

    CustomerAnalyticsDTO getCustomerAnalytics();

    OrderAnalyticsDTO getOrderAnalytics();

    // Banner Management
    BannerDTO createBanner(CreateBannerRequest request);

    BannerDTO updateBanner(UUID bannerId, CreateBannerRequest request);

    void deleteBanner(UUID bannerId);

    Page<BannerDTO> getAllBanners(Pageable pageable);

    BannerDTO getBannerById(UUID bannerId);

    List<BannerDTO> getActiveBanners();

    void incrementBannerImpression(UUID bannerId);

    void incrementBannerClick(UUID bannerId);

    // Coupon Management
    CouponDTO createCoupon(CreateCouponRequest request);

    CouponDTO updateCoupon(UUID couponId, CreateCouponRequest request);

    void deleteCoupon(UUID couponId);

    Page<CouponDTO> getAllCoupons(Pageable pageable);

    CouponDTO getCouponById(UUID couponId);

    void deactivateExpiredCoupons();

    // Activity Log
    Page<AdminActivityLogDTO> getActivityLogs(Pageable pageable);

    Page<AdminActivityLogDTO> getActivityLogsByUser(UUID userId, Pageable pageable);

    Page<AdminActivityLogDTO> getActivityLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    AdminActivityLogDTO logActivity(ActivityLogRequest request);

    void cleanupOldActivityLogs(LocalDateTime beforeDate);

    // Reports
    SalesReportDTO generateSalesReport(LocalDate startDate, LocalDate endDate);

    InventoryReportDTO generateInventoryReport();

    CustomerReportDTO generateCustomerReport(LocalDate startDate, LocalDate endDate);
}
