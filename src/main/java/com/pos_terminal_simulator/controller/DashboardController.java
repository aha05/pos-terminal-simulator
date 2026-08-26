package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.config.PosConfiguration;
import com.pos_terminal_simulator.entity.Settings;
import com.pos_terminal_simulator.service.SettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label terminalIdLabel;

    @FXML
    private Label merchantIdLabel;

    @FXML
    private Label posStatusLabel;

    @FXML
    private Label heartbeatStatusLabel;

    @FXML
    private Label heartbeatIntervalLabel;

    private PosConfiguration configuration;
    private SettingsService settingsService;
//    private SchedulerManager schedulerManager;

    public void initialize(
            PosConfiguration configuration,
            SettingsService settingsService
//            SchedulerManager schedulerManager
    ) {

        this.configuration =
                configuration;

        this.settingsService =
                settingsService;

//        this.schedulerManager =
//                schedulerManager;

        refresh();
    }

    private void refresh() {

        terminalIdLabel.setText(
                configuration.getTerminalId()
        );

        merchantIdLabel.setText(
                configuration.getMerchantId()
        );

        Settings settings =
                settingsService.getSettings();

//        heartbeatStatusLabel.setText(
//                schedulerManager.isHeartbeatRunning()
//                        ? "RUNNING"
//                        : "STOPPED"
//        );

//        heartbeatIntervalLabel.setText(
//                settings.getHeartbeatIntervalSeconds()
//                        + " seconds"
//        );

//        posStatusLabel.setText("ONLINE");
    }
}