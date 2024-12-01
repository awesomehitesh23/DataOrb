package com.dataorb.pps.service;

import com.dataorb.pps.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PayrollService {
    void uploadFile(MultipartFile file);

    Long getTotalEmployees();

    List<EmployeeDto> getMonthlyJoiners(int month, int year);

    List<EmployeeDto> getMonthlyLeavers(int month, int year);

    MonthlySalaryReportDTO getMonthlySalaryReport(int month, int year);

    List<EmployeeFinanceReportDTO> getEmployeeFinanceReport(String employeeId);

    MonthlyAmountReleasedDTO getMonthlyAmountReleased(int month, int year);

    List<YearlyFinancialReportDTO> getYearlyFinancialReport(int year);
}
