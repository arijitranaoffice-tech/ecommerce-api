package com.ecommerce.dto;

import com.ecommerce.entity.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Request DTO for creating a distributor order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDistributorOrderRequest {

    @NotEmpty(message = "Order items are required")
    private List<OrderItemRequest> items;

    private UUID shippingAddressId;

    private UUID billingAddressId;

    private String distributorNote;

    @Builder.Default
    private PaymentMethod paymentMethod = PaymentMethod.COD;

    private boolean isFulfillmentOrder;
}
