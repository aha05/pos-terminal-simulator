package com.pos_terminal_simulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pos_terminal_simulator.client.PosApiClient;
import com.pos_terminal_simulator.client.PosManagementClient;
import com.pos_terminal_simulator.client.SwitchClient;
import com.pos_terminal_simulator.client.iso8583.Iso8583Mapper;
import com.pos_terminal_simulator.config.ApiConfig;
import com.pos_terminal_simulator.config.AppConfig;
import com.pos_terminal_simulator.context.AppContext;
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

    private PaymentService paymentService;

    private HeartbeatService heartbeatService;

    private HeartbeatScheduler heartbeatScheduler;

    private TerminalService terminalService;

    private AppConfig appConfig;

    private Iso8583Mapper iso8583Mapper;

    private static final double WINDOW_WIDTH = 400;
    private static final double WINDOW_HEIGHT = 600;


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
                                .getSwitchPort(),
                        iso8583Mapper
                );

        settingsService =
                new SettingsService();


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

        AppContext appContext = new AppContext(
                terminal,
                heartbeatService,
                heartbeatScheduler,
                settingsService,
                terminalService,
                paymentService
        );

        controller.initialize(appContext);


        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        stage.setScene(scene);

        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setMinWidth(WINDOW_WIDTH);
        stage.setMaxWidth(WINDOW_WIDTH);
        stage.setMinHeight(WINDOW_HEIGHT);
        stage.setMaxHeight(WINDOW_HEIGHT);

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

