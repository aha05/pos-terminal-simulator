package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.scheduler.HeartbeatScheduler;
import com.pos_terminal_simulator.service.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private StackPane contentArea;

    private Terminal terminal;

    private HeartbeatService heartbeatService;

    private HeartbeatScheduler heartbeatScheduler;

    private SettingsService settingsService;

    private TerminalDetailsService terminalDetailsService;

    private TerminalService terminalService;

    private PaymentService paymentService;


    public void initialize(
            Terminal terminal,
            HeartbeatService heartbeatService,
            HeartbeatScheduler heartbeatScheduler,
            SettingsService settingsService,
            TerminalDetailsService terminalDetailsService,
            PaymentService paymentService,
            TerminalService terminalService
    ) {

        this.terminal =
                terminal;

        this.heartbeatService =
                heartbeatService;

        this.heartbeatScheduler =
                heartbeatScheduler;

        this.settingsService =
                settingsService;

        this.terminalDetailsService =
                terminalDetailsService;

        this.paymentService =
                paymentService;

        this.terminalService = terminalService;

        showDashboard();
    }

    @FXML
    private void showDashboard() {

        load(
                "/view/pages/Dashboard.fxml",

                controller -> {

                    DashboardController dashboardController =
                            (DashboardController) controller;

                    dashboardController.initialize(
                            terminal,
                            settingsService,
                            heartbeatScheduler
                    );
                }
        );
    }


    @FXML
    private void showPayment() {

        load(
                "/view/pages/Payment.fxml",

                controller -> {

                    PaymentController paymentController =
                            (PaymentController) controller;

                    paymentController.initialize(
                            terminal,
                            paymentService
                    );
                }
        );
    }


    @FXML
    private void showTerminalDetails() {

        load(
                "/view/pages/TerminalDetails.fxml",

                controller -> {

                    TerminalDetailsController
                            terminalDetailsController =
                            (TerminalDetailsController) controller;

                    terminalDetailsController.initialize(
                            terminal,
                            terminalDetailsService
                    );
                }
        );
    }


    @FXML
    private void showSettings() {

        load(
                "/view/pages/Settings.fxml",

                controller -> {

                    SettingsController settingsController =
                            (SettingsController) controller;

                    settingsController.initialize(
                            terminalService.findFirst(),
                            settingsService
                    );
                }
        );
    }


    private void load(
            String resource,
            ControllerInitializer initializer
    ) {

        try {

            URL url =
                    getClass()
                            .getResource(resource);

            System.out.println(
                    "Loading FXML: "
                            + resource
            );

            System.out.println(
                    "Resolved URL: "
                            + url
            );

            if (url == null) {

                throw new IllegalStateException(
                        "FXML NOT FOUND: "
                                + resource
                );
            }

            FXMLLoader loader =
                    new FXMLLoader(url);

            Node view =
                    loader.load();

            Object controller =
                    loader.getController();

            System.out.println(
                    "Loaded controller: "
                            + controller
            );

            if (initializer != null) {

                if (controller == null) {

                    throw new IllegalStateException(
                            "FXML loaded but controller is null: "
                                    + resource
                    );
                }

                initializer.initialize(
                        controller
                );
            }

            contentArea
                    .getChildren()
                    .setAll(view);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load FXML: "
                            + resource,
                    e
            );
        }
    }


    @FXML
    private void exit() {

        if (heartbeatScheduler != null) {

            heartbeatScheduler.shutdown();
        }

        System.out.println(
                "Exiting POS Simulator..."
        );

        System.exit(0);
    }


    @FunctionalInterface
    private interface ControllerInitializer {

        void initialize(Object controller);
    }
}