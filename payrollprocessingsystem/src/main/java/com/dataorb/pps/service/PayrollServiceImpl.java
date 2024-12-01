package com.dataorb.pps.service;

import com.dataorb.pps.dto.*;
import com.dataorb.pps.entity.Employee;
import com.dataorb.pps.entity.Event;
import com.dataorb.pps.entity.EventType;
import com.dataorb.pps.repository.EmployeeRepo;
import com.dataorb.pps.repository.EventRepo;
import com.dataorb.pps.utility.FileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private FileParser fileParser;

    @Override
    public void uploadFile(MultipartFile file) {
        fileParser.parseFileAndSaveToDb(file);
    }

    @Override
    public Long getTotalEmployees() {
        return employeeRepo.count();
    }

    @Override
    public List<EmployeeDto> getMonthlyJoiners(int month, int year) {
        LocalDate starDate = LocalDate.parse("01-" + month + "-" + year, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDate = starDate.withDayOfMonth(starDate.lengthOfMonth());
        return eventRepo.findByEventTypeAndEventDateBetween(EventType.ONBOARD, starDate, endDate)
                .stream()
                .map(Event::getEmployee)
                .distinct()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> getMonthlyLeavers(int month, int year) {
        LocalDate starDate = LocalDate.parse("01-" + month + "-" + year, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDate = starDate.withDayOfMonth(starDate.lengthOfMonth());
        return eventRepo.findByEventTypeAndEventDateBetween(EventType.EXIT, starDate, endDate)
                .stream()
                .map(Event::getEmployee)
                .distinct()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public MonthlySalaryReportDTO getMonthlySalaryReport(int month, int year) {
        LocalDate starDate = LocalDate.parse("01-" + month + "-" + year, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDate = starDate.withDayOfMonth(starDate.lengthOfMonth());
        List<Event> monthlyEvents = eventRepo.findByEventTypeAndEventDateBetween(EventType.SALARY, starDate, endDate);
        Long totalEmployees = Long.valueOf(monthlyEvents.size());
        double totalSalary = monthlyEvents.stream().mapToDouble(Event::getValue).sum();
        String monthName = starDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        MonthlySalaryReportDTO monthlySalaryReportDTO = new MonthlySalaryReportDTO();
        monthlySalaryReportDTO.setTotalSalary(totalSalary);
        monthlySalaryReportDTO.setMonth(monthName);
        monthlySalaryReportDTO.setTotalEmployees(totalEmployees);
        return monthlySalaryReportDTO;
    }

    @Override
    public List<EmployeeFinanceReportDTO> getEmployeeFinanceReport(String employeeId) {
        Map<Employee, Double> employeeTotalMap = eventRepo.findByEmployeeEmpidAndEventTypeIn(
                        employeeId,
                        List.of(EventType.SALARY, EventType.BONUS, EventType.REIMBURSEMENT)
                ).stream()
                .filter(event -> event.getEmployee() != null)
                .collect(Collectors.groupingBy(
                        Event::getEmployee,
                        Collectors.summingDouble(event -> event.getValue() != null ? event.getValue() : 0.0)
                ));

        return employeeTotalMap.entrySet().stream()
                .map(entry -> new EmployeeFinanceReportDTO(
                        entry.getKey().getEmpid(),
                        entry.getKey().getFirstname(),
                        entry.getKey().getLastname(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public MonthlyAmountReleasedDTO getMonthlyAmountReleased(int month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        double totalAmount = eventRepo.findByEventDateBetween(startDate, endDate)
                .stream()
                .filter(event -> List.of(EventType.SALARY, EventType.BONUS, EventType.REIMBURSEMENT).contains(event.getEventType()))
                .mapToDouble(Event::getValue)
                .sum();

        long totalEmployees = eventRepo.findByEventDateBetween(startDate, endDate)
                .stream()
                .map(Event::getEmployee)
                .distinct()
                .count();

        return new MonthlyAmountReleasedDTO(String.valueOf(month), totalAmount, totalEmployees);
    }

    @Override
    public List<YearlyFinancialReportDTO> getYearlyFinancialReport(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.withDayOfYear(startDate.lengthOfYear());

        List<Event> events = eventRepo.findByEventDateBetween(startDate, endDate);
        List<YearlyFinancialReportDTO> yearlyFinancialReportDTOS = events
                .stream()
                .map(event -> new YearlyFinancialReportDTO(
                        event.getEventType().toString(),
                        event.getEmployee().getEmpid(),
                        event.getEventDate(),
                        event.getValue()
                ))
                .collect(Collectors.toList());
        return yearlyFinancialReportDTOS;
    }
}
