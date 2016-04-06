package com.raizlabs.android.databasecomparison.events;

/**
 * Event to log data for a benchmark trial
 */
public class LogTestDataEvent {
    private long startTime;
    private long endTime;
    private String framework;
    private String eventName;

    public LogTestDataEvent(long startTime, long endTime, String framework, String eventName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.framework = framework;
        this.eventName = eventName;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getFramework() {
        return framework;
    }

    public String getEventName() {
        return eventName;
    }
}
