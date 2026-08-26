package com.pos_terminal_simulator.client;

import com.pos_terminal_simulator.dto.PaymentRequest;
import com.pos_terminal_simulator.dto.PaymentResponse;

public class SwitchClient {

    private final String host;
    private final int port;

    public SwitchClient(
            String host,
            int port
    ) {
        this.host = host;
        this.port = port;
    }

    public PaymentResponse sendPurchase(
            PaymentRequest request
    ) {

        throw new UnsupportedOperationException(
                "Switch communication not implemented yet"
        );
    }
}