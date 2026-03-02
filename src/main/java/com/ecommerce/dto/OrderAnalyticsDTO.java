package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for order analytics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAnalyticsDTO {

    private Integer totalOrders;

    private Integer pendingOrders;

    private Integer processingOrders;

    private Integer shippedOrders;

    private Integer deliveredOrders;

    private Integer cancelledOrders;

    private Integer returnedOrders;

    private Double fulfillmentRate;

    private Double cancellationRate;
}
