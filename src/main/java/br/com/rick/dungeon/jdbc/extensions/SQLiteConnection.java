package br.com.rick.dungeon.jdbc.extensions;

import br.com.rick.dungeon.jdbc.abstractions.JDBCConnectionDriver;

public class SQLiteConnection extends JDBCConnectionDriver {
    protected SQLiteConnection(String filePath) {
        super("jdbc:sqlite:" + filePath, null, null);
    }

    @Override
    protected String getDriverClass() {
        return "org.sqlite.JDBC";
    }
}
