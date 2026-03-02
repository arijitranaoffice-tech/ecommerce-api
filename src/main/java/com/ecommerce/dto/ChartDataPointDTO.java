package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for chart data points.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChartDataPointDTO {

    private String label;

    private Number value;

    private String date;
}
