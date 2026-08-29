package com.pos_terminal_simulator.service;

import com.pos_terminal_simulator.dto.TerminalDetailsResponse;
import com.pos_terminal_simulator.entity.Terminal;

public class TerminalService {

    private final Terminal terminal;

    public TerminalService(
            Terminal terminal
    ) {
        this.terminal = terminal;
    }

    public TerminalDetailsResponse getTerminalDetails() {

        TerminalDetailsResponse response =
                new TerminalDetailsResponse();

        response.setTerminalId(
                terminal.getTerminalId()
        );

        response.setMerchantId(
                terminal.getMerchantId()
        );

        response.setSerialNumber(
                terminal.getSerialNumber()
        );

        response.setModel(
                terminal.getTerminalModel()
        );

        response.setSoftwareVersion(
                terminal.getSoftwareVersion()
        );

        response.setStatus("ONLINE");

        return response;
    }
}
