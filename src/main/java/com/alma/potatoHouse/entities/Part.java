package com.alma.potatoHouse.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

@Entity
@Table(name= "part")
@EntityListeners({AuditingEntityListener.class})
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "c_id")
    private int c_id;

    @Column(name = "w_id")
    private int w_id;

    @Column(name = "s_id")
    private int s_id;

    @Column(name = "w_title")
    private String w_title;

    @Column(name = "w_date")
    private String w_date;

    @Column(name = "w_start")
    private String w_start;

    @Column(name = "w_end")
    private String w_end;

    @Column(name = "c_came")
    private String c_came;

    @Column(name = "c_phone")
    private String c_phone;

    @Column(name = "c_gender")
    private String c_gender;

    @Column(name = "c_first")
    private String c_first;

    @Column(name = "c_last")
    private String c_last;

    public Part() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getW_id() {
        return w_id;
    }

    public void setW_id(int w_id) {
        this.w_id = w_id;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
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

    public String getC_came() {
        return c_came;
    }

    public void setC_came(String c_came) {
        this.c_came = c_came;
    }

    public String getC_first() {
        return c_first;
    }

    public void setC_first(String c_first) {
        this.c_first = c_first;
    }

    public String getC_last() {
        return c_last;
    }

    public void setC_last(String c_last) {
        this.c_last = c_last;
    }

    public String getC_phone() {
        return c_phone;
    }

    public void setC_phone(String c_phone) {
        this.c_phone = c_phone;
    }

    public String getC_gender() {
        return c_gender;
    }

    public void setC_gender(String c_gender) {
        this.c_gender = c_gender;
    }
}