package com.pos_terminal_simulator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos_terminal_simulator.dto.HeartbeatRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PosApiClient {

    private static final String HEARTBEAT_URL =
            "http://localhost:8081/terminal/health/heartbeat";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public PosApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String sendHeartbeat(
            HeartbeatRequest heartbeat
    ) throws Exception {

        String json =
                objectMapper.writeValueAsString(heartbeat);

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(HEARTBEAT_URL))
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .POST(
                                HttpRequest.BodyPublishers
                                        .ofString(json)
                        )
                        .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() >= 200
                && response.statusCode() < 300) {

            return response.body();
        }

        throw new RuntimeException(
                "HTTP " + response.statusCode()
                        + ": " + response.body()
        );
    }
}