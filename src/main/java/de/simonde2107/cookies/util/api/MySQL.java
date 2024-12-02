package de.simonde2107.cookies.util.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    String host;
    String port;
    String database;
    String username;
    String password;
    Connection connection;

    public MySQL(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Die Verbindung zur MySQL-Datenbank wurde erfolgreich hergestellt.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Fehler beim Verbinden zur MySQL-Datenbank: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (isConnected()) {
                connection.close();
                System.out.println("Die Verbindung zur MySQL-Datenbank wurde geschlossen.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String query, Object... parameters) {
        try (PreparedStatement statement = prepareStatement(query, parameters)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query, Object... parameters) {
        try {
            PreparedStatement statement = prepareStatement(query, parameters);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    PreparedStatement prepareStatement(String query, Object... parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
        return statement;
    }
}
