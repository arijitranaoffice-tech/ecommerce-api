package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * User Notification entity.
 */
@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private NotificationType type = NotificationType.INFO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private NotificationPriority priority = NotificationPriority.NORMAL;

    @Column(nullable = false)
    @Builder.Default
    private Boolean read = false;

    private String actionUrl;

    private String relatedEntityType;
    private String relatedEntityId;

    @Column(length = 1000)
    private String metadata;

    private java.time.LocalDateTime readAt;
}
