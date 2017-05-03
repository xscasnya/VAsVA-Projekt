package beans.user;

import config.DatabaseConfig;
import model.Response;
import model.User;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Jednoducha stateless session beana
 */
@Stateless
@Remote(UserPersistentBeanRemote.class)
public class UserPersistentBean implements UserPersistentBeanRemote {

    public Response getAuthentication(String nickname, String password) {
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
        } catch (SQLException e) {
            e.printStackTrace();
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


        } catch (SQLException e) {
            e.printStackTrace();
            resp.setCode(Response.error);
            resp.setDescription("Error with database query.");

        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }

            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
            }

        }

        return resp;
    }


    public Response processRow(ResultSet rs) {
        Response resp = new Response();
        try {
            resp.setData(new User(rs.getInt("id"), rs.getString("email"), rs.getString("nickname"), rs.getString("password"), rs.getTimestamp("registered_at")));
            return resp;
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setCode(Response.error);
            resp.setDescription("Error while proccessing row.");
            return resp;
        }
    }

}
