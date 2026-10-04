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

    private static final double WINDOW_WIDTH = 400;
    private static final double WINDOW_HEIGHT = 600;

    private final Stack<Parent> rootHistory = new Stack<>();


    /**
     * Navigate to a new page.
     *
     * The existing Scene and Stage are reused.
     * Only the Scene root is replaced.
     */
    public void navigate(
            Node source,
            String resource,
            Consumer<Object> initializer
    ) {

        try {
            Stage stage = (Stage) source.getScene().getWindow();
            Scene scene = stage.getScene();

            if (scene == null) {
                throw new IllegalStateException("Stage does not have a Scene.");
            }

            // Save the current page root
            Parent currentRoot = scene.getRoot();

            if (currentRoot != null) {
                rootHistory.push(currentRoot);
            }

            // Load the new FXML
            URL url = getClass().getResource(resource);

            if (url == null) {
                throw new IllegalStateException(
                        "FXML NOT FOUND: " + resource
                );
            }

            FXMLLoader loader = new FXMLLoader(url);

            Parent newRoot = loader.load();

            Object controller = loader.getController();

            // Initialize controller if required
            if (initializer != null) {

                if (controller == null) {
                    throw new IllegalStateException(
                            "FXML loaded but controller is null: "
                                    + resource
                    );
                }

                initializer.accept(controller);
            }

            // Replace only the Scene root
            scene.setRoot(newRoot);

            // Keep the window size fixed
            setFixedWindowSize(stage);

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
     * Go back to the previous page.
     *
     * The existing Scene and Stage are reused.
     */
    public void back(Node source) {

        if (rootHistory.isEmpty()) {
            return;
        }

        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = stage.getScene();

        if (scene == null) {
            return;
        }

        // Restore previous page root
        Parent previousRoot = rootHistory.pop();

        scene.setRoot(previousRoot);

        // Keep the window size fixed
        setFixedWindowSize(stage);

        stage.show();
    }


    /**
     * Configure the application window with a fixed size.
     */
    private void setFixedWindowSize(Stage stage) {

        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);

        stage.setMinWidth(WINDOW_WIDTH);
        stage.setMaxWidth(WINDOW_WIDTH);

        stage.setMinHeight(WINDOW_HEIGHT);
        stage.setMaxHeight(WINDOW_HEIGHT);

        stage.setResizable(false);
    }


    /**
     * Clear navigation history.
     */
    public void clearHistory() {
        rootHistory.clear();
    }
}

