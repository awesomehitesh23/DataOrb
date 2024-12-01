package com.dataorb.pps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFinanceReportDTO {
    private String empId;
    private String firstName;
    private String lastName;
    private double totalPaid;
}
