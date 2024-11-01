package com.example.myapplication;

public class EventModel {
    private String eventName;
    private String date;
    private String description;

    public EventModel(String eventName, String date, String description) {
        this.eventName = eventName;
        this.date = date;
        this.description = description;
    }

    public EventModel() {
    }

    // Getters and setters
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
