package com.cityassistant.model;

public class TimeInfo {
    private String currentTime;
    private String timezone;
    private String utcOffset;
    private String dayOfWeek;
    
    public TimeInfo() {}
    
    // Getters and Setters
    public String getCurrentTime() { return currentTime; }
    public void setCurrentTime(String currentTime) { this.currentTime = currentTime; }
    
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    
    public String getUtcOffset() { return utcOffset; }
    public void setUtcOffset(String utcOffset) { this.utcOffset = utcOffset; }
    
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
}
