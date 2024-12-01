package com.dataorb.pps.repository;

import com.dataorb.pps.entity.Event;
import com.dataorb.pps.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {
    List<Event> findByEventTypeAndEventDateBetween(EventType eventType, LocalDate startDate, LocalDate endDate);

    List<Event> findByEventDateBetween(LocalDate startDate, LocalDate endDate);

    List<Event> findByEmployeeEmpidAndEventTypeIn(String employeeId, List<EventType> salary);
}
