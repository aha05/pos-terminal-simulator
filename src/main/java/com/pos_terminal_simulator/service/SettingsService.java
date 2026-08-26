package com.pos_terminal_simulator.service;


import com.pos_terminal_simulator.entity.Settings;

public class SettingsService {

    private final Settings settings;

    public SettingsService() {
        this.settings = new Settings();
    }

    public Settings getSettings() {
        return settings;
    }

    public void setAutoHeartbeat(
            boolean enabled
    ) {
        settings.setAutoHeartbeatEnabled(
                enabled
        );
    }

    public void setHeartbeatInterval(
            int seconds
    ) {

        if (seconds <= 0) {
            throw new IllegalArgumentException(
                    "Heartbeat interval must be greater than zero"
            );
        }

        settings.setHeartbeatIntervalSeconds(
                seconds
        );
    }
}
