package ua.simpleservletframework.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class PropertyUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");

        try {
            synchronized (PropertyUtil.class) {
                if (PROPERTIES.isEmpty()) {
                    PROPERTIES.load(Optional.ofNullable(stream)
                            .orElseThrow(() -> new RuntimeException("application.properties file does not exists in your classpath"))
                    );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties getProperties() {
        return PROPERTIES;
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    private PropertyUtil() {
    }
}
