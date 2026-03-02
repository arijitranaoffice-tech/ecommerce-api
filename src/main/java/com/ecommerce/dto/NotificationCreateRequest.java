package com.ecommerce.dto;

import com.ecommerce.entity.NotificationPriority;
import com.ecommerce.entity.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO for creating notification.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCreateRequest {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Message is required")
    private String message;

    @Builder.Default
    private NotificationType type = NotificationType.INFO;

    @Builder.Default
    private NotificationPriority priority = NotificationPriority.NORMAL;

    private String actionUrl;

    private String relatedEntityType;

    private String relatedEntityId;
}
