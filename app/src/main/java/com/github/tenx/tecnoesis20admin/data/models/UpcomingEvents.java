package com.github.tenx.tecnoesis20admin.data.models;

public class UpcomingEvents {

    private String moduleName;
    private String eventName;
    private String eventDate;
    private String eventTime;

    public UpcomingEvents(String moduleName, String eventName, String eventDate, String eventTime) {
        this.moduleName = moduleName;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    public UpcomingEvents() {
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}
