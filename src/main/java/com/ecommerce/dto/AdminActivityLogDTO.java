package com.ecommerce.dto;

import com.ecommerce.entity.ActivityStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for AdminActivityLog.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminActivityLogDTO {

    private String id;

    private String adminUserId;

    private String adminUserName;

    private String action;

    private String entityType;

    private String entityId;

    private String description;

    private String changes;

    private String ipAddress;

    private String userAgent;

    private ActivityStatus status;

    private String errorMessage;

    @JsonProperty("createdAt")
    private String createdAt;
}
