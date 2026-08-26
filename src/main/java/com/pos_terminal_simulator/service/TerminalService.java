package com.pos_terminal_simulator.service;

import com.pos_terminal_simulator.config.PosConfiguration;
import com.pos_terminal_simulator.dto.TerminalDetailsResponse;

public class TerminalService {

    private final PosConfiguration configuration;

    public TerminalService(
            PosConfiguration configuration
    ) {
        this.configuration = configuration;
    }

    public TerminalDetailsResponse getTerminalDetails() {

        TerminalDetailsResponse response =
                new TerminalDetailsResponse();

        response.setTerminalId(
                configuration.getTerminalId()
        );

        response.setMerchantId(
                configuration.getMerchantId()
        );

        response.setSerialNumber(
                configuration.getSerialNumber()
        );

        response.setModel(
                configuration.getTerminalModel()
        );

        response.setSoftwareVersion(
                configuration.getSoftwareVersion()
        );

        response.setStatus("ONLINE");

        return response;
    }
}
