package com.calendar.Model;

/**
 * Created by nguyenhoang on 5/16/2015.
 */
public class ExamSchedule {
    private String id_schedule;
    private String date;
    private String id_patient;
    private String id_doctor;
    private String title;
    private String detail;
    private String image;
    private int acceptation;
    private String alarm;

    public String getId_schedule() {
        return id_schedule;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setId_schedule(String id_schedule) {
        this.id_schedule = id_schedule;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_patient() {
        return id_patient;
    }

    public void setId_patient(String id_patient) {
        this.id_patient = id_patient;
    }

    public String getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(String id_doctor) {
        this.id_doctor = id_doctor;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAcceptation() {
        return acceptation;
    }

    public void setAcceptation(int acceptation) {
        this.acceptation = acceptation;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
}
