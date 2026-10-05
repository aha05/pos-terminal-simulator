package com.pos_terminal_simulator.client;

import com.pos_terminal_simulator.client.iso8583.Iso8583Mapper;
import com.pos_terminal_simulator.client.iso8583.Iso8583TcpClient;
import com.pos_terminal_simulator.dto.PaymentRequest;
import com.pos_terminal_simulator.dto.PaymentResponse;

import java.io.IOException;

public class SwitchClient {

    private final String host;
    private final int port;
    private Iso8583Mapper iso5883Mapper;

    private final Iso8583TcpClient tcpClient;

    public SwitchClient(
            String host,
            int port,
            Iso8583Mapper iso5883Mapper,
            Iso8583TcpClient tcpClient
    ) {
        this.host = host;
        this.port = port;
        this.iso5883Mapper = iso5883Mapper;
        this.tcpClient = tcpClient;
    }

    public void connect() throws IOException { tcpClient.connect(); }

    public String sendPurchase(
            PaymentRequest request
    ) throws IOException {

        String isoMessage =
                iso5883Mapper.toIso8583Message(request);

        System.out.println("Sending ISO 8583 message:");

        System.out.println(isoMessage);

        System.out.println(tcpClient.sendAndReceive(isoMessage));

        return "sent";
    }

    public void disconnect() { tcpClient.disconnect(); } public boolean isConnected() { return tcpClient.isConnected(); }
}