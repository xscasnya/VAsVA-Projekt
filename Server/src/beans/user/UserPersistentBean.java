package beans.user;

import config.DatabaseConfig;
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

    public User getAuthentication(String nickname, String password) {
        if(nickname.equals("") || password.equals("")){
            return null;
        }

        PreparedStatement stmt = null;
        Connection conn = null;
        User user = null;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        String getSQL = "SELECT * FROM users WHERE nickname = ? AND password = ?";
        try {
            stmt = conn.prepareStatement(getSQL);
            stmt.setString(1,nickname);
            stmt.setString(2,password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = processRow(rs);
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

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

        return user;

    }


    public User processRow(ResultSet rs) {
        try {
            return new User(rs.getInt("id"), rs.getString("email"), rs.getString("nickname"), rs.getString("password"), rs.getTimestamp("registered_at"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }





}
