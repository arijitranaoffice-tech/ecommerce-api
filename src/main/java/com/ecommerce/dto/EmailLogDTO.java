package com.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Email Log.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailLogDTO {

    private String id;

    private String recipient;

    private String subject;

    private String body;

    private String status;

    private String sentAt;

    private String errorMessage;

    private String messageType;

    private String relatedEntityType;

    private String relatedEntityId;

    @JsonProperty("createdAt")
    private String createdAt;
}
