package com.pos_terminal_simulator.controller;

import com.pos_terminal_simulator.context.AppContext;
import com.pos_terminal_simulator.dto.PaymentRequest;
import com.pos_terminal_simulator.dto.PaymentResponse;
import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.service.NavigationService;
import com.pos_terminal_simulator.service.PaymentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentController {

    @FXML
    private TextField terminalIdField;

    @FXML
    private TextField merchantIdField;

    @FXML
    private TextField amountField;

    @FXML
    private TextField panField;

    @FXML
    private TextField processingCodeField;

    @FXML
    private TextField stanField;

    @FXML
    private ComboBox<String> currencyComboBox;

    @FXML
    private ComboBox<String> transactionTypeComboBox;

    @FXML
    private Label responseStatusLabel;

    @FXML
    private Label responseMessageLabel;

    private AppContext appContext;

    private final NavigationService navigationService =
            new NavigationService();

    private PaymentService paymentService;

    public void initialize(
            AppContext appContext
    ) {

        this.appContext = appContext;

        this.paymentService =
                appContext.getPaymentService();

        currencyComboBox
                .getItems()
                .addAll("ETB", "USD");

        stanField.setText(generateStan());

        terminalIdField.setText(appContext.getTerminal().getTerminalId());

        merchantIdField.setText(appContext.getTerminal().getMerchantId());

        transactionTypeComboBox.getItems()
                        .addAll("Purchase", "Reversal");

        transactionTypeComboBox.setValue("Purchase");

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
                    appContext.getTerminal().getTerminalId()
            );

            request.setMerchantId(
                    appContext.getTerminal().getMerchantId()
            );

            request.setAmount(amount);

            request.setCurrency(
                    currencyComboBox.getValue()
            );

            request.setProcessingCode(processingCodeField.getText());

            request.setPan(
                    panField.getText()
            );

            String response =
                    paymentService.purchase(
                            request
                    );

            responseStatusLabel.setText(response);
            responseMessageLabel.setText(response);

        } catch (Exception e) {
            responseMessageLabel.setText(
                    "ERROR: "
                            + e.getMessage()
            );
        }
    }

    private String generateStan() {return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));}

    @FXML
    private void clearForm() {

        System.out.println(
                "Clear payment form"
        );

        amountField.clear();

        panField.clear();
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
