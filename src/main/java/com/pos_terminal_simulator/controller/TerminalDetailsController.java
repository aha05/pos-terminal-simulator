package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.dto.TerminalDetailsResponse;
import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.service.TerminalService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TerminalDetailsController {

    @FXML
    private Label terminalIdLabel;

    @FXML
    private Label merchantIdLabel;

    @FXML
    private Label serialNumberLabel;

    @FXML
    private Label battery;

//    @FXML
//    private Label versionLabel;
//
//    @FXML
//    private Label statusLabel;

    private TerminalService terminalService;
    private Terminal terminal;

    public void initialize(
            Terminal terminal,
            TerminalService terminalService
    ) {
        this.terminal =
                terminal;

        this.terminalService =
                terminalService;

        loadDetails();
    }

    private void loadDetails() {

        TerminalDetailsResponse details =
                terminalService
                        .getTerminalDetails();

        terminalIdLabel.setText(
                details.getTerminalId()
        );

        merchantIdLabel.setText(
                details.getMerchantId()
        );

        serialNumberLabel.setText(
                details.getSerialNumber()
        );

        battery.setText(
                details.getModel()
        );

//        versionLabel.setText(
//                details.getSoftwareVersion()
//        );
//
//        statusLabel.setText(
//                details.getStatus()
//        );

        battery.setText("POS-SIMULATOR");
    }
}
