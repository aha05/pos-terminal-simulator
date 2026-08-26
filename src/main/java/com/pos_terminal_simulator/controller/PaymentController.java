package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.config.PosConfiguration;
import com.pos_terminal_simulator.dto.PaymentRequest;
import com.pos_terminal_simulator.dto.PaymentResponse;
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

    private PosConfiguration configuration;
    private PaymentService paymentService;

    public void initialize(
            PosConfiguration configuration,
            PaymentService paymentService
    ) {

        this.configuration =
                configuration;

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
                    configuration.getTerminalId()
            );

            request.setMerchantId(
                    configuration.getMerchantId()
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
