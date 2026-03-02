package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.service.EmailService;
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
 * Controller for Email endpoints.
 */
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<EmailLogDTO>> sendEmail(@Valid @RequestBody SendEmailRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Email sent", emailService.sendEmail(request)));
    }

    @PostMapping("/send/order-confirmation")
    public ResponseEntity<ApiResponse<Void>> sendOrderConfirmation(
            @RequestParam String orderId,
            @RequestParam String customerEmail) {
        emailService.sendOrderConfirmationEmail(orderId, customerEmail);
        return ResponseEntity.ok(ApiResponse.success("Order confirmation sent", null));
    }

    @PostMapping("/send/password-reset")
    public ResponseEntity<ApiResponse<Void>> sendPasswordReset(
            @RequestParam String userEmail,
            @RequestParam String resetToken) {
        emailService.sendPasswordResetEmail(userEmail, resetToken);
        return ResponseEntity.ok(ApiResponse.success("Password reset email sent", null));
    }

    @PostMapping("/send/welcome")
    public ResponseEntity<ApiResponse<Void>> sendWelcomeEmail(
            @RequestParam String userEmail,
            @RequestParam String userName) {
        emailService.sendWelcomeEmail(userEmail, userName);
        return ResponseEntity.ok(ApiResponse.success("Welcome email sent", null));
    }

    @PostMapping("/send/shipment")
    public ResponseEntity<ApiResponse<Void>> sendShipmentNotification(
            @RequestParam String userEmail,
            @RequestParam String trackingNumber) {
        emailService.sendShipmentNotification(userEmail, trackingNumber);
        return ResponseEntity.ok(ApiResponse.success("Shipment notification sent", null));
    }

    @GetMapping("/logs/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<EmailLogDTO>> getEmailLog(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(emailService.getEmailLogById(id)));
    }

    @GetMapping("/logs")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Page<EmailLogDTO>>> getEmailLogs(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(emailService.getEmailLogs(pageable)));
    }

    @GetMapping("/logs/recipient/{recipient}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Page<EmailLogDTO>>> getEmailLogsByRecipient(@PathVariable String recipient, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(emailService.getEmailLogsByRecipient(recipient, pageable)));
    }

    @GetMapping("/logs/status/{status}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Page<EmailLogDTO>>> getEmailLogsByStatus(@PathVariable String status, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(emailService.getEmailLogsByStatus(status, pageable)));
    }

    @GetMapping("/logs/count/{recipient}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Long>> countSentEmails(@PathVariable String recipient) {
        return ResponseEntity.ok(ApiResponse.success(emailService.countSentEmailsByRecipient(recipient)));
    }

    @GetMapping("/logs/pending")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<EmailLogDTO>>> getPendingEmails() {
        return ResponseEntity.ok(ApiResponse.success(emailService.getPendingEmails()));
    }

    @PostMapping("/logs/process-pending")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> processPendingEmails() {
        emailService.processPendingEmails();
        return ResponseEntity.ok(ApiResponse.success("Pending emails processed", null));
    }
}
