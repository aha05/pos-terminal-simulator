package com.pos_terminal_simulator.config;

import com.pos_terminal_simulator.database.DatabaseManager;
import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.repository.TerminalRepository;
import com.pos_terminal_simulator.service.TerminalService;

public class AppConfig {

    private final Terminal terminal;
    private final ApiConfig apiConfig;
    private final DatabaseManager databaseManager;
    private final TerminalRepository terminalRepository;
    private final TerminalService terminalService;

    public AppConfig() {
        this.terminal = new Terminal();
        this.databaseManager = new DatabaseManager();
        this.terminalRepository = new TerminalRepository(
                databaseManager.getDataSource()
        );
        this.terminalService = new TerminalService(terminalRepository);

        this.apiConfig = new ApiConfig();
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
}