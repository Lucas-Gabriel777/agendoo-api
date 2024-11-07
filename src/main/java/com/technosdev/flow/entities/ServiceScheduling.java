package com.technosdev.flow.entities;

import java.util.Objects;

public class ServiceScheduling {
    private String id;
    private String title;
    private String metadata;

    public ServiceScheduling() {
    }

    public ServiceScheduling(String id, String title, String metadata) {
        this.id = id;
        this.title = title;
        this.metadata = metadata;
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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceScheduling that = (ServiceScheduling) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
