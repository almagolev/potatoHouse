package com.alma.potatoHouse.controller;

import com.alma.potatoHouse.entities.*;
import com.alma.potatoHouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WorkoutsController {

    @Autowired
    private ActionsRepository actionsRepository;
    @Autowired
    private WorkoutRepository workoutRepository;

    @GetMapping("/workouts")
    public ResponseEntity<List<Workout>> getAllWorkouts(){
        ArrayList<Workout> workouts=new ArrayList(workoutRepository.findAll());
        if (!workouts.isEmpty())
            return new ResponseEntity(workouts, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/workouts/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable(value = "id") Integer workoutId){
        try {
            Optional<Workout> workout = workoutRepository.findById(workoutId);
            return new ResponseEntity(workout.get(),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/workouts")
    public ResponseEntity createWorkout(@RequestBody Workout workout) {
        try {
            workoutRepository.save(workout);
            Actions actions = new Actions(workout.getId(), WORKOUT,0,"", ADD,
                    String.format("%s %s HAS ADDED!", WORKOUT, workout.getTitle()), LocalDateTime.now());
            actionsRepository.save(actions);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //    @PutMapping("/workouts")
//    public ResponseEntity createWorkout(@RequestBody Workout workout) throws Exception {
//        Integer wId=workout.getId();
//        if(workoutRepository.existsById(wId))
//            return updateWorkout(wId,workout);
//        workoutRepository.save(workout);
//        Actions actions = new Actions(workout.getId(), WORKOUT, ADD,
//                String.format("%s %s HAS ADDED!", WORKOUT,workout.getTitle()), LocalDateTime.now());
//        actionsRepository.save(actions);
//        return new ResponseEntity(HttpStatus.OK);
//    }

//    @PutMapping("/workouts/{id}")
//    public ResponseEntity updateWorkout(
//            @PathVariable(value = "id") Integer workoutId, @RequestBody Workout workoutDetails) throws Exception {
//        Workout workout = workoutRepository.findById(workoutId).orElseThrow(() -> new Exception("WORKOUT " + workoutId + " NOT FOUND!"));
//        List<String> changes = workout.updateWorkout(workoutDetails);
//        final Workout updatedWorkout = workoutRepository.save(workoutDetails);
//        for(String change:changes)
//            actionsRepository.save(new Actions(workoutId, WORKOUT, UPDATE, change, LocalDateTime.now()));
//        return ResponseEntity.ok(updatedWorkout);
//    }

    @GetMapping("/workouts-active/{is_active}")
    public ResponseEntity<Workout> getAllWorkoutsByStatus(@PathVariable(value = "is_active") boolean is_active) {
        ArrayList<Workout> workouts=new ArrayList(workoutRepository.getAllWorkoutsByStatus(is_active));
        if (!workouts.isEmpty())
            return new ResponseEntity(workouts, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}