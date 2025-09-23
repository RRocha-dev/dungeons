package br.com.rick.dungeon.jdbc;

import br.com.rick.dungeon.Dungeon;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

@AllArgsConstructor
public class DatabaseManager {
    private static final Dungeon plugin = null;

    public static void executeQuery(Connection con, String sql, ResultSetHandler handler, Object... params) {
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            setParameters(pStmt, params);
            try (ResultSet rs = pStmt.executeQuery()) {
                handler.handle(rs);
            }
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Ocorreu um erro ao executar a consulta SQL!", e);
        }
    }

    public static void execute(Connection con, String sql, Object... params) {
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            setParameters(pStmt, params);
            pStmt.execute();
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Ocorreu um erro ao executar a query!", e);
        }
    }

    private static void setParameters(PreparedStatement pStmt, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pStmt.setObject(i + 1, params[i]);
            }
        }
    }

    @FunctionalInterface
    public interface ResultSetHandler {
        void handle(ResultSet rs) throws SQLException;
    }
}