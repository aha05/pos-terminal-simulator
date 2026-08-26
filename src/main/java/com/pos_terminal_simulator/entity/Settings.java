package com.pos_terminal_simulator.entity;

public class Settings {

    private boolean autoHeartbeatEnabled;
    private int heartbeatIntervalSeconds;

    public Settings() {
        this.autoHeartbeatEnabled = true;
        this.heartbeatIntervalSeconds = 30;
    }

    public boolean isAutoHeartbeatEnabled() {
        return autoHeartbeatEnabled;
    }

    public void setAutoHeartbeatEnabled(
            boolean autoHeartbeatEnabled
    ) {
        this.autoHeartbeatEnabled =
                autoHeartbeatEnabled;
    }

    public int getHeartbeatIntervalSeconds() {
        return heartbeatIntervalSeconds;
    }

    public void setHeartbeatIntervalSeconds(
            int heartbeatIntervalSeconds
    ) {
        this.heartbeatIntervalSeconds =
                heartbeatIntervalSeconds;
    }
}