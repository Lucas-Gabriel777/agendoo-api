package com.technosdev.flow.entities;

public class TimeScheduling {
    private String id;
    private String title;
    private boolean enabled;

    public TimeScheduling(String id, String title, boolean enabled) {
        this.id = id;
        this.title = title;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
