package beans.room;

import beans.user.UserPersistentBeanRemote;
import config.DatabaseConfig;
import model.Room;
import model.RoomType;
import org.postgresql.ds.PGPoolingDataSource;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 25.4.2017.
 */

@Stateless
@Remote(RoomPersistentBeanRemote.class)
public class RoomPersistentBean implements RoomPersistentBeanRemote {

    public List<Room> getUserRooms(int id) {
        PreparedStatement stmt = null;
        Connection conn = null;
        List<Room> rooms = null;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        String getSQL = "SELECT * from room r JOIN user_in_room ur ON r.id = ur.user_id where ur.user_id = ?";
        try {
            rooms = new ArrayList<Room>();
            stmt = conn.prepareStatement(getSQL);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Room get = processRowRoom(rs);
                rooms.add(get);
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

            return rooms;
        }
    }

    @Override
    public List<RoomType> getRoomTypes() {
        PreparedStatement stmt = null;
        Connection conn = null;
        List<RoomType> rooms = null;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        String getSQL = "SELECT * FROM room_type";
        try {
            rooms = new ArrayList<RoomType>();
            stmt = conn.prepareStatement(getSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RoomType get = processRowRoomType(rs);
                rooms.add(get);
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

            return rooms;
        }
    }


    public RoomType processRowRoomType(ResultSet rs) {
        try {
            return new RoomType(rs.getInt("id"),rs.getString("type"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Room processRowRoom(ResultSet rs) {
        try {
            return new Room(rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getInt("type_id"), rs.getTimestamp("created_at"), rs.getInt("created_by"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
