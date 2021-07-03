package com.alma.potatoHouse.repository;

import com.alma.potatoHouse.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(value = "SELECT * FROM schedule WHERE w_date like ? order by w_start", nativeQuery = true)
    List<Schedule> getScheduleByDate(String w_date);
}