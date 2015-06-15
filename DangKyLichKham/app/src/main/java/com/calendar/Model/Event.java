package com.calendar.Model;
public class Event {

	String idEvent;
	String dateEvent;
	String contentEvent;
	String titleEvent;
	String timeEvent;
	String alarmEvent;
	
	
	public String getTitleEvent() {
		return titleEvent;
	}
	public void setTitleEvent(String titleEvent) {
		this.titleEvent = titleEvent;
	}
	public String getDateEvent() {
		return dateEvent;
	}
	public void setDateEvent(String dateEvent) {
		this.dateEvent = dateEvent;
	}
	public String getContentEvent() {
		return contentEvent;
	}
	public void setContentEvent(String contentEvent) {
		this.contentEvent = contentEvent;
	}
	public String getIdEvent() {
		return idEvent;
	}
	public void setIdEvent(String idEvent) {
		this.idEvent = idEvent;
	}
	public String getTimeEvent() {
		return timeEvent;
	}
	public void setTimeEvent(String timeEvent) {
		this.timeEvent = timeEvent;
	}
	public String getAlarmEvent() {
		return alarmEvent;
	}
	public void setAlarmEvent(String alarmEvent) {
		this.alarmEvent = alarmEvent;
	}


    public Event() {
        super();
    }
    public Event(String idEvent, String titleEvent, String contentEvent, String dateEvent, String timeEvent, String alarmEvent) {
        super();
        this.idEvent = idEvent;
        this.dateEvent = dateEvent;
        this.contentEvent = contentEvent;
        this.titleEvent = titleEvent;
        this.timeEvent = timeEvent;
        this.alarmEvent = alarmEvent;
    }
}
