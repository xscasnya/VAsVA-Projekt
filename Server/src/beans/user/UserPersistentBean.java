package beans.user;

import config.DatabaseConfig;
import model.Response;
import model.User;
import model.UserDetails;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Jednoducha stateless session beana
 */
@Stateless
@Remote(UserPersistentBeanRemote.class)
public class UserPersistentBean implements UserPersistentBeanRemote {

    private static Logger LOG = Logger.getLogger("beans.user");

    public Response getAuthentication(String nickname, String password) {
        LOG.log(Level.INFO,"Zacinam overenie pouzivatela");
        Response resp = new Response();
        if(nickname.equals("") || password.equals("")){
            resp.setCode(Response.error);
            resp.setDescription("Invalid username or password");
            return resp;
        }

        PreparedStatement stmt = null;
        Connection conn = null;
        User user = null;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.log(Level.SEVERE,"Chyba pri nadviazaní DB connection",e);
            resp.setCode(Response.error);
            resp.setDescription("Connection to database failed!");
            return resp;
        }

        String getSQL = "SELECT * FROM users WHERE nickname = ? AND password = ?";
        try {
            stmt = conn.prepareStatement(getSQL);
            stmt.setString(1,nickname);
            stmt.setString(2,password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                resp = processRow(rs);
            }


        } catch (Exception e) {
            e.printStackTrace();
            LOG.log(Level.SEVERE,"Chyba pri spusteni dopytu",e);
            resp.setCode(Response.error);
            resp.setDescription("Error with database query.");

        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
            }

            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani connetction",e);
            }

        }

        return resp;
    }

    public Response getUsersInRoom(int roomID)
    {
        LOG.log(Level.INFO,"Zacinam nacitavat userov v roomke " + roomID);
        Response resp = new Response();
        List<UserDetails> usersInRoom = null;

        PreparedStatement stmt = null;
        Connection conn = null;
        User user = null;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nadviazaní DB connection",e);
            resp.setCode(Response.error);
            resp.setDescription("Connection to database failed!");
            e.printStackTrace();
            return resp;
        }

        String getSQL = "SELECT u.id, u.nickname, ur.joined_at, (SELECT count(*) FROM film f WHERE f.added_by=u.id AND f.room_id = ur.room_id GROUP BY u.id) FROM user_in_room ur\n" +
                        "JOIN users u ON ur.user_id=u.id\n" +
                        "WHERE ur.room_id = ?";
        try {
            stmt = conn.prepareStatement(getSQL);
            stmt.setInt(1, roomID);
            usersInRoom = new ArrayList<>();

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                usersInRoom.add(processRowUserDetails(rs));
            }

            resp.setData(usersInRoom);

        } catch (Exception e) {
            e.printStackTrace();
            LOG.log(Level.SEVERE,"Chyba pri spusteni dopytu",e);
            resp.setCode(Response.error);
            resp.setDescription("Error with database query.");

        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
            }

            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani connetction",e);
            }
        }

        LOG.log(Level.INFO,"Nacitavanie userov v roomke " + roomID + " bolo uspesne ukoncene");
        return resp;
    }

    public UserDetails processRowUserDetails(ResultSet rs) {
        try {
            return new UserDetails(rs.getInt("id"), rs.getString("nickname"), rs.getTimestamp("joined_at"), rs.getInt("count"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri spracovani resultu z DB",e);
            e.printStackTrace();
            return null;
        }
    }


    public Response processRow(ResultSet rs) {
        Response resp = new Response();
        try {
            resp.setData(new User(rs.getInt("id"), rs.getString("email"), rs.getString("nickname"), rs.getString("password"), rs.getTimestamp("registered_at")));
            return resp;
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri spracovani resultu z DB",e);
            e.printStackTrace();
            resp.setCode(Response.error);
            resp.setDescription("Error while proccessing row.");
            return resp;
        }
    }

}
