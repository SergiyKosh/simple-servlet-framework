package ua.simpleservletframework.data.factory;

import ua.simpleservletframework.data.connection.DatabaseConnection;
import ua.simpleservletframework.data.pool.ConnectionPool;

public class ConnectionFactory {
    public static DatabaseConnection getInstance() {
        return new ConnectionPool();
    }
}
