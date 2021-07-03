package com.alma.potatoHouse.repository;

import com.alma.potatoHouse.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    @Query(value = "SELECT * FROM workouts WHERE is_active = ?1", nativeQuery = true)
    List<Workout> getAllWorkoutsByStatus(boolean is_active);
}
