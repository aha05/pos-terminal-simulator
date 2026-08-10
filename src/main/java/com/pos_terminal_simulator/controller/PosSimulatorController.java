package com.pos_terminal_simulator.controller;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.pos_terminal_simulator.dto.HeartbeatRequest;
import com.pos_terminal_simulator.service.PosApiClient;

import java.util.concurrent.CompletableFuture;

public class PosSimulatorController {

    private final PosApiClient apiClient;

    private TextField terminalIdField;
    private TextField batteryField;
    private TextField storageField;
    private TextField networkField;
    private TextField signalField;
    private TextField ipAddressField;

    private TextArea logArea;

    public PosSimulatorController() {
        this.apiClient = new PosApiClient();
    }

    public Parent createView() {

        VBox root = new VBox(15);
        root.setPadding(new Insets(25));

        Label title = new Label(
                "POS TERMINAL SIMULATOR"
        );

        title.getStyleClass().add("title");

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(12);

        terminalIdField =
                createField("TERM-0001");

        batteryField =
                createField("82");

        storageField =
                createField("4096");

        networkField =
                createField("4G");

        signalField =
                createField("-65");

        ipAddressField =
                createField("192.168.1.20");

        addRow(
                form,
                0,
                "Terminal ID:",
                terminalIdField
        );

        addRow(
                form,
                1,
                "Battery (%):",
                batteryField
        );

        addRow(
                form,
                2,
                "Free Storage (MB):",
                storageField
        );

        addRow(
                form,
                3,
                "Network Type:",
                networkField
        );

        addRow(
                form,
                4,
                "Signal (dBm):",
                signalField
        );

        addRow(
                form,
                5,
                "IP Address:",
                ipAddressField
        );

        Button heartbeatButton =
                new Button("Send Heartbeat");

        heartbeatButton.setMaxWidth(
                Double.MAX_VALUE
        );

        heartbeatButton.setOnAction(
                event -> sendHeartbeat()
        );

        Label logLabel =
                new Label("Activity Log");

        logArea = new TextArea();

        logArea.setEditable(false);
        logArea.setPrefHeight(250);

        root.getChildren().addAll(
                title,
                form,
                heartbeatButton,
                logLabel,
                logArea
        );

        return root;
    }

    private TextField createField(
            String defaultValue
    ) {

        TextField field =
                new TextField(defaultValue);

        field.setPrefWidth(300);

        return field;
    }

    private void addRow(
            GridPane grid,
            int row,
            String label,
            Node field
    ) {

        grid.add(
                new Label(label),
                0,
                row
        );

        grid.add(
                field,
                1,
                row
        );
    }

    private void sendHeartbeat() {

        try {

            HeartbeatRequest heartbeat =
                    new HeartbeatRequest(
                            terminalIdField.getText(),
                            Integer.parseInt(
                                    batteryField.getText()
                            ),
                            Long.parseLong(
                                    storageField.getText()
                            ),
                            networkField.getText(),
                            Integer.parseInt(
                                    signalField.getText()
                            ),
                            ipAddressField.getText()
                    );

            appendLog(
                    "Sending heartbeat..."
            );

            CompletableFuture
                    .supplyAsync(() -> {

                        try {

                            return apiClient
                                    .sendHeartbeat(
                                            heartbeat
                                    );

                        } catch (Exception e) {

                            throw new RuntimeException(e);
                        }

                    })
                    .thenAccept(response ->
                            Platform.runLater(() -> {

                                appendLog(
                                        "Heartbeat successful."
                                );

                                appendLog(
                                        "Response: "
                                                + response
                                );

                            })
                    )
                    .exceptionally(error -> {

                        Platform.runLater(() ->
                                appendLog(
                                        "Heartbeat failed: "
                                                + error
                                                .getCause()
                                                .getMessage()
                                )
                        );

                        return null;
                    });

        } catch (NumberFormatException e) {

            appendLog(
                    "Invalid numeric value."
            );
        }
    }

    private void appendLog(String message) {

        logArea.appendText(
                message + "\n"
        );
    }
}