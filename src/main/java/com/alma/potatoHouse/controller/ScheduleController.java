package com.alma.potatoHouse.controller;

import com.alma.potatoHouse.entities.Actions;
import com.alma.potatoHouse.entities.Schedule;
import com.alma.potatoHouse.entities.Workout;
import com.alma.potatoHouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alma.potatoHouse.Assist.*;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    @Autowired
    private ActionsRepository actionsRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private WorkoutRepository workoutRepository;

    @GetMapping("/schedule")
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        ArrayList<Schedule> schedules =new ArrayList(scheduleRepository.findAll());
        if (!schedules.isEmpty())
            return new ResponseEntity(schedules, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable(value = "id") Integer scheduleId){
        try {
            Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
            return new ResponseEntity(schedule.get(),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/schedule-date/{w_date}")
    public ResponseEntity<Schedule> getScheduleByDate(@PathVariable(value = "w_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String w_date) {
        try {
            ArrayList<Schedule> schedules = new ArrayList(scheduleRepository.getScheduleByDate(w_date));
            if (!schedules.isEmpty())
                return new ResponseEntity(schedules, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/schedule")
    public ResponseEntity createSchedule(@RequestBody Schedule schedule) {
        try {
            scheduleRepository.save(schedule);
            Actions actions = new Actions(schedule.getW_id(),WORKOUT,schedule.getId(), SCHEDULE, ADD,
                    String.format("WORKOUT %s HAS SCHEDULED!",
                            schedule.getW_title() + " " + schedule.getW_date() + " " + schedule.getW_start() + "-" + schedule.getW_end()),
                    LocalDateTime.now());
            actionsRepository.save(actions);
            updateWorkout(schedule.getW_id(),1);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PutMapping("/schedule")
//    public ResponseEntity createSchedule(@RequestBody Schedule schedule) {
//        try {
//            Integer sId = schedule.getId();
//            if (scheduleRepository.existsById(sId))
//                return updateSchedule(sId, schedule);
//            scheduleRepository.save(schedule);
//            Actions actions = new Actions(schedule.getId(), SCHEDULE, ADD,
//                    String.format("WORKOUT %s HAS SCHEDULED!", schedule.getW_title() + " " + schedule.getW_date() + " " + schedule.getW_start() + "-" + schedule.getW_end()), LocalDateTime.now());
//            actionsRepository.save(actions);
//            return new ResponseEntity(HttpStatus.OK);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

//    @PostMapping("/schedule/{id}")
//    public ResponseEntity updateSchedule(
//            @PathVariable(value = "id") Integer scheduleId, @RequestBody Schedule scheduleDetails){
//        try {
//            Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
//            List<String> changes = schedule.get().updateSchedule(scheduleDetails);
//            Schedule updatedSchedule = scheduleRepository.save(scheduleDetails);
//            for (String change : changes)
//                actionsRepository.save(new Actions(scheduleId, SCHEDULE, UPDATE, change, LocalDateTime.now()));
//            return new ResponseEntity(HttpStatus.OK);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @DeleteMapping("/schedule/{id}")
    public ResponseEntity deleteScheduleById(@PathVariable(value = "id") int id) {
        try {
            Optional<Schedule> schedule = scheduleRepository.findById(id);
            scheduleRepository.deleteById(id);
            actionsRepository.save(new Actions(schedule.get().getW_id(),WORKOUT,id, SCHEDULE, DELETE,
                    String.format("WORKOUT %s WAS REMOVED FROM %s %s-%s",
                            schedule.get().getW_title(),schedule.get().getW_date(),schedule.get().getW_start(),schedule.get().getW_end()),
                    LocalDateTime.now()));
            updateWorkout(schedule.get().getW_id(),-1);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    private void updateWorkout(int w_id, int many_times){
        Optional<Workout> workout=workoutRepository.findById(w_id);
        workout.get().setMany_times(workout.get().getMany_times()+many_times);
        workoutRepository.save(workout.get());
    }
}