package com.ecommerce.dto;

import com.ecommerce.entity.CommissionStatus;
import com.ecommerce.entity.CommissionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for DistributorCommission data transfer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorCommissionDTO {

    private String id;

    private String distributorId;

    private String distributorName;

    private String orderId;

    private String orderNumber;

    private String distributorOrderId;

    private String distributorOrderNumber;

    private BigDecimal amount;

    private BigDecimal rate;

    private CommissionType type;

    private CommissionStatus status;

    private LocalDate periodStartDate;

    private LocalDate periodEndDate;

    private LocalDateTime calculatedAt;

    private LocalDateTime paidAt;

    private String notes;

    private boolean isPaid;

    private String paymentReference;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;
}
