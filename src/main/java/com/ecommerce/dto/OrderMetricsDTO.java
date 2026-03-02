package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Order Metrics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMetricsDTO {

    private Integer pendingOrders;

    private Integer processingOrders;

    private Integer shippedOrders;

    private Integer deliveredOrders;

    private Integer cancelledOrders;

    private Integer totalOrders;

    private Double fulfillmentRate;
}
