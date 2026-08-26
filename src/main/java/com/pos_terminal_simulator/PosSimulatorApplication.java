package com.pos_terminal_simulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pos_terminal_simulator.client.PosApiClient;
import com.pos_terminal_simulator.client.PosManagementClient;
import com.pos_terminal_simulator.client.SwitchClient;
import com.pos_terminal_simulator.config.ApiConfig;
import com.pos_terminal_simulator.config.AppConfig;
import com.pos_terminal_simulator.config.PosConfiguration;
import com.pos_terminal_simulator.controller.MainController;
import com.pos_terminal_simulator.scheduler.HeartbeatScheduler;
import com.pos_terminal_simulator.service.HeartbeatService;
import com.pos_terminal_simulator.service.PaymentService;
import com.pos_terminal_simulator.service.SettingsService;
import com.pos_terminal_simulator.service.TerminalService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.net.http.HttpClient;

public class PosSimulatorApplication extends Application {

    /*
     * =========================================================
     * APPLICATION-LEVEL SERVICES
     * =========================================================
     *
     * These services are created once when the application
     * starts and shared with the controllers that need them.
     */

    private SettingsService settingsService;

    private TerminalService terminalService;

    private PaymentService paymentService;

    private HeartbeatService heartbeatService;

    private HeartbeatScheduler heartbeatScheduler;


    /*
     * =========================================================
     * START APPLICATION
     * =========================================================
     */

