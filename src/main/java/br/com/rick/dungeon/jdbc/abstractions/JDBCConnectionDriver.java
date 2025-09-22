package br.com.rick.dungeon.jdbc.abstractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class JDBCConnectionDriver {
    private String url;
    private String user;
    private String password;

    protected JDBCConnectionDriver(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    protected abstract String getDriverClass();

    public Connection getConnection() throws SQLException {
        try {
            Class.forName(getDriverClass());
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver não encontrado: " + getDriverClass(), e);
        }
        if (user == null || password == null) {
            return DriverManager.getConnection(url);
        }
        return DriverManager.getConnection(url, user, password);
    }
}
