package com.example.course2cars;

import java.sql.Date;
import java.sql.Time;

public class Assemble {
    private String car_number;
    private String detail_number;
    private Date end_date;
    private Date start_date;
    private int staff_id;
    private Time working_time;
    private int id;

    public Assemble(int id, String car_number, String detail_number, Date end_date, Date start_date, int staff_id, Time working_time) {
        this.id = id;
        this.car_number = car_number;
        this.detail_number = detail_number;
        this.end_date = end_date;
        this.start_date = start_date;
        this.staff_id = staff_id;
        this.working_time = working_time;
    }

    public Assemble() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getDetail_number() {
        return detail_number;
    }

    public void setDetail_number(String detail_number) {
        this.detail_number = detail_number;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public Time getWorking_time() {
        return working_time;
    }

    public void setWorking_time(Time working_time) {
        this.working_time = working_time;
    }
}
