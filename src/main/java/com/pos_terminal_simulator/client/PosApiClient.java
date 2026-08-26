package com.pos_terminal_simulator.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos_terminal_simulator.config.ApiConfig;
import com.pos_terminal_simulator.dto.HeartbeatRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PosApiClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ApiConfig apiConfig;

    public PosApiClient(
            HttpClient httpClient,
            ObjectMapper objectMapper,
            ApiConfig apiConfig
    ) {

        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.apiConfig = apiConfig;
    }


    public String sendHeartbeat(
            HeartbeatRequest heartbeat
    ) throws Exception {

        String json =
                objectMapper.writeValueAsString(
                        heartbeat
                );


        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        apiConfig.getPosManagementBaseUrl()
                                                + "/terminal/health/heartbeat"
                                )
                        )
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
                "POS Management returned HTTP "
                        + response.statusCode()
                        + ": "
                        + response.body()
        );
    }
}