package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.scheduler.HeartbeatScheduler;
import com.pos_terminal_simulator.service.HeartbeatService;
import com.pos_terminal_simulator.service.PaymentService;
import com.pos_terminal_simulator.service.SettingsService;
import com.pos_terminal_simulator.service.TerminalService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private StackPane contentArea;


    /*
     * =========================================================
     * SERVICES
     * =========================================================
     */

    private Terminal terminal;

    private HeartbeatService heartbeatService;

    private HeartbeatScheduler heartbeatScheduler;

    private SettingsService settingsService;

    private TerminalService terminalService;

    private PaymentService paymentService;


    /*
     * =========================================================
     * INITIALIZATION
     * =========================================================
     */

    public void initialize(
            Terminal terminal,
            HeartbeatService heartbeatService,
            HeartbeatScheduler heartbeatScheduler,
            SettingsService settingsService,
            TerminalService terminalService,
            PaymentService paymentService
    ) {

        this.terminal =
                terminal;

        this.heartbeatService =
                heartbeatService;

        this.heartbeatScheduler =
                heartbeatScheduler;

        this.settingsService =
                settingsService;

        this.terminalService =
                terminalService;

        this.paymentService =
                paymentService;

        showDashboard();
    }


    /*
     * =========================================================
     * DASHBOARD
     * =========================================================
     */

    @FXML
    private void showDashboard() {

        load(
                "/view/pages/Dashboard.fxml",

                controller -> {

                    DashboardController dashboardController =
                            (DashboardController) controller;

                    dashboardController.initialize(
                            terminal,
                            settingsService
                    );
                }
        );
    }


    /*
     * =========================================================
     * PAYMENT
     * =========================================================
     */

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


    /*
     * =========================================================
     * HEARTBEAT
     * =========================================================
     */


    /*
     * =========================================================
     * TERMINAL DETAILS
     * =========================================================
     */

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
                            terminalService
                    );
                }
        );
    }


    /*
     * =========================================================
     * SETTINGS
     * =========================================================
     */

    @FXML
    private void showSettings() {

        load(
                "/view/pages/Settings.fxml",

                controller -> {

                    SettingsController settingsController =
                            (SettingsController) controller;

                    settingsController.initialize(
                            terminal,
                            settingsService
                    );
                }
        );
    }


    /*
     * =========================================================
     * GENERIC FXML LOADER
     * =========================================================
     */

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


    /*
     * =========================================================
     * EXIT
     * =========================================================
     */

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


    /*
     * =========================================================
     * FUNCTIONAL INTERFACE
     * =========================================================
     */

    @FunctionalInterface
    private interface ControllerInitializer {

        void initialize(Object controller);
    }
}