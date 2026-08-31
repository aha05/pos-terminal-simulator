package com.pos_terminal_simulator.repository;

import com.pos_terminal_simulator.entity.Terminal;

import javax.sql.DataSource;
import java.sql.*;

public class TerminalRepository {

    private final DataSource dataSource;

    public TerminalRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Terminal terminal) throws SQLException {

        String sql = """
                INSERT INTO terminal (
                    terminal_id,
                    merchant_id,
                    serial_number,
                    currency,
                    status
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, terminal.getTerminalId());
            statement.setString(2, terminal.getMerchantId());
            statement.setString(3, terminal.getSerialNumber());
            statement.setString(4, terminal.getCurrency());
            statement.setString(5, terminal.getStatus());

            statement.executeUpdate();
        }
    }

    public Terminal findFirst() throws SQLException {

        String sql = """
            SELECT
                id,
                terminal_id,
                merchant_id,
                serial_number,
                currency,
                status
            FROM terminal
            ORDER BY id ASC
            LIMIT 1
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return mapRow(resultSet);
            }

            return null;
        }
    }

    public void update(Terminal terminal) throws SQLException {

        String sql = """
            UPDATE terminal
            SET
                merchant_id = ?,
                serial_number = ?,
                currency = ?,
                status = ?
            WHERE id = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, terminal.getMerchantId());
            statement.setString(2, terminal.getSerialNumber());
            statement.setString(3, terminal.getCurrency());
            statement.setString(4, terminal.getStatus());
            statement.setLong(5, terminal.getId());

            statement.executeUpdate();
        }
    }

    public void deleteByTerminalId(String terminalId) throws SQLException {

        String sql = """
                DELETE FROM terminal
                WHERE terminal_id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, terminalId);

            statement.executeUpdate();
        }
    }

    private Terminal mapRow(ResultSet resultSet) throws SQLException {

        Terminal terminal = new Terminal();

        terminal.setId(resultSet.getLong("id"));
        terminal.setTerminalId(resultSet.getString("terminal_id"));
        terminal.setMerchantId(resultSet.getString("merchant_id"));
        terminal.setSerialNumber(resultSet.getString("serial_number"));
        terminal.setCurrency(resultSet.getString("currency"));
        terminal.setStatus(resultSet.getString("status"));

        return terminal;
    }
}