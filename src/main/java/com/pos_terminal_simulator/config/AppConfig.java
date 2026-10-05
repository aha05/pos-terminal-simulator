package com.pos_terminal_simulator.config;

import com.pos_terminal_simulator.client.SwitchClient;
import com.pos_terminal_simulator.client.iso8583.Iso8583Mapper;
import com.pos_terminal_simulator.client.iso8583.Iso8583MapperImpl;
import com.pos_terminal_simulator.client.iso8583.Iso8583TcpClient;
import com.pos_terminal_simulator.database.DatabaseManager;
import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.repository.TerminalRepository;
import com.pos_terminal_simulator.service.PaymentService;
import com.pos_terminal_simulator.service.TerminalService;

public class AppConfig {

    private final Terminal terminal;
    private final ApiConfig apiConfig;
    private final DatabaseManager databaseManager;
    private final TerminalRepository terminalRepository;
    private final TerminalService terminalService;

    private final SwitchClient switchClient;
    private final Iso8583Mapper iso8583Mapper;
    private final PaymentService paymentService;

    private final Iso8583TcpClient iso8583TcpClient;

    public AppConfig() {
        this.apiConfig = new ApiConfig();
        this.terminal = new Terminal();
        this.databaseManager = new DatabaseManager();
        this.terminalRepository = new TerminalRepository(
                databaseManager.getDataSource()
        );
        this.terminalService = new TerminalService(terminalRepository);
        this.iso8583Mapper =
                new Iso8583MapperImpl(this.getTerminalService());
        this.iso8583TcpClient = new Iso8583TcpClient(
                apiConfig.getSwitchHost(),
                apiConfig.getSwitchPort()
        );
        this.switchClient =
                new SwitchClient(
                        apiConfig.getSwitchHost(),
                        apiConfig.getSwitchPort(),
                        iso8583Mapper,
                        this.getIso8583TcpClient()
                );
        this.paymentService =
                new PaymentService(
                        switchClient
                );
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public TerminalRepository getTerminalRepository() {
        return terminalRepository;
    }

    public TerminalService getTerminalService() {return terminalService;}

    public Terminal getTerminal() {
        return terminal;
    }

    public ApiConfig getApiConfig() {
        return apiConfig;
    }

    public Iso8583Mapper getIso8583Mapper() {
        return iso8583Mapper;
    }

    public SwitchClient getSwitchClient() {
        return switchClient;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public Iso8583TcpClient getIso8583TcpClient() {
        return iso8583TcpClient;
    }
}