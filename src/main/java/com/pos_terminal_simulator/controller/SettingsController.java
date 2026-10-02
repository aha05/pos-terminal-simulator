package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.context.AppContext;
import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.service.NavigationService;
import com.pos_terminal_simulator.service.TerminalService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    private TerminalService terminalService;

    private AppContext appContext;

    private final NavigationService navigationService =
            new NavigationService();


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


    public void initialize(AppContext appContext) {

        System.out.println(
                "Settings application initialization"
        );
        this.appContext = appContext;
        this.terminalService = appContext.getTerminalService();

        if (appContext.getTerminal() != null) {
            terminalIdField.setText(
                    appContext.getTerminal().getTerminalId()
            );

            merchantIdField.setText(
                    appContext.getTerminal().getMerchantId()
            );

            serialNumberField.setText(
                    appContext.getTerminal().getSerialNumber()
            );

            firmwareVersion.setText(appContext.getTerminal().getSoftwareVersion());
        } else {
            terminalIdField.setText(
                    "TRM00001"
            );

            merchantIdField.setText(
                    "MRC00001"
            );

            serialNumberField.setText(
                    "SN00001"
            );

            firmwareVersion.setText("1.0.0");

            posManagementUrlField.setText(
                    "http://localhost:8080"
            );

            switchHostField.setText(
                    "127.0.0.1"
            );

            switchPortField.setText(
                    "5000"
            );

            networkType.setText("4GLTE");
        }
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

    @FXML
    private void navigateBack(ActionEvent event) {

        navigationService.navigate(
                (Node) event.getSource(),
                "/view/Main.fxml",

                controller -> {

                    MainController mainController =
                            (MainController) controller;

                    mainController.initialize(
                            appContext
                    );
                }
        );
    }
}