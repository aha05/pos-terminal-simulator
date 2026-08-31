package com.pos_terminal_simulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pos_terminal_simulator.client.PosApiClient;
import com.pos_terminal_simulator.client.PosManagementClient;
import com.pos_terminal_simulator.client.SwitchClient;
import com.pos_terminal_simulator.config.ApiConfig;
import com.pos_terminal_simulator.config.AppConfig;
import com.pos_terminal_simulator.controller.MainController;
import com.pos_terminal_simulator.database.DatabaseInitializer;
import com.pos_terminal_simulator.database.DatabaseManager;
import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.scheduler.HeartbeatScheduler;
import com.pos_terminal_simulator.service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.net.http.HttpClient;

public class PosSimulatorApplication extends Application {

    private SettingsService settingsService;

    private TerminalDetailsService terminalDetailsService;

    private PaymentService paymentService;

    private HeartbeatService heartbeatService;

    private HeartbeatScheduler heartbeatScheduler;

    private TerminalService terminalService;

    private AppConfig appConfig;


    @Override
    public void start(Stage stage) throws Exception {
        // APPLICATION CONFIGURATION
        DatabaseManager databaseManager =
                new DatabaseManager();

        DatabaseInitializer databaseInitializer =
                new DatabaseInitializer(
                        databaseManager.getDataSource()
                );

        databaseInitializer.initialize();


        AppConfig appConfig =
                new AppConfig();

        Terminal terminal =
                appConfig.getTerminal();

        terminalService = appConfig.getTerminalService();


        HttpClient httpClient =
                HttpClient.newHttpClient();

        ObjectMapper objectMapper =
                new ObjectMapper();

        objectMapper.registerModule(
                new JavaTimeModule()
        );

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

        settingsService =
                new SettingsService();


        terminalDetailsService =
                new TerminalDetailsService(
                        terminal
                );

        paymentService =
                new PaymentService(
                        switchClient
                );


        heartbeatService =
                new HeartbeatService(
                        posApiClient
                );


        heartbeatScheduler =
                new HeartbeatScheduler(
                        heartbeatService
                );

        URL fxmlUrl =
                getClass().getResource(
                        "/view/Main.fxml"
                );

        System.out.println(
                "Main.fxml URL = "
                        + fxmlUrl
        );

        if (fxmlUrl == null) {

            throw new RuntimeException(
                    "Main.fxml NOT FOUND on classpath"
            );
        }

        FXMLLoader loader =
                new FXMLLoader(fxmlUrl);

        Parent root =
                loader.load();


        MainController controller =
                loader.getController();

        if (controller == null) {

            throw new RuntimeException(
                    "MainController was not created by FXMLLoader"
            );
        }


        System.out.println(
                "MainController loaded: "
                        + controller
        );

        appConfig = new AppConfig();

        controller.initialize(

                terminal,

                heartbeatService,

                heartbeatScheduler,

                settingsService,

                terminalDetailsService,

                paymentService,

                terminalService
        );



        Scene scene =
                new Scene(
                        root,
                        1000,
                        650
                );


        stage.setTitle(
                "POS Terminal Simulator"
        );

        stage.setScene(scene);

        stage.show();

        System.out.println(
                "POS Terminal Simulator started"
        );
    }




    @Override
    public void stop() {

        if (heartbeatScheduler != null) {
            heartbeatScheduler.shutdown();
        }

        appConfig = new AppConfig();

        if (appConfig != null) {
            appConfig.getDatabaseManager().shutdown();
        }


        System.out.println(
                "POS Terminal Simulator stopped"
        );
    }


    public static void main(String[] args) {
        launch(args);
    }
}

