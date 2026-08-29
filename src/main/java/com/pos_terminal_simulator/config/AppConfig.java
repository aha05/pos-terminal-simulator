package com.pos_terminal_simulator.config;

import com.pos_terminal_simulator.entity.Terminal;

public class AppConfig {

    private final Terminal terminal;
    private final ApiConfig apiConfig;

    public AppConfig() {
        this.terminal =
                new Terminal();

        this.apiConfig =
                new ApiConfig();
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public ApiConfig getApiConfig() {
        return apiConfig;
    }
}