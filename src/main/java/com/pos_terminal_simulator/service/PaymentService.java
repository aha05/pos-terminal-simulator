package com.pos_terminal_simulator.service;

import com.pos_terminal_simulator.client.SwitchClient;
import com.pos_terminal_simulator.dto.PaymentRequest;
import com.pos_terminal_simulator.dto.PaymentResponse;


public class PaymentService {

    private final SwitchClient switchClient;

    public PaymentService(
            SwitchClient switchClient
    ) {
        this.switchClient = switchClient;
    }

    public PaymentResponse purchase(
            PaymentRequest request
    ) {

        return switchClient.sendPurchase(
                request
        );
    }
}
