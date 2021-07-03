package com.alma.potatoHouse.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "workouts")
@EntityListeners(AuditingEntityListener.class)
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title",unique = true, nullable = false)
    private String title;

    @Column(name = "max_trainers", nullable = false)
    private int max_trainers;

    @Column(name = "min_age", nullable = false)
    private int min_age;

    @Column(name = "length_min", nullable = false)
    private int length_min;

    @Column(name = "difficulty", nullable = false)
    private String difficulty;

    @Column(name = "many_times", nullable = false)
    private int many_times;

    @Column(name = "is_active", nullable = false)
    private Boolean is_active;

    @Column(name = "added_date", nullable = false)
    private LocalDateTime added_date;

    @PrePersist
    public void prePersist() {
        added_date = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        added_date = LocalDateTime.now();
    }

    public Workout(){}

    public int getId() {
        return id;
    }

    public int getMax_trainers() {
        return max_trainers;
    }

    public int getMin_age() {
        return min_age;
    }

    public int getLength_min() {
        return length_min;
    }

    public int getMany_times() {
        return many_times;
    }

    public String getTitle() {
        return title;
    }

    public Boolean isActive() {
        return is_active;
    }

    public LocalDateTime getAdded_date() {
        return added_date;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMax_trainers(int max_trainers) {
        this.max_trainers = max_trainers;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public void setLength_min(int length_min) {
        this.length_min = length_min;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setMany_times(int many_times) {
        this.many_times = many_times;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public void setAdded_date(LocalDateTime added_date) {
        this.added_date = added_date;
    }

    public List<String> updateWorkout(Workout workout_new){
        List<String> changes=new ArrayList<>();
        if(is_active != workout_new.is_active){
            is_active=workout_new.is_active;
            addChange(changes,"ACTIVE",String.valueOf(is_active),String.valueOf(workout_new.is_active));
        }
        if(!difficulty.equals(workout_new.difficulty)){
            addChange(changes,"DIFFICULTY",difficulty,workout_new.difficulty);
            difficulty= workout_new.difficulty;
        }
        if(max_trainers != workout_new.max_trainers){
            addChange(changes,"MAXIMAL TRAINERS",String.valueOf(max_trainers),String.valueOf(workout_new.max_trainers));
            max_trainers=workout_new.max_trainers;
        }
        if(min_age != workout_new.min_age){
            addChange(changes,"MINIMAL AGE",String.valueOf(min_age),String.valueOf(workout_new.min_age));
            min_age= workout_new.min_age;
        }
        if(length_min != workout_new.length_min){
            addChange(changes,"LENGTH IN MINUTES",String.valueOf(length_min),String.valueOf(workout_new.length_min));
            length_min=workout_new.length_min;
        }
        this.title=workout_new.title;
        return changes;
    }

    private void addChange(List<String> changes, String title, String was, String is){
        changes.add(String.format("%s: WAS %s, NOW %s",title,was,is));
    }

    public String toString(){
        return String.format("%s/t %d/t %d/t %d/t %d/t %s/t",is_active,many_times,max_trainers,min_age,length_min,difficulty);
    }
}