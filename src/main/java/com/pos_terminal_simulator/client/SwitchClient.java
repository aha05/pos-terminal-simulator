package com.pos_terminal_simulator.client;

import com.pos_terminal_simulator.client.iso8583.Iso8583Mapper;
import com.pos_terminal_simulator.dto.PaymentRequest;
import com.pos_terminal_simulator.dto.PaymentResponse;

public class SwitchClient {

    private final String host;
    private final int port;
    private Iso8583Mapper iso5883Mapper;

    public SwitchClient(
            String host,
            int port,
            Iso8583Mapper iso5883Mapper
    ) {
        this.host = host;
        this.port = port;
        this.iso5883Mapper = iso5883Mapper;
    }

    public PaymentResponse sendPurchase(
            PaymentRequest request
    ) {
        System.out.println("sending purchases...");

        System.out.println(iso5883Mapper.toIso8583Message(request));

        throw new UnsupportedOperationException(
                "Switch communication not implemented yet"
        );
    }
}