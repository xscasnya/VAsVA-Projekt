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
        //loadProperties(); // vytvori v sebe novy objekt
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
        InputStream inStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("configuration.properties");

        Properties config = new Properties();
        try {
            config.load(inStream);
            System.out.println("Konfiguracny subor bol nacitany");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Nepodarilo sa nacitat konfig subor");
        }

        instance.serverName = (config.getProperty("serverName"));
        instance.databaseName = (config.getProperty("databaseName"));
        instance.password = (config.getProperty("userPassword"));
        instance.user = (config.getProperty("userName"));
        instance.maxConnections = (5);
        instance.portNumber = (5432);
    }

    private void connectToDatabase() {
        //loadProperties();
        source = new PGPoolingDataSource();
       /* source.setServerName(cfg.getServerName());
        source.setDatabaseName(cfg.getDatabaseName());
        source.setUser(cfg.getUser());
        source.setPassword(cfg.getPassword());
        source.setPortNumber(cfg.getPortNumber());
        source.setInitialConnections(cfg.getMaxConnections());*/


        source.setServerName("ec2-46-137-97-169.eu-west-1.compute.amazonaws.com");
        source.setDatabaseName("d3vjs6m0fc6sfh");
        source.setUser("tcqqyxjvdsbzko");
        source.setPassword("a0be6dfc4a8755fd934cfebf7afd53ca9d7deac0f2c63f26a4eb0a80cdc36aad");
        source.setSsl(true);
        source.setSslfactory("org.postgresql.ssl.NonValidatingFactory");


    }


}
