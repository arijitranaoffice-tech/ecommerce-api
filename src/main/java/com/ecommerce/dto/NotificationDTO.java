package com.ecommerce.dto;

import com.ecommerce.entity.NotificationPriority;
import com.ecommerce.entity.NotificationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Notification.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private String id;

    private String userId;

    private String title;

    private String message;

    private NotificationType type;

    private NotificationPriority priority;

    private Boolean read;

    private String actionUrl;

    private String relatedEntityType;

    private String relatedEntityId;

    private String readAt;

    @JsonProperty("createdAt")
    private String createdAt;
}
