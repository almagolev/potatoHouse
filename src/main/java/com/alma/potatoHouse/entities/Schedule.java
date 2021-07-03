package com.alma.potatoHouse.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "schedule")
@EntityListeners({AuditingEntityListener.class})

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "w_id")
    private int w_id;

    @Column(name = "w_title",nullable = false)
    private String w_title;

    @Column(name = "w_date",nullable = false)
    private String w_date;

    @Column(name = "w_max",nullable = false)
    private int w_max;

    @Column(name = "w_signed")
    private int w_signed;

    @Column(name = "w_canceled")
    private int w_canceled;

    @Column(name = "w_done")
    private int w_done;

    @Column(name = "w_start",nullable = false)
    private String w_start;

    @Column(name = "w_end",nullable = false)
    private String w_end;

    @Column(name = "w_length",nullable = false)
    private int w_length;

    public Schedule(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getW_id() {
        return w_id;
    }

    public void setW_id(int w_id) {
        this.w_id = w_id;
    }

    public String getW_title() {
        return w_title;
    }

    public void setW_title(String w_title) {
        this.w_title = w_title;
    }

    public String getW_date() {
        return w_date;
    }

    public void setW_date(String w_date) {
        this.w_date = w_date;
    }

    public int getW_max() {
        return w_max;
    }

    public void setW_max(int w_max) {
        this.w_max = w_max;
    }

    public int getW_signed() {
        return w_signed;
    }

    public void setW_signed(int w_signed) {
        this.w_signed = w_signed;
    }

    public String getW_start() {
        return w_start;
    }

    public void setW_start(String w_start) {
        this.w_start = w_start;
    }

    public String getW_end() {
        return w_end;
    }

    public void setW_end(String w_end) {
        this.w_end = w_end;
    }

    public int getW_length() {
        return w_length;
    }

    public void setW_length(int w_length) {
        this.w_length = w_length;
    }

    public int getW_canceled() {
        return w_canceled;
    }

    public void setW_canceled(int w_canceled) {
        this.w_canceled = w_canceled;
    }

    public int getW_done() {
        return w_done;
    }

    public void setW_done(int w_done) {
        this.w_done = w_done;
    }

    public List<String> updateSchedule(Schedule schedule_new) {
        List<String> changes=new ArrayList<>();
        if(!w_start.equals(schedule_new.w_start)) {
            addChange(changes, "START TIME", w_start, schedule_new.w_start);
            w_start=schedule_new.w_start;
        }
        if(!w_end.equals(schedule_new.w_end)) {
            addChange(changes, "END TIME", w_end, schedule_new.w_end);
            w_end=schedule_new.w_end;
        }
        return changes;
    }

    private void addChange(List<String> changes, String title, String was, String is){
        changes.add(String.format("%s: WAS %s, NOW %s",title,was,is));
    }
}