package com.pos_terminal_simulator.database;

import org.hsqldb.jdbc.JDBCDataSource;

import javax.sql.DataSource;

public class DatabaseManager {

    private final DataSource dataSource;

    public DatabaseManager() {

        JDBCDataSource dataSource = new JDBCDataSource();

        dataSource.setUrl(
                "jdbc:hsqldb:file:./data/pos-simulator"
        );

        dataSource.setUser("SA");
        dataSource.setPassword("");

        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}