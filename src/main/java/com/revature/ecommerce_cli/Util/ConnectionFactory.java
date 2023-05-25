package com.revature.ecommerce_cli.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static ConnectionFactory instance;
    private Connection connection;

    private ConnectionFactory() throws IOException, SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Properties props = getProperties();
        connection = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"),
                props.getProperty("password"));
    }

    /* ------------- Methods -------------- */
    public static ConnectionFactory getInstance() throws ClassNotFoundException, IOException, SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    /* ------------- Helper Methods ----------- */

    private Properties getProperties() throws IOException {
        Properties props = new Properties();

        try (InputStream iStream = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (iStream == null) {
                throw new IOException("Unable to find db.properties");
            }
            props.load(iStream);
        }
        return props;
    }
}
