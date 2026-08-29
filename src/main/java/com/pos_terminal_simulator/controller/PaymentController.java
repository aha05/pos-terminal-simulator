package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.dto.PaymentRequest;
import com.pos_terminal_simulator.dto.PaymentResponse;
import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.service.PaymentService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;

public class PaymentController {

    @FXML
    private TextField amountField;

    @FXML
    private TextField panField;

    @FXML
    private ComboBox<String> currencyComboBox;

    @FXML
    private Label responseStatusLabel;

    @FXML
    private Label responseMessageLabel;

    private Terminal terminal;
    private PaymentService paymentService;

    public void initialize(
            Terminal terminal,
            PaymentService paymentService
    ) {

        this.terminal =
                terminal;

        this.paymentService =
                paymentService;

        currencyComboBox
                .getItems()
                .addAll("ETB", "USD");

        currencyComboBox.setValue("ETB");
    }

    @FXML
    private void processPayment() {

        try {

            BigDecimal amount =
                    new BigDecimal(
                            amountField.getText()
                    );

            PaymentRequest request =
                    new PaymentRequest();

            request.setTerminalId(
                    terminal.getTerminalId()
            );

            request.setMerchantId(
                    terminal.getMerchantId()
            );

            request.setAmount(amount);

            request.setCurrency(
                    currencyComboBox.getValue()
            );

            request.setPan(
                    panField.getText()
            );

            PaymentResponse response =
                    paymentService.purchase(
                            request
                    );
            responseStatusLabel.setText(response.getResponseCode());
            responseMessageLabel.setText(response.getMessage());

        } catch (Exception e) {
            responseMessageLabel.setText(
                    "ERROR: "
                            + e.getMessage()
            );
        }
    }

    @FXML
    private void clearForm() {

        System.out.println(
                "Clear payment form"
        );

        amountField.clear();

        panField.clear();
    }
}
