package com.pos_terminal_simulator.client.iso8583;

import com.pos_terminal_simulator.dto.PaymentRequest;
import com.pos_terminal_simulator.dto.PaymentResponse;
import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.service.TerminalService;

import java.time.LocalDateTime;

public class Iso8582MapperImpl  implements Iso8583Mapper{
    private TerminalService terminalService;

    Iso8582MapperImpl(TerminalService terminalService){
        this.terminalService = terminalService;
    }

    @Override
    public String toIso8583Message(PaymentRequest request) {
        Terminal terminal =  terminalService.findFirst();

        var message = request.getProcessingCode() +
                      request.getAmount() +
                      LocalDateTime.now().toString() +
                      "123456" +
                       LocalDateTime.now().toString() +
                       LocalDateTime.now().toString() +
                       request.getAmount() +
                       "021" +
                       "00" +
                       "123456789012" +
                        terminal.getTerminalId() +
                        terminal.getMerchantId();



        return message;
    }

    @Override
    public PaymentResponse fromIso8583Message(String iso8586) {
        return null;
    }
}
