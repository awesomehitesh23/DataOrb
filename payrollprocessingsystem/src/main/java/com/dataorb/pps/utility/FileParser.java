package com.dataorb.pps.utility;

import com.dataorb.pps.entity.Employee;
import com.dataorb.pps.entity.Event;
import com.dataorb.pps.entity.EventType;
import com.dataorb.pps.repository.EmployeeRepo;
import com.dataorb.pps.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileParser {

    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private EventRepo eventRepository;

    @Transactional
    public void parseFileAndSaveToDb(MultipartFile file) {
        Map<String, Employee> employeeMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }

                String[] data = line.split(",");
                if (data.length < 8) {
                    System.out.println("Skipping invalid row: " + line);
                    continue;
                }

                String empId = data[0].trim();
                Employee employee = employeeMap.computeIfAbsent(empId, id -> {
                    Employee emp = new Employee();
                    emp.setEmpid(id);
                    emp.setFirstname(data[1].trim());
                    emp.setLastname(data[2].trim());
                    emp.setDesignation(data[3].trim());
                    return employeeRepository.save(emp);
                });

                Event event = new Event();
                event.setEmployee(employee);
                event.setEventType(EventType.valueOf(data[4].trim().toUpperCase()));
                if (data.length > 5 && !data[5].trim().isEmpty()) {
                    event.setValue(Double.parseDouble(data[5].trim()));
                }
                event.setEventDate(LocalDate.parse(data[6].trim(), formatter));
                if (data.length > 7) {
                    event.setNotes(data[7].trim());
                }

                eventRepository.save(event);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + file);
            e.printStackTrace();
            throw new RuntimeException("Error parsing file", e);
        }
    }
}