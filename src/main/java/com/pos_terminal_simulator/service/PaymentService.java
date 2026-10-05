package com.pos_terminal_simulator.service;

import com.pos_terminal_simulator.client.SwitchClient;
import com.pos_terminal_simulator.dto.PaymentRequest;
import com.pos_terminal_simulator.dto.PaymentResponse;

import java.io.IOException;


public class PaymentService {

    private final SwitchClient switchClient;

    public PaymentService(
            SwitchClient switchClient
    ) {
        this.switchClient = switchClient;
    }

    public String purchase(
            PaymentRequest request
    ) {
        try {
            switchClient.connect();
            String response = switchClient.sendPurchase(
                    request
            );
            switchClient.disconnect();
            return response;
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            throw new RuntimeException("failed to send message");
        }
    }
}