    @Override
    public void start(Stage stage) throws Exception {

        /*
         * =========================================================
         * 1. APPLICATION CONFIGURATION
         * =========================================================
         */

        AppConfig appConfig =
                new AppConfig();

        PosConfiguration posConfiguration =
                appConfig.getPosConfiguration();


        /*
         * =========================================================
         * 2. HTTP CLIENT
         * =========================================================
         *
         * Used for REST communication with POS Management.
         */

        HttpClient httpClient =
                HttpClient.newHttpClient();


        /*
         * =========================================================
         * 3. OBJECT MAPPER
         * =========================================================
         *
         * Jackson needs JavaTimeModule when DTOs contain:
         *
         * - LocalDateTime
         * - LocalDate
         * - LocalTime
         *
         * HeartbeatRequest contains LocalDateTime.
         */

        ObjectMapper objectMapper =
                new ObjectMapper();

        objectMapper.registerModule(
                new JavaTimeModule()
        );


        /*
         * =========================================================
         * 4. CLIENTS
         * =========================================================
         *
         * PosManagementClient
         * -------------------
         * Communicates with POS Management backend.
         *
         * SwitchClient
         * ------------
         * Communicates with payment switch using TCP.
         */

        PosManagementClient posManagementClient =
                new PosManagementClient(
                        httpClient,
                        objectMapper,
                        appConfig.getApiConfig()
                );

        ApiConfig apiConfig =
                appConfig.getApiConfig();

        PosApiClient posApiClient =
                new PosApiClient(
                        httpClient,
                        objectMapper,
                        apiConfig
                );


        SwitchClient switchClient =
                new SwitchClient(
                        appConfig
                                .getApiConfig()
                                .getSwitchHost(),

                        appConfig
                                .getApiConfig()
                                .getSwitchPort()
                );


        /*
         * =========================================================
         * 5. SERVICES
         * =========================================================
         */

        /*
         * ---------------------------------------------------------
         * Settings Service
         * ---------------------------------------------------------
         *
         * Responsible for local POS settings.
         */

        settingsService =
                new SettingsService();


        /*
         * ---------------------------------------------------------
         * Terminal Service
         * ---------------------------------------------------------
         *
         * Responsible for terminal-related operations.
         */

        terminalService =
                new TerminalService(
                        posConfiguration
                );


        /*
         * ---------------------------------------------------------
         * Payment Service
         * ---------------------------------------------------------
         *
         * Responsible for payment communication with
         * the payment switch.
         */

        paymentService =
                new PaymentService(
                        switchClient
                );


        /*
         * ---------------------------------------------------------
         * Heartbeat Service
         * ---------------------------------------------------------
         *
         * Responsible for sending heartbeat requests to
         * POS Management.
         */

        heartbeatService =
                new HeartbeatService(
                        posApiClient
                );


        /*
         * =========================================================
         * 6. HEARTBEAT SCHEDULER
         * =========================================================
         *
         * The scheduler uses HeartbeatService to send
         * automatic heartbeat requests.
         */

        heartbeatScheduler =
                new HeartbeatScheduler(
                        heartbeatService
                );


        /*
         * =========================================================
         * 7. LOAD MAIN FXML
         * =========================================================
         *
         * Main.fxml is the application shell.
         *
         * It contains:
         *
         * - Navigation menu
         * - Content area
         * - POS simulator layout
         *
         * Individual pages such as:
         *
         * - Dashboard
         * - Payment
         * - Heartbeat
         * - Terminal Details
         * - Settings
         *
         * are loaded by MainController.
         */

        URL fxmlUrl =
                getClass().getResource(
                        "/view/Main.fxml"
                );

        System.out.println(
                "Main.fxml URL = "
                        + fxmlUrl
        );


        /*
         * =========================================================
         * 8. VERIFY FXML
         * =========================================================
         */

        if (fxmlUrl == null) {

            throw new RuntimeException(
                    "Main.fxml NOT FOUND on classpath"
            );
        }


        /*
         * =========================================================
         * 9. CREATE FXML LOADER
         * =========================================================
         */

        FXMLLoader loader =
                new FXMLLoader(fxmlUrl);


        /*
         * =========================================================
         * 10. LOAD FXML
         * =========================================================
         *
         * IMPORTANT:
         *
         * The controller is created during load().
         *
         * Therefore:
         *
         * loader.getController()
         *
         * must be called AFTER loader.load().
         */

        Parent root =
                loader.load();


        /*
         * =========================================================
         * 11. GET MAIN CONTROLLER
         * =========================================================
         */

        MainController controller =
                loader.getController();


        /*
         * =========================================================
         * 12. VERIFY MAIN CONTROLLER
         * =========================================================
         */

        if (controller == null) {

            throw new RuntimeException(
                    "MainController was not created by FXMLLoader"
            );
        }


        System.out.println(
                "MainController loaded: "
                        + controller
        );


        /*
         * =========================================================
         * 13. INJECT APPLICATION SERVICES
         * =========================================================
         *
         * MainController does not create services.
         *
         * PosSimulatorApplication creates them and passes
         * them into MainController.
         */

        controller.initialize(

                posConfiguration,

                heartbeatService,

                heartbeatScheduler,

                settingsService,

                terminalService,

                paymentService
        );


        /*
         * =========================================================
         * 14. CREATE SCENE
         * =========================================================
         */

        Scene scene =
                new Scene(
                        root,
                        1200,
                        750
                );


        /*
         * =========================================================
         * 15. CONFIGURE STAGE
         * =========================================================
         */

        stage.setTitle(
                "POS Terminal Simulator"
        );

        stage.setScene(scene);

        stage.show();


        /*
         * =========================================================
         * 16. APPLICATION STARTED
         * =========================================================
         */

        System.out.println(
                "POS Terminal Simulator started"
        );
    }


    /*
     * =========================================================
     * APPLICATION SHUTDOWN
     * =========================================================
     */

    @Override
    public void stop() {

        /*
         * Stop heartbeat scheduler.
         *
         * This is important because the scheduler uses
         * its own background thread.
         */

        if (heartbeatScheduler != null) {

            heartbeatScheduler.shutdown();
        }


        System.out.println(
                "POS Terminal Simulator stopped"
        );
    }


    /*
     * =========================================================
     * MAIN
     * =========================================================
     */

    public static void main(String[] args) {

        launch(args);
    }
}

