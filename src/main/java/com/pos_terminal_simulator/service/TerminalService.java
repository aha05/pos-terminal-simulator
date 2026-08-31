package com.pos_terminal_simulator.service;

import com.pos_terminal_simulator.entity.Terminal;
import com.pos_terminal_simulator.repository.TerminalRepository;

import java.sql.SQLException;

public class TerminalService {
    private final TerminalRepository terminalRepository;

    public TerminalService(TerminalRepository terminalRepository){
        this.terminalRepository = terminalRepository;
    }

    public void saveTerminal(Terminal terminal) {
        try {
            terminalRepository.save(terminal);
        } catch (SQLException e) {
            System.out.println(
                    "Failed to save terminal: " + e.getMessage()
            );
        }
    }

    public Terminal findFirst() {
        try {
           return terminalRepository.findFirst();
        } catch (SQLException e) {
            System.out.println(
                    "Terminal not configured yet: " + e.getMessage()
            );
        }

        return null;
    }

    public void updateTerminal (Terminal terminal){
        try {
            terminalRepository.update(terminal);
        } catch (SQLException e) {
            System.out.println(
                    "Terminal not configured yet: " + e.getMessage()
            );
        }
    }
}


