package com.pos_terminal_simulator.database;

import org.hsqldb.jdbc.JDBCDataSource;
import org.hsqldb.server.Server;

import javax.sql.DataSource;

public class DatabaseManager {
    private final Server server;
    private final DataSource dataSource;

    public DatabaseManager() {

        server = new Server();

        server.setDatabaseName(0, "pos-simulator");
        server.setDatabasePath(
                0,
                "./data/pos-simulator"
        );

        server.setPort(9001);
        server.setSilent(true);
        server.setTrace(false);

        server.start();


        JDBCDataSource dataSource = new JDBCDataSource();

        dataSource.setUrl(
                "jdbc:hsqldb:hsql://localhost:9001/pos-simulator"
        );

        dataSource.setUser("SA");
        dataSource.setPassword("");

        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void shutdown() {
        server.shutdown();
    }
}