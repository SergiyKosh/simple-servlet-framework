package ua.simpleservletframework.data.source;

import org.apache.commons.dbcp2.BasicDataSource;
import ua.simpleservletframework.core.util.PropertyUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl(PropertyUtil.getProperty("db.url"));
        ds.setUsername(PropertyUtil.getProperty("db.username"));
        ds.setPassword(PropertyUtil.getProperty("db.password"));
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() {
        try {
            synchronized (DataSource.class) {
                return ds.getConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSource() {
    }
}
