package com.dataorb.pps.controller;

import com.dataorb.pps.dto.*;
import com.dataorb.pps.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/payroll")
public class PayrollServiceApis {

    @Autowired
    private PayrollService payrollService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadEmpRecordFile(@RequestParam("file") MultipartFile file) {
        String status = null;
        try {
            payrollService.uploadFile(file);
            status = "File uploaded and processed successfully!";
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (Exception e) {
            status = "Error in file upload";
            return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/total-employees")
    public ResponseEntity<Long> getTotalEmployees() {
        Long totalEmployees = payrollService.getTotalEmployees();
        return new ResponseEntity<>(totalEmployees, HttpStatus.OK);
    }

    @GetMapping("monthly-joiners")
    public ResponseEntity<List<EmployeeDto>> getMonthlyJoiners(@RequestParam("month") int month, @RequestParam("year") int year) {
        List<EmployeeDto> monthlyJoiners = payrollService.getMonthlyJoiners(month, year);
        return new ResponseEntity<>(monthlyJoiners, HttpStatus.OK);
    }

    @GetMapping("/monthly-leavers")
    public ResponseEntity<List<EmployeeDto>> getMonthlyLeavers(@RequestParam("month") int month, @RequestParam("year") int year) {
        List<EmployeeDto> monthlyLeavers = payrollService.getMonthlyLeavers(month, year);
        return new ResponseEntity<>(monthlyLeavers, HttpStatus.OK);
    }

    @GetMapping("/monthly-salary-report")
    public ResponseEntity<MonthlySalaryReportDTO> getMonthlySalaryReport(@RequestParam("month") int month, @RequestParam("year") int year) {
        MonthlySalaryReportDTO monthlySalaryReport = payrollService.getMonthlySalaryReport(month, year);
        return new ResponseEntity<>(monthlySalaryReport, HttpStatus.OK);
    }

    @GetMapping("/employee-finance-report")
    public ResponseEntity<List<EmployeeFinanceReportDTO>> getEmployeeFinanceReport(@RequestParam("employeeId") String employeeId) {
        List<EmployeeFinanceReportDTO> employeeFinanceReport = payrollService.getEmployeeFinanceReport(employeeId);
        return new ResponseEntity<>(employeeFinanceReport, HttpStatus.OK);
    }

    @GetMapping("/monthly-amount-released")
    public ResponseEntity<MonthlyAmountReleasedDTO> getMonthlyAmountReleased(@RequestParam int month, @RequestParam int year) {
        MonthlyAmountReleasedDTO monthlyAmountReleased = payrollService.getMonthlyAmountReleased(month, year);
        return new ResponseEntity<>(monthlyAmountReleased, HttpStatus.OK);
    }

    @GetMapping("/yearly-financial-report")
    public ResponseEntity<List<YearlyFinancialReportDTO>> getYearlyFinancialReport(@RequestParam int year) {
        List<YearlyFinancialReportDTO> yearlyFinancialReport = payrollService.getYearlyFinancialReport(year);
        return new ResponseEntity<>(yearlyFinancialReport, HttpStatus.OK);
    }
}
