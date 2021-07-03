package com.alma.potatoHouse.entities;

import java.time.LocalDateTime;
import java.util.Date;

public class Payment {
    private int id, client_id, item_id;
    private LocalDateTime added_on;
    private Date deadline;
    private boolean payed;

    public Payment(int client_id, int item_id, Date deadline,boolean payed) {
        this.client_id = client_id;
        this.item_id = item_id;
        this.deadline = deadline;
        this.payed = payed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public LocalDateTime getAdded_on() {
        return added_on;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }
}