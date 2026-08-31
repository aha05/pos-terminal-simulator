package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.config.AppConfig;
import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.service.SettingsService;
import com.pos_terminal_simulator.service.TerminalService;
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

    private AppConfig appConfig;


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


    public void initialize(Terminal terminal, SettingsService settingsService) {

        System.out.println(
                "Settings application initialization"
        );

        terminalIdField.setText(
                terminal.getTerminalId()
        );

        merchantIdField.setText(
                terminal.getMerchantId()
        );

        serialNumberField.setText(
                terminal.getSerialNumber()
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

        firmwareVersion.setText(terminal.getSoftwareVersion());

        networkType.setText("4GLTE");
    }


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

        String firmwareVersion =
                this.firmwareVersion
                        .getText();

        String serialNumberField = this.serialNumberField.getText();


        Terminal terminal = new Terminal.Builder()
                .terminalId(terminalId)
                .merchantId(merchantId)
                .serialNumber(serialNumberField)
                .terminalModel("POS-X100")
                .softwareVersion(firmwareVersion)
                .currency(currency)
                .status("ONLINE")
                .build();



        System.out.println(
                "Saving settings..."
        );


        appConfig = new AppConfig();
        TerminalService terminalService = new TerminalService(appConfig.getTerminalRepository());
        Terminal existingTerminal = terminalService.findFirst();
        System.out.println("existing terminal: " + existingTerminal);
        if(existingTerminal == null) {
            terminalService.saveTerminal(terminal);
        } else {
            terminal.setId(existingTerminal.getId());
            System.out.println(existingTerminal.getId());
            terminalService.updateTerminal(terminal);
        }



        System.out.println(
                "Terminal settings saved"
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


        statusLabel.setText(
                "Settings saved successfully."
        );
    }

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