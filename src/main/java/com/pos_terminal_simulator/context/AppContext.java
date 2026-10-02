package com.pos_terminal_simulator.context;

import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.scheduler.HeartbeatScheduler;
import com.pos_terminal_simulator.service.HeartbeatService;
import com.pos_terminal_simulator.service.PaymentService;
import com.pos_terminal_simulator.service.SettingsService;
import com.pos_terminal_simulator.service.TerminalService;

public class AppContext {

    private final Terminal terminal;
    private final HeartbeatService heartbeatService;
    private final HeartbeatScheduler heartbeatScheduler;
    private final SettingsService settingsService;
    private final TerminalService terminalService;
    private final PaymentService paymentService;

    public AppContext(
            Terminal terminal,
            HeartbeatService heartbeatService,
            HeartbeatScheduler heartbeatScheduler,
            SettingsService settingsService,
            TerminalService terminalService,
            PaymentService paymentService
    ) {
        this.terminal = terminal;
        this.heartbeatService = heartbeatService;
        this.heartbeatScheduler = heartbeatScheduler;
        this.settingsService = settingsService;
        this.terminalService = terminalService;
        this.paymentService = paymentService;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public HeartbeatService getHeartbeatService() {
        return heartbeatService;
    }

    public HeartbeatScheduler getHeartbeatScheduler() {
        return heartbeatScheduler;
    }

    public SettingsService getSettingsService() {
        return settingsService;
    }

    public TerminalService getTerminalService() {
        return terminalService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }
}