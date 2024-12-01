package com.dataorb.pps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String eventType;
    private Double value;
    private LocalDate eventDate;
    private String notes;
}
