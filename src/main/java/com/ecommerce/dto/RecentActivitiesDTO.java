package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for recent activities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentActivitiesDTO {

    private List<RecentOrderDTO> recentOrders;

    private List<AdminActivityLogDTO> recentActivities;

    private List<ProductDTO> lowStockProducts;

    private List<UserDTO> newCustomers;
}
