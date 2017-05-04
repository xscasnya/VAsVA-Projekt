package config;

import org.postgresql.ds.PGPoolingDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author : Andrej Ščasný, Dominik - singleton
 * Date : 23.04.2017
 */
public class DatabaseConfig {
    private String serverName;
    private String databaseName;
    private String user;
    private String password;
    private int portNumber;
    private int maxConnections;

    private PGPoolingDataSource source;
    private StringBuilder apiUrl;
    private String apiSearch;
    private String apiTypePrefix;
    private String apiYearPrefix;
    private String apiGetByID;

    private static DatabaseConfig instance;

    private static Logger LOG = Logger.getLogger("beans.config");

    private DatabaseConfig() {
        loadProperties(); // vytvori v sebe novy objekt
        connectToDatabase();
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            // load config
            instance = new DatabaseConfig();
        }

        return instance;
    }

    public PGPoolingDataSource getSource() {
        return source;
    }

    public StringBuilder getApiUrl() {
        return apiUrl;
    }

    public String getApiSearch() {
        return apiSearch;
    }

    public String getApiTypePrefix() {
        return apiTypePrefix;
    }

    public String getApiYearPrefix() {
        return apiYearPrefix;
    }

    public String getApiGetByID() {
        return apiGetByID;
    }

    // Nacitanie properties z konfiguracneho suboru configuration.properties
    private void loadProperties() {
        LOG.log(Level.INFO,"Nacitavam properties");
        Properties config = new Properties();
        try {
            InputStream stream = (this.getClass().getClassLoader().getResourceAsStream("/config/configuration.properties"));
            config.load(stream);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani konfiguracneho suboru",e);
            e.printStackTrace();
        }

        this.serverName = (config.getProperty("serverName"));
        this.databaseName = (config.getProperty("databaseName"));
        this.password = (config.getProperty("userPassword"));
        this.user = (config.getProperty("userName"));
        this.maxConnections = Integer.parseInt(config.getProperty("maxConnections"));
        this.portNumber = Integer.parseInt(config.getProperty("portNumber"));
        this.apiUrl = new StringBuilder(config.getProperty("apiURL"));
        this.apiSearch = (config.getProperty("apiSearch"));
        this.apiTypePrefix = (config.getProperty("apiTypePrefix"));
        this.apiYearPrefix = (config.getProperty("apiYearPrefix"));
        this.apiGetByID = (config.getProperty("apiGetByID"));
    }

    private void connectToDatabase() {
        this.source = new PGPoolingDataSource();

        this.source.setServerName(this.serverName);
        this.source.setDatabaseName(this.databaseName);
        this.source.setUser(this.user);
        this.source.setPassword(this.password);
        this.source.setPortNumber(this.portNumber);
        this.source.setMaxConnections(this.maxConnections);
        this.source.setSsl(true);
        this.source.setSslfactory("org.postgresql.ssl.NonValidatingFactory");
    }


}
