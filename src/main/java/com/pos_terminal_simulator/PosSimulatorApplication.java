package com.pos_terminal_simulator;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.pos_terminal_simulator.controller.PosSimulatorController;


public class PosSimulatorApplication extends Application {

    @Override
    public void start(Stage stage) {

        PosSimulatorController controller =
                new PosSimulatorController();

        Scene scene = new Scene(
                controller.createView(),
                600,
                700
        );

        scene.getStylesheets().add(
                getClass()
                        .getResource("/application.css")
                        .toExternalForm()
        );

        stage.setTitle("POS Terminal Simulator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}