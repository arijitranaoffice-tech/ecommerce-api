package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.service.AdminPortalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Controller for Admin Portal endpoints.
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminPortalController {

    private final AdminPortalService adminPortalService;

    // Dashboard endpoints
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<AdminDashboardDTO>> getDashboard() {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getDashboardData()));
    }

    @GetMapping("/dashboard/overview")
    public ResponseEntity<ApiResponse<OverviewStatsDTO>> getOverviewStats() {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getOverviewStats()));
    }

    @GetMapping("/dashboard/sales")
    public ResponseEntity<ApiResponse<SalesAnalyticsDTO>> getSalesAnalytics() {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getSalesAnalytics()));
    }

    @GetMapping("/dashboard/products")
    public ResponseEntity<ApiResponse<ProductAnalyticsDTO>> getProductAnalytics() {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getProductAnalytics()));
    }

    @GetMapping("/dashboard/customers")
    public ResponseEntity<ApiResponse<CustomerAnalyticsDTO>> getCustomerAnalytics() {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getCustomerAnalytics()));
    }

    @GetMapping("/dashboard/orders")
    public ResponseEntity<ApiResponse<OrderAnalyticsDTO>> getOrderAnalytics() {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getOrderAnalytics()));
    }

    // Banner endpoints
    @PostMapping("/banners")
    public ResponseEntity<ApiResponse<BannerDTO>> createBanner(@Valid @RequestBody CreateBannerRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Banner created", adminPortalService.createBanner(request)));
    }

    @PutMapping("/banners/{id}")
    public ResponseEntity<ApiResponse<BannerDTO>> updateBanner(
            @PathVariable UUID id,
            @Valid @RequestBody CreateBannerRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Banner updated", adminPortalService.updateBanner(id, request)));
    }

    @DeleteMapping("/banners/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBanner(@PathVariable UUID id) {
        adminPortalService.deleteBanner(id);
        return ResponseEntity.ok(ApiResponse.success("Banner deleted", null));
    }

    @GetMapping("/banners")
    public ResponseEntity<ApiResponse<Page<BannerDTO>>> getAllBanners(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getAllBanners(pageable)));
    }

    @GetMapping("/banners/{id}")
    public ResponseEntity<ApiResponse<BannerDTO>> getBannerById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getBannerById(id)));
    }

    @GetMapping("/banners/active")
    public ResponseEntity<ApiResponse<List<BannerDTO>>> getActiveBanners() {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getActiveBanners()));
    }

    @PostMapping("/banners/{id}/impression")
    public ResponseEntity<ApiResponse<Void>> incrementBannerImpression(@PathVariable UUID id) {
        adminPortalService.incrementBannerImpression(id);
        return ResponseEntity.ok(ApiResponse.success("Impression recorded", null));
    }

    @PostMapping("/banners/{id}/click")
    public ResponseEntity<ApiResponse<Void>> incrementBannerClick(@PathVariable UUID id) {
        adminPortalService.incrementBannerClick(id);
        return ResponseEntity.ok(ApiResponse.success("Click recorded", null));
    }

    // Coupon endpoints
    @PostMapping("/coupons")
    public ResponseEntity<ApiResponse<CouponDTO>> createCoupon(@Valid @RequestBody CreateCouponRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Coupon created", adminPortalService.createCoupon(request)));
    }

    @PutMapping("/coupons/{id}")
    public ResponseEntity<ApiResponse<CouponDTO>> updateCoupon(
            @PathVariable UUID id,
            @Valid @RequestBody CreateCouponRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Coupon updated", adminPortalService.updateCoupon(id, request)));
    }

    @DeleteMapping("/coupons/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCoupon(@PathVariable UUID id) {
        adminPortalService.deleteCoupon(id);
        return ResponseEntity.ok(ApiResponse.success("Coupon deleted", null));
    }

    @GetMapping("/coupons")
    public ResponseEntity<ApiResponse<Page<CouponDTO>>> getAllCoupons(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getAllCoupons(pageable)));
    }

    @GetMapping("/coupons/{id}")
    public ResponseEntity<ApiResponse<CouponDTO>> getCouponById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getCouponById(id)));
    }

    @PostMapping("/coupons/deactivate-expired")
    public ResponseEntity<ApiResponse<Void>> deactivateExpiredCoupons() {
        adminPortalService.deactivateExpiredCoupons();
        return ResponseEntity.ok(ApiResponse.success("Expired coupons deactivated", null));
    }

    // Activity Log endpoints
    @GetMapping("/activity-logs")
    public ResponseEntity<ApiResponse<Page<AdminActivityLogDTO>>> getActivityLogs(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getActivityLogs(pageable)));
    }

    @GetMapping("/activity-logs/user/{userId}")
    public ResponseEntity<ApiResponse<Page<AdminActivityLogDTO>>> getActivityLogsByUser(
            @PathVariable UUID userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.getActivityLogsByUser(userId, pageable)));
    }

    @GetMapping("/activity-logs/range")
    public ResponseEntity<ApiResponse<Page<AdminActivityLogDTO>>> getActivityLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                adminPortalService.getActivityLogsByDateRange(startDate, endDate, pageable)));
    }

    @PostMapping("/activity-logs")
    public ResponseEntity<ApiResponse<AdminActivityLogDTO>> logActivity(@Valid @RequestBody ActivityLogRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Activity logged", adminPortalService.logActivity(request)));
    }

    // Reports endpoints
    @GetMapping("/reports/sales")
    public ResponseEntity<ApiResponse<SalesReportDTO>> generateSalesReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                adminPortalService.generateSalesReport(startDate, endDate)));
    }

    @GetMapping("/reports/inventory")
    public ResponseEntity<ApiResponse<InventoryReportDTO>> generateInventoryReport() {
        return ResponseEntity.ok(ApiResponse.success(adminPortalService.generateInventoryReport()));
    }

    @GetMapping("/reports/customers")
    public ResponseEntity<ApiResponse<CustomerReportDTO>> generateCustomerReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                adminPortalService.generateCustomerReport(startDate, endDate)));
    }
}
