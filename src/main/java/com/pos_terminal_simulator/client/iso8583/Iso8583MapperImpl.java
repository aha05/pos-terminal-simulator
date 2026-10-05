package com.pos_terminal_simulator.client.iso8583;

import com.pos_terminal_simulator.dto.PaymentRequest;
import com.pos_terminal_simulator.dto.PaymentResponse;
import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.service.TerminalService;

public class Iso8583MapperImpl implements Iso8583Mapper {

    private final TerminalService terminalService;

    private final Iso8583Builder iso8583Builder;

    public Iso8583MapperImpl(
            TerminalService terminalService
    ) {
        this.terminalService = terminalService;
        this.iso8583Builder = new Iso8583Builder();
    }

    @Override
    public String toIso8583Message(
            PaymentRequest request
    ) {

        Terminal terminal =
                terminalService.findFirst();

        Iso8583Message message =
                new Iso8583Message("0200");

        // ---------------------------------------------------------
        // DE3 - Processing Code
        // ---------------------------------------------------------

        message.setField(
                IsoField.PROCESSING_CODE,
                request.getProcessingCode()
        );

        // ---------------------------------------------------------
        // DE4 - Transaction Amount
        // ---------------------------------------------------------

        String amount =
                String.format(
                        "%012d",
                        request.getAmount()
                                .movePointRight(2)
                                .longValue()
                );

        message.setField(
                IsoField.AMOUNT_TRANSACTION,
                amount
        );

        // ---------------------------------------------------------
        // DE7 - Transmission Date/Time
        // ---------------------------------------------------------

        message.setField(
                IsoField.TRANSMISSION_DATE_TIME,
                "0818230000"
        );

        // ---------------------------------------------------------
        // DE11 - STAN
        // ---------------------------------------------------------

        message.setField(
                IsoField.SYSTEM_TRACE_AUDIT_NUMBER,
                "123456"
        );

        // ---------------------------------------------------------
        // DE12 - Local Transaction Time
        // ---------------------------------------------------------

        message.setField(
                IsoField.LOCAL_TRANSACTION_TIME,
                "230000"
        );

        // ---------------------------------------------------------
        // DE13 - Local Transaction Date
        // ---------------------------------------------------------

        message.setField(
                IsoField.LOCAL_TRANSACTION_DATE,
                "0818"
        );

        // ---------------------------------------------------------
        // DE18 - Merchant Type
        // ---------------------------------------------------------

        message.setField(
                IsoField.MERCHANT_TYPE,
                "5411"
        );

        // ---------------------------------------------------------
        // DE22 - POS Entry Mode
        // ---------------------------------------------------------

        message.setField(
                IsoField.POS_ENTRY_MODE,
                "021"
        );

        // ---------------------------------------------------------
        // DE25 - POS Condition Code
        // ---------------------------------------------------------

        message.setField(
                IsoField.POS_CONDITION_CODE,
                "00"
        );

        // ---------------------------------------------------------
        // DE37 - Retrieval Reference Number
        // ---------------------------------------------------------

        message.setField(
                IsoField.RETRIEVAL_REFERENCE_NUMBER,
                "123458789019"
        );

        // ---------------------------------------------------------
        // DE41 - Terminal ID
        // ---------------------------------------------------------

        message.setField(
                IsoField.TERMINAL_ID,
                "TERM0001"
        );

        // ---------------------------------------------------------
        // DE42 - Merchant ID
        // ---------------------------------------------------------

        message.setField(
                IsoField.MERCHANT_ID,
                "MERCHANT0000001"
        );

        // ---------------------------------------------------------
        // Build final raw ISO 8583 string
        // ---------------------------------------------------------

        return iso8583Builder.buildString(message);
    }

    @Override
    public PaymentResponse fromIso8583Message(
            String iso8586
    ) {

        return null;
    }
}
