package config;

import org.postgresql.ds.PGPoolingDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

    private static DatabaseConfig instance;


    private DatabaseConfig() {
        loadProperties();
        connectToDatabase();
    }

    public static DatabaseConfig getInstance() {
        if(instance == null) {
            // load config
            instance = new DatabaseConfig();
        }

        return instance;
    }

    public PGPoolingDataSource getSource() {
        return source;
    }

    // Nacitanie properties z konfiguracneho suboru configuration.properties
    private void loadProperties() {

        Properties config = new Properties();
        try {
            System.out.println("CLASS:  " + this.getClass().getResource("/config/configuration.properties"));
            InputStream stream = (this.getClass().getClassLoader().getResourceAsStream("/config/configuration.properties"));
            config.load(stream);
            System.out.println("Konfiguracny subor bol nacitany");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Nepodarilo sa nacitat konfig subor");
        }

        this.serverName = (config.getProperty("serverName"));
        this.databaseName = (config.getProperty("databaseName"));
        this.password = (config.getProperty("userPassword"));
        this.user = (config.getProperty("userName"));
        this.maxConnections = Integer.parseInt(config.getProperty("maxConnections"));
        this.portNumber = Integer.parseInt(config.getProperty("portNumber"));
    }

    private void connectToDatabase() {
        //loadProperties();
        this.source = new PGPoolingDataSource();
       /* source.setServerName(cfg.getServerName());
        source.setDatabaseName(cfg.getDatabaseName());
        source.setUser(cfg.getUser());
        source.setPassword(cfg.getPassword());
        source.setPortNumber(cfg.getPortNumber());
        source.setInitialConnections(cfg.getMaxConnections());*/


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
