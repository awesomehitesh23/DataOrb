package com.dataorb.pps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyAmountReleasedDTO {
    private String month;
    private double totalAmount;
    private long totalEmployees;
}
