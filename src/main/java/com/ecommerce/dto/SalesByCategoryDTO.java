package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for sales by category.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesByCategoryDTO {

    private String categoryId;

    private String categoryName;

    private BigDecimal totalSales;

    private Integer unitsSold;
}
