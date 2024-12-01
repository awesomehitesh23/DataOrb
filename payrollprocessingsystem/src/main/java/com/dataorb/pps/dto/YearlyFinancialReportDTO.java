package com.dataorb.pps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlyFinancialReportDTO {
    private String eventType;
    private String empId;
    private LocalDate eventDate;
    private double eventValue;


}
