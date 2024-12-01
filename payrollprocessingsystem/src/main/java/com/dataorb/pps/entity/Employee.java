package com.dataorb.pps.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String empid;
    private String firstname;
    private String lastname;
    private String designation;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Event> events = new HashSet<>();
}
