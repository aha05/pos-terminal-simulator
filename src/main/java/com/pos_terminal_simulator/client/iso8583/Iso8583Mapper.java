package com.pos_terminal_simulator.client.iso8583;

import com.pos_terminal_simulator.dto.PaymentRequest;
import com.pos_terminal_simulator.dto.PaymentResponse;

public interface Iso8583Mapper {
    String toIso8583Message(PaymentRequest request);
    PaymentResponse fromIso8583Message(String iso8586);
}
