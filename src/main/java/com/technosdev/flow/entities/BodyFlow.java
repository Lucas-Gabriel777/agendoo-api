package com.technosdev.flow.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BodyFlow {
    private Data data;

    @JsonProperty("flow_token")
    private String flowToken;
    private String screen;
    private String action;
    private String version;
    private String error;

    public BodyFlow() {
    }

    public BodyFlow(Data data, String flowToken, String screen, String action, String version, String error) {
        this.data = data;
        this.flowToken = flowToken;
        this.screen = screen;
        this.action = action;
        this.version = version;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getFlowToken() {
        return flowToken;
    }

    public void setFlowToken(String flowToken) {
        this.flowToken = flowToken;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BodyFlow{" +
                "data=" + data +
                ", flowToken='" + flowToken + '\'' +
                ", screen='" + screen + '\'' +
                ", action='" + action + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
