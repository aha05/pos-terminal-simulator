package com.pos_terminal_simulator.service;

import com.pos_terminal_simulator.client.PosApiClient;
import com.pos_terminal_simulator.dto.HeartbeatRequest;

public class HeartbeatService {

    private final PosApiClient posApiClient;

    public HeartbeatService(
            PosApiClient posApiClient
    ) {
        this.posApiClient = posApiClient;
    }

    public String sendHeartbeat(
            HeartbeatRequest request
    ) throws Exception {

        return posApiClient.sendHeartbeat(request);
    }
}