package com.pos_terminal_simulator.dto;

import java.time.LocalDateTime;

public record HeartbeatResponse (
        Integer terminalId,
        String ipAddress,
        Integer batteryLevel,
        Long freeStorage,
        String networkType,
        Integer signalStrength,
        LocalDateTime heartbeatTime,
        String firmwareVersion

) {
}

