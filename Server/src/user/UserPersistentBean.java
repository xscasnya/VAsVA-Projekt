package test;

import config.DatabaseConfig;
import org.postgresql.ds.PGPoolingDataSource;


import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Jednoducha stateless session beana
 */
@Stateless
@Remote(UserPersistentBeanRemote.class)
public class UserPersistentBean implements UserPersistentBeanRemote {

    private PGPoolingDataSource source;
    private DatabaseConfig cfg;

    /**
     * Biznis logika ktora sa stara o usera
     */

    public String testMe(String input) {
        return "tested " + input;
    }


    public String getUsers() {
        if (source == null) {
            connectToDatabase();
        }

        PreparedStatement stmt = null;
        Connection conn = null;


        try {
            conn = source.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return "zla connection";
        }
        String getSQL = "SELECT * FROM users";
        try {
            stmt = conn.prepareStatement(getSQL);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("email");
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return "zla query";
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
            ;
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
            }
            ;
        }

        return "failed";
    }

    public void connectToDatabase() {
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

    // Nacitanie properties z konfiguracneho suboru configuration.properties
    public void loadProperties() {

        Properties config = new Properties();
        try {

            String path = System.getProperty("user.dir");
            config.load(new FileInputStream("bin/configuration.properties"));
            System.out.println("Konfiguracny subor bol nacitany");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Nepodarilo sa nacitat konfig subor");
        }
        cfg = new DatabaseConfig();

        cfg.setServerName(config.getProperty("serverName"));
        cfg.setDatabaseName(config.getProperty("databaseName"));
        cfg.setPassword(config.getProperty("userPassword"));
        cfg.setUser(config.getProperty("userName"));
        cfg.setMaxConnections(5);
        cfg.setPortNumber(5432);
    }

}
