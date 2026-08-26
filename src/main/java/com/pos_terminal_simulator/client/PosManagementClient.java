package com.pos_terminal_simulator.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos_terminal_simulator.config.ApiConfig;
import com.pos_terminal_simulator.dto.HeartbeatRequest;
import com.pos_terminal_simulator.dto.HeartbeatResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PosManagementClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ApiConfig apiConfig;

    public PosManagementClient(
            HttpClient httpClient,
            ObjectMapper objectMapper,
            ApiConfig apiConfig
    ) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.apiConfig = apiConfig;
    }

    public HeartbeatResponse sendHeartbeat(
            HeartbeatRequest request
    ) throws Exception {

        String body =
                objectMapper.writeValueAsString(request);

        HttpRequest httpRequest =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        apiConfig
                                                .getPosManagementBaseUrl()
                                                + "/api/v1/terminals/heartbeat"
                                )
                        )
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .POST(
                                HttpRequest.BodyPublishers
                                        .ofString(body)
                        )
                        .build();

        HttpResponse<String> response =
                httpClient.send(
                        httpRequest,
                        HttpResponse.BodyHandlers
                                .ofString()
                );

        if (response.statusCode() < 200 ||
                response.statusCode() >= 300) {

            throw new RuntimeException(
                    "POS Management returned HTTP "
                            + response.statusCode()
            );
        }

        return objectMapper.readValue(
                response.body(),
                HeartbeatResponse.class
        );
    }
}