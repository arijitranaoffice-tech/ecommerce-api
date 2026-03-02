package com.ecommerce.service;

import com.ecommerce.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Email operations.
 */
public interface EmailService {

    EmailLogDTO sendEmail(SendEmailRequest request);

    void sendOrderConfirmationEmail(String orderId, String customerEmail);

    void sendPasswordResetEmail(String userEmail, String resetToken);

    void sendWelcomeEmail(String userEmail, String userName);

    void sendShipmentNotification(String userEmail, String trackingNumber);

    EmailLogDTO getEmailLogById(UUID logId);

    Page<EmailLogDTO> getEmailLogs(Pageable pageable);

    Page<EmailLogDTO> getEmailLogsByRecipient(String recipient, Pageable pageable);

    Page<EmailLogDTO> getEmailLogsByStatus(String status, Pageable pageable);

    long countSentEmailsByRecipient(String recipient);

    List<EmailLogDTO> getPendingEmails();

    void processPendingEmails();
}
