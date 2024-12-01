package com.dataorb.pps.dto;

import com.dataorb.pps.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String empid;
    private String firstname;
    private String lastname;
    private String designation;

    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.empid = employee.getEmpid();
        this.firstname = employee.getFirstname();
        this.lastname = employee.getLastname();
        this.designation = employee.getDesignation();
    }
}
