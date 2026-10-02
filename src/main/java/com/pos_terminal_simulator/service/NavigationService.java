package com.pos_terminal_simulator.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Stack;
import java.util.function.Consumer;

public class NavigationService {

    private final Stack<Scene> sceneHistory = new Stack<>();


    /**
     * Navigate to a new page.
     */
    public void navigate(
            Node source,
            String resource,
            Consumer<Object> initializer
    ) {

        try {

            Stage stage = (Stage) source.getScene().getWindow();

            // Keep the current scene
            Scene currentScene = stage.getScene();

            if (currentScene != null) {
                sceneHistory.push(currentScene);
            }

            // Load new FXML
            URL url = getClass().getResource(resource);

            if (url == null) {
                throw new IllegalStateException(
                        "FXML NOT FOUND: " + resource
                );
            }

            FXMLLoader loader = new FXMLLoader(url);

            Parent root = loader.load();

            Object controller = loader.getController();

            if (initializer != null) {

                if (controller == null) {
                    throw new IllegalStateException(
                            "FXML loaded but controller is null: "
                                    + resource
                    );
                }

                initializer.accept(controller);
            }

            // Create new scene
            Scene newScene = new Scene(root);

            // Replace current scene
            stage.setScene(newScene);

            stage.show();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to navigate to: " + resource,
                    e
            );
        }
    }


    /**
     * Navigate without controller initialization.
     */
    public void navigate(
            Node source,
            String resource
    ) {
        navigate(source, resource, null);
    }


    /**
     * Go back to the previous scene.
     */
    public void back(Node source) {

        if (sceneHistory.isEmpty()) {
            return;
        }

        Stage stage = (Stage) source.getScene().getWindow();

        Scene previousScene = sceneHistory.pop();

        stage.setScene(previousScene);

        stage.show();
    }
}