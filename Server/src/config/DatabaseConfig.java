package config;

/**
 * Author : Andrej Ščasný
 * Date : 23.04.2017
 */
public class DatabaseConfig {
    private String serverName;
    private String databaseName;
    private String user;
    private String password;
    private int portNumber;
    private int maxConnections;

    public DatabaseConfig(String serverName, String databaseName, String user, String password, int portNumber, int maxConnections) {
        this.serverName = serverName;
        this.databaseName = databaseName;
        this.user = user;
        this.password = password;
        this.portNumber = portNumber;
        this.maxConnections = maxConnections;
    }

    public DatabaseConfig() {

    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
