package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.context.AppContext;
import com.pos_terminal_simulator.service.NavigationService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class MainController {

    private AppContext appContext;

    /*
     * This is created when FXMLLoader creates MainController.
     *
     * It must NOT depend on your custom initialize() method.
     */
    private final NavigationService navigationService =
            new NavigationService();


    /**
     * Called manually by the Application class.
     */
    public void initialize(AppContext appContext) {
        this.appContext = appContext;
    }


    @FXML
    private void showDashboard(ActionEvent event) {

        navigationService.navigate(
                (Node) event.getSource(),
                "/view/pages/Dashboard.fxml",

                controller -> {

                    DashboardController dashboardController =
                            (DashboardController) controller;

                    dashboardController.initialize(
                            appContext
                    );
                }
        );
    }


    @FXML
    private void showPayment(ActionEvent event) {

        navigationService.navigate(
                (Node) event.getSource(),
                "/view/pages/Payment.fxml",

                controller -> {

                    PaymentController paymentController =
                            (PaymentController) controller;

                    paymentController.initialize(
                            appContext
                    );
                }
        );
    }


    @FXML
    private void showSettings(ActionEvent event) {

        navigationService.navigate(
                (Node) event.getSource(),
                "/view/pages/Settings.fxml",

                controller -> {

                    SettingsController settingsController =
                            (SettingsController) controller;

                    settingsController.initialize(
                            appContext
                    );
                }
        );
    }


    @FXML
    private void exit() {

        if (appContext != null &&
                appContext.getHeartbeatScheduler() != null) {

            appContext.getHeartbeatScheduler().shutdown();
        }

        System.out.println("Exiting POS Simulator...");

        System.exit(0);
    }
}