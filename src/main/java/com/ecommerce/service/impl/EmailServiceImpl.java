package com.ecommerce.service.impl;

import com.ecommerce.dto.*;
import com.ecommerce.entity.EmailLog;
import com.ecommerce.entity.EmailStatus;
import com.ecommerce.entity.Order;
import com.ecommerce.repository.EmailLogRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of EmailService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailLogRepository emailLogRepository;
    private final OrderRepository orderRepository;

    @Value("${app.email.from}")
    private String fromEmail;

    @Override
    @Transactional
    public EmailLogDTO sendEmail(SendEmailRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(request.getRecipient());
            message.setSubject(request.getSubject());
            message.setText(request.getBody());

            mailSender.send(message);

            EmailLog emailLog = EmailLog.builder()
                    .recipient(request.getRecipient())
                    .subject(request.getSubject())
                    .body(request.getBody())
                    .status(EmailStatus.SENT)
                    .sentAt(LocalDateTime.now())
                    .messageType(request.getMessageType())
                    .relatedEntityType(request.getRelatedEntityType())
                    .relatedEntityId(request.getRelatedEntityId())
                    .build();

            emailLog = emailLogRepository.save(emailLog);
            return mapToEmailLogDTO(emailLog);

        } catch (Exception e) {
            log.error("Failed to send email: {}", e.getMessage());
            EmailLog emailLog = EmailLog.builder()
                    .recipient(request.getRecipient())
                    .subject(request.getSubject())
                    .body(request.getBody())
                    .status(EmailStatus.FAILED)
                    .errorMessage(e.getMessage())
                    .messageType(request.getMessageType())
                    .build();
            emailLog = emailLogRepository.save(emailLog);
            return mapToEmailLogDTO(emailLog);
        }
    }

    @Override
    public void sendOrderConfirmationEmail(String orderId, String customerEmail) {
        Order order = orderRepository.findById(UUID.fromString(orderId)).orElse(null);
        if (order == null) return;

        String subject = "Order Confirmation - " + order.getOrderNumber();
        String body = String.format("Dear Customer,\n\nYour order %s has been confirmed.\nTotal: $%s\n\nThank you for shopping with us!",
                order.getOrderNumber(), order.getTotalAmount());

        SendEmailRequest request = SendEmailRequest.builder()
                .recipient(customerEmail)
                .subject(subject)
                .body(body)
                .messageType("ORDER_CONFIRMATION")
                .relatedEntityType("ORDER")
                .relatedEntityId(orderId)
                .build();

        sendEmail(request);
    }

    @Override
    public void sendPasswordResetEmail(String userEmail, String resetToken) {
        String subject = "Password Reset Request";
        String body = String.format("Click the link to reset your password: http://localhost:3000/reset-password?token=%s\n\nThis link expires in 1 hour.", resetToken);

        SendEmailRequest request = SendEmailRequest.builder()
                .recipient(userEmail)
                .subject(subject)
                .body(body)
                .messageType("PASSWORD_RESET")
                .build();

        sendEmail(request);
    }

    @Override
    public void sendWelcomeEmail(String userEmail, String userName) {
        String subject = "Welcome to Our Store!";
        String body = String.format("Dear %s,\n\nWelcome to our store! Start exploring amazing products.\n\nBest regards,\nThe Team", userName);

        SendEmailRequest request = SendEmailRequest.builder()
                .recipient(userEmail)
                .subject(subject)
                .body(body)
                .messageType("WELCOME")
                .build();

        sendEmail(request);
    }

    @Override
    public void sendShipmentNotification(String userEmail, String trackingNumber) {
        String subject = "Your Order Has Been Shipped!";
        String body = String.format("Great news! Your order has been shipped.\nTracking Number: %s\n\nTrack your package on our website.", trackingNumber);

        SendEmailRequest request = SendEmailRequest.builder()
                .recipient(userEmail)
                .subject(subject)
                .body(body)
                .messageType("SHIPMENT_NOTIFICATION")
                .build();

        sendEmail(request);
    }

    @Override
    public EmailLogDTO getEmailLogById(UUID logId) {
        EmailLog emailLog = emailLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Email log not found"));
        return mapToEmailLogDTO(emailLog);
    }

    @Override
    public Page<EmailLogDTO> getEmailLogs(Pageable pageable) {
        return emailLogRepository.findAll(pageable).map(this::mapToEmailLogDTO);
    }

    @Override
    public Page<EmailLogDTO> getEmailLogsByRecipient(String recipient, Pageable pageable) {
        return emailLogRepository.findByRecipient(recipient, pageable).map(this::mapToEmailLogDTO);
    }

    @Override
    public Page<EmailLogDTO> getEmailLogsByStatus(String status, Pageable pageable) {
        return emailLogRepository.findByStatus(EmailStatus.valueOf(status), pageable).map(this::mapToEmailLogDTO);
    }

    @Override
    public long countSentEmailsByRecipient(String recipient) {
        return emailLogRepository.countSentEmailsByRecipient(recipient);
    }

    @Override
    public List<EmailLogDTO> getPendingEmails() {
        return emailLogRepository.findPendingEmails()
                .stream()
                .map(this::mapToEmailLogDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void processPendingEmails() {
        List<EmailLog> pendingEmails = emailLogRepository.findPendingEmails();
        for (EmailLog email : pendingEmails) {
            SendEmailRequest request = SendEmailRequest.builder()
                    .recipient(email.getRecipient())
                    .subject(email.getSubject())
                    .body(email.getBody())
                    .messageType(email.getMessageType())
                    .build();
            sendEmail(request);
        }
    }

    private EmailLogDTO mapToEmailLogDTO(EmailLog emailLog) {
        return EmailLogDTO.builder()
                .id(emailLog.getId().toString())
                .recipient(emailLog.getRecipient())
                .subject(emailLog.getSubject())
                .body(emailLog.getBody())
                .status(emailLog.getStatus().name())
                .sentAt(emailLog.getSentAt() != null ? emailLog.getSentAt().toString() : null)
                .errorMessage(emailLog.getErrorMessage())
                .messageType(emailLog.getMessageType())
                .relatedEntityType(emailLog.getRelatedEntityType())
                .relatedEntityId(emailLog.getRelatedEntityId())
                .createdAt(emailLog.getCreatedAt() != null ? emailLog.getCreatedAt().toString() : null)
                .build();
    }
}
