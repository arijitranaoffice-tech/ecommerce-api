package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for Sales Report.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesReportDTO {

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal totalRevenue;

    private Integer totalOrders;

    private BigDecimal averageOrderValue;

    private Integer totalItemsSold;

    private List<SalesByCategoryDTO> salesByCategory;

    private List<SalesByDateDTO> salesByDate;

    private List<TopProductDTO> topProducts;
}
