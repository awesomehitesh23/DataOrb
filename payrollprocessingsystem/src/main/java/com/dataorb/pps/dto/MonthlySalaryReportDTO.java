package com.dataorb.pps.dto;

import lombok.Data;

@Data
public class MonthlySalaryReportDTO {
    private String month;
    private double totalSalary;
    private long totalEmployees;
}
