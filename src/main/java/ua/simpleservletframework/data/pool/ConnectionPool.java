package ua.simpleservletframework.data.pool;

import ua.simpleservletframework.data.connection.DatabaseConnection;
import ua.simpleservletframework.data.source.DataSource;

import java.sql.Connection;

public class ConnectionPool implements DatabaseConnection {
    @Override
    public Connection getConnection() {
        return DataSource.getConnection();
    }
}
