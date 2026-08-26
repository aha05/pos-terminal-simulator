package com.pos_terminal_simulator.config;

public class AppConfig {

    private final PosConfiguration posConfiguration;
    private final ApiConfig apiConfig;

    public AppConfig() {
        this.posConfiguration =
                new PosConfiguration();

        this.apiConfig =
                new ApiConfig();
    }

    public PosConfiguration getPosConfiguration() {
        return posConfiguration;
    }

    public ApiConfig getApiConfig() {
        return apiConfig;
    }
}