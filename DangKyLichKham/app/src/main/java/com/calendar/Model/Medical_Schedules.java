package com.calendar.Model;

/**
 * Created by nguyenhoang on 6/1/2015.
 */
public class Medical_Schedules {
    private String scheduleId;
    private String scheduleDate;
    private String scheduleNamePatient;
    private String scheduleIdDoctor;
    private String scheduleDetail;
    private String scheduleImage;
    private Boolean scheduleAcceptation;
    private String scheduleAlarm;

    public Boolean getScheduleAcceptation() {
        return scheduleAcceptation;
    }

    public void setScheduleAcceptation(Boolean scheduleAcceptation) {
        this.scheduleAcceptation = scheduleAcceptation;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleNamePatient() {
        return scheduleNamePatient;
    }

    public void setScheduleNamePatient(String scheduleNamePatient) {
        this.scheduleNamePatient = scheduleNamePatient;
    }

    public String getScheduleIdDoctor() {
        return scheduleIdDoctor;
    }

    public void setScheduleIdDoctor(String scheduleIdDoctor) {
        this.scheduleIdDoctor = scheduleIdDoctor;
    }

    public String getScheduleDetail() {
        return scheduleDetail;
    }

    public void setScheduleDetail(String scheduleDetail) {
        this.scheduleDetail = scheduleDetail;
    }

    public String getScheduleImage() {
        return scheduleImage;
    }

    public void setScheduleImage(String scheduleImage) {
        this.scheduleImage = scheduleImage;
    }

    public String getScheduleAlarm() {
        return scheduleAlarm;
    }

    public void setScheduleAlarm(String scheduleAlarm) {
        this.scheduleAlarm = scheduleAlarm;
    }
}
