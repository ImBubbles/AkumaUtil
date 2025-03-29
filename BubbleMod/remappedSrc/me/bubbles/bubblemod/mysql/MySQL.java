package me.bubbles.bubblemod.mysql;

import java.sql.*;

public final class MySQL {

    private static final String url = "jdbc:mysql://99.172.162.236:3306/bubblemod?autoReconnect=true&useSSL=false";
    private static final String user = "readable";
    private static final String password = "read";

    private Connection connection;
    private boolean connected=false;

    public MySQL connect() throws SQLException {

        this.connection = DriverManager.getConnection(url, user, password);

        this.connected=true;

        return this;

    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public boolean isConnected() {
        return connected;
    }

    public User getData(String playerUUID) {

        try {

            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM users WHERE uuid=?");
            statement.setString(1,playerUUID);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
                String uuid = results.getString("uuid");
                String discord = results.getString("discord");
                int power = results.getInt("able");
                return new User(uuid,discord,power);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;

    }

    public boolean execute(String exc) {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.execute(exc);
            statement.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
