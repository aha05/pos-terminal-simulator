package com.pos_terminal_simulator.config;

public class ApiConfig {

    private String posManagementBaseUrl;
    private String switchHost;
    private int switchPort;

    public ApiConfig() {
        this.posManagementBaseUrl =
                "http://localhost:8080";

        this.switchHost = "localhost";
        this.switchPort = 9000;
    }

    public String getPosManagementBaseUrl() {
        return posManagementBaseUrl;
    }

    public void setPosManagementBaseUrl(
            String posManagementBaseUrl
    ) {
        this.posManagementBaseUrl =
                posManagementBaseUrl;
    }

    public String getSwitchHost() {
        return switchHost;
    }

    public void setSwitchHost(String switchHost) {
        this.switchHost = switchHost;
    }

    public int getSwitchPort() {
        return switchPort;
    }

    public void setSwitchPort(int switchPort) {
        this.switchPort = switchPort;
    }
}
