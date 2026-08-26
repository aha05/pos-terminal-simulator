package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.config.PosConfiguration;
import com.pos_terminal_simulator.service.SettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SettingsController {

    @FXML
    private CheckBox autoHeartbeatCheckBox;

    @FXML
    private Spinner<Integer> heartbeatIntervalSpinner;

    @FXML
    private TextField terminalIdField;

    @FXML
    private TextField merchantIdField;

    @FXML
    private TextField serialNumberField;

    @FXML
    private ComboBox<String> currencyComboBox;

    @FXML
    private TextField posManagementUrlField;

    @FXML
    private TextField switchHostField;

    @FXML
    private TextField switchPortField;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField firmwareVersion;

    @FXML
    private TextField networkType;


    /*
     * =========================================================
     * JAVAFX INITIALIZATION
     * =========================================================
     *
     * Called automatically after Settings.fxml is loaded.
     */


    @FXML
    public void initialize() {

        System.out.println(
                "SettingsController.initialize()"
        );


        /*
         * Currency
         */

        currencyComboBox
                .getItems()
                .addAll(
                        "ETB",
                        "USD"
                );

        currencyComboBox.setValue(
                "ETB"
        );


        /*
         * Default heartbeat settings.
         */

        autoHeartbeatCheckBox.setSelected(
                true
        );

        heartbeatIntervalSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        5,
                        3600,
                        30
                )
        );


        /*
         * Debug injection checks.
         */

        System.out.println(
                "autoHeartbeatCheckBox = "
                        + autoHeartbeatCheckBox
        );

        System.out.println(
                "heartbeatIntervalSpinner = "
                        + heartbeatIntervalSpinner
        );

        System.out.println(
                "currencyComboBox = "
                        + currencyComboBox
        );
    }


    /*
     * =========================================================
     * APPLICATION INITIALIZATION
     * =========================================================
     *
     * Called from MainController after FXMLLoader loads
     * this page.
     */
    public void initialize(
            PosConfiguration configuration,
            SettingsService settingsService
    ) {

        System.out.println(
                "Settings application initialization"
        );


        /*
         * For now use sample values.
         *
         * Later these should come from:
         *
         * PosConfiguration
         * SettingsService
         */

        terminalIdField.setText(
                "TERM0001"
        );

        merchantIdField.setText(
                "MERCHANT000001"
        );

        serialNumberField.setText(
                "SN-000001"
        );

        posManagementUrlField.setText(
                "http://localhost:8080"
        );

        switchHostField.setText(
                "127.0.0.1"
        );

        switchPortField.setText(
                "5000"
        );

        firmwareVersion.setText("1.0.0");

        networkType.setText("4GLTE");
    }


    /*
     * =========================================================
     * SEND HEARTBEAT
     * =========================================================
     */

    @FXML
    private void sendHeartbeat() {

        System.out.println(
                "Manual heartbeat requested"
        );

        statusLabel.setText(
                "Heartbeat request sent."
        );

        /*
         * TODO:
         *
         * heartbeatService.sendHeartbeat()
         */
    }


    /*
     * =========================================================
     * SAVE SETTINGS
     * =========================================================
     */

    @FXML
    private void saveSettings() {

        boolean autoHeartbeat =
                autoHeartbeatCheckBox
                        .isSelected();

        int interval =
                heartbeatIntervalSpinner
                        .getValue();

        String terminalId =
                terminalIdField
                        .getText();

        String merchantId =
                merchantIdField
                        .getText();

        String currency =
                currencyComboBox
                        .getValue();


        System.out.println(
                "Saving settings..."
        );

        System.out.println(
                "Auto heartbeat: "
                        + autoHeartbeat
        );

        System.out.println(
                "Heartbeat interval: "
                        + interval
                        + " seconds"
        );

        System.out.println(
                "Terminal ID: "
                        + terminalId
        );

        System.out.println(
                "Merchant ID: "
                        + merchantId
        );

        System.out.println(
                "Currency: "
                        + currency
        );


        /*
         * TODO:
         *
         * settingsService.save(...)
         *
         * Then:
         *
         * schedulerManager.startHeartbeat(...)
         *
         * or
         *
         * schedulerManager.stopHeartbeat()
         */

        statusLabel.setText(
                "Settings saved successfully."
        );
    }


    /*
     * =========================================================
     * RESET SETTINGS
     * =========================================================
     */

    @FXML
    private void resetSettings() {

        autoHeartbeatCheckBox.setSelected(
                true
        );

        heartbeatIntervalSpinner
                .getValueFactory()
                .setValue(30);

        currencyComboBox.setValue(
                "ETB"
        );

        statusLabel.setText(
                "Settings reset."
        );
    }
}