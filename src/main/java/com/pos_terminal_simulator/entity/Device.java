package com.pos_terminal_simulator.entity;

import com.pos_terminal_simulator.config.PosConfiguration;

public class Device {

    private final PosConfiguration configuration;

    private Status status;

    public Device(
            PosConfiguration configuration
    ) {
        this.configuration = configuration;
        this.status = Status.OFFLINE;
    }

    public PosConfiguration getConfiguration() {
        return configuration;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
