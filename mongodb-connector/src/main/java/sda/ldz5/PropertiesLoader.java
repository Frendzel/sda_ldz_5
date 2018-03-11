package sda.ldz5;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final String PROPERTIES_FILE = "connection.properties";

    Properties properties = new Properties();

    public PropertiesLoader() {
        InputStream res = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        try {
            properties.load(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUser() {
        return properties.getProperty("user");
    }

    public String getPass() {
        return properties.getProperty("password");
    }

    public String getDB() {
        return properties.getProperty("db");
    }

    public String getHost() {
        return properties.getProperty("host");
    }

    public Integer getPort() {
        return Integer.valueOf(properties.getProperty("port"));
    }

}
