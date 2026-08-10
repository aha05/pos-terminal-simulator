package com.pos_terminal_simulator.dto;

public record HeartbeatRequest(
        String terminalId,
        Integer batteryLevel,
        Long freeStorage,
        String networkType,
        Integer signalStrength,
        String ipAddress
) {
}
