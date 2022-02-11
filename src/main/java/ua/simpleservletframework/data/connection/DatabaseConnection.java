package ua.simpleservletframework.data.connection;

import java.sql.Connection;

public interface DatabaseConnection {
    Connection getConnection();
}
