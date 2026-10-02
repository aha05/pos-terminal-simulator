package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.context.AppContext;
import com.pos_terminal_simulator.service.NavigationService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class DashboardController {

    private AppContext appContext;

    private final NavigationService navigationService =
            new NavigationService();


    public void initialize(AppContext appContext) {

        this.appContext = appContext;

        // Your existing dashboard initialization here
        // Example:
        //
        // Terminal terminal = appContext.getTerminal();
        // SettingsService settingsService = appContext.getSettingsService();
        // HeartbeatScheduler scheduler =
        //         appContext.getHeartbeatScheduler();
    }


    @FXML
    private void navigateBack(ActionEvent event) {

        navigationService.navigate(
                (Node) event.getSource(),
                "/view/Main.fxml",

                controller -> {

                    MainController mainController =
                            (MainController) controller;

                    mainController.initialize(
                            appContext
                    );
                }
        );
    }
}