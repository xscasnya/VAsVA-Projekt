package beans.room;

import beans.movie.MovieApiBean;
import beans.user.UserPersistentBeanRemote;
import com.sun.org.apache.regexp.internal.RE;
import config.DatabaseConfig;
import model.*;
import model.api.ApiMovie;
import org.postgresql.ds.PGPoolingDataSource;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dominik on 25.4.2017.
 */

@Stateless
@Remote(RoomPersistentBeanRemote.class)
public class RoomPersistentBean implements RoomPersistentBeanRemote {

    private static Logger LOG = Logger.getLogger("beans.room");

    public boolean createRoom(Room room) {
        LOG.log(Level.INFO,"Spustam vytvaranie miestnosti");
        Boolean success = true;
        PreparedStatement insertRoom = null;
        PreparedStatement insertUserRoom = null;
        Connection conn = null;


        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani DB connection",e);
            e.printStackTrace();
            return false;
        }

        int roomID = 0;

        try {
            conn.setAutoCommit(false);
            insertRoom = conn.prepareStatement("INSERT INTO room VALUES (DEFAULT,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertUserRoom = conn.prepareStatement("INSERT INTO user_in_room VALUES (?,?,?,?)");

            insertRoom.setString(1,room.getName());
            insertRoom.setString(2,room.getPassword());
            insertRoom.setInt(3,room.getType_id());
            insertRoom.setTimestamp(4,room.getCreated_at());
            insertRoom.setInt(5,room.getCreated_by());
            insertRoom.setString(6,room.getDescription());

            insertRoom.executeUpdate();

            if(insertRoom.getGeneratedKeys().next())
                roomID = insertRoom.getGeneratedKeys().getInt(1);

            insertUserRoom.setInt(1,room.getCreated_by());
            insertUserRoom.setInt(2,roomID);
            insertUserRoom.setTimestamp(3,room.getCreated_at());
            insertUserRoom.setInt(4,1);

            insertUserRoom.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri vykonavani dopytu na DB",e);
            e.printStackTrace();
            success = false;

            try {
                LOG.log(Level.INFO,"Rollbacking transaction");
                conn.rollback();
            } catch (Exception e1) {
                LOG.log(Level.SEVERE,"Chyba pri rollbacku transakcie",e1);
                e1.printStackTrace();
            }

        } finally {
            try {
                if (insertRoom != null) insertRoom.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
            }

            try {
                if(insertUserRoom != null) insertUserRoom.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani connection",e);
            }
        }


        return success;
    }

    public boolean insertUserToRoom(int userID, int roomID) {
        LOG.log(Level.INFO,"Spustam insertnutie usera");
        Boolean success = true;
        PreparedStatement insertRoom = null;
        PreparedStatement insertUserRoom = null;
        Connection conn = null;


        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani DB connection",e);
            e.printStackTrace();
            success = false;
        }
        try {
            conn.setAutoCommit(false);
            insertUserRoom = conn.prepareStatement("INSERT INTO user_in_room VALUES (?,?,?,?)");

            insertUserRoom.setInt(1, userID);
            insertUserRoom.setInt(2, roomID);
            insertUserRoom.setTimestamp(3, new Timestamp(Calendar.getInstance().getTime().getTime()));
            insertUserRoom.setInt(4,2);

            insertUserRoom.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri vykonavani dopytu na DB",e);
            e.printStackTrace();
            success = false;

            try {
                LOG.log(Level.INFO,"Rollbacking transaction");
                conn.rollback();
            } catch (Exception e1) {
                LOG.log(Level.SEVERE,"Chyba pri rollbacku transakcie",e1);
                e1.printStackTrace();
                success = false;
            }

        } finally {
            try {
                if (insertRoom != null) insertRoom.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani connetction",e);
                success = false;
            }

            try {
                if(insertUserRoom != null) insertUserRoom.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
                success = false;
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                LOG.log(Level.SEVERE,"Chyba pri zatvarani DB connection",e);
                success = false;
            }
        }


        return success;

    }


    public Response getUserRooms(int id) {
        LOG.log(Level.INFO,"Spustam ziskanie miestnosti pre pouzivatela");
        Response resp = new Response();

        PreparedStatement stmt = null;
        Connection conn = null;
        List<Room> rooms = null;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani DB connection",e);
            e.printStackTrace();
            resp.setCode(Response.error);
            resp.setDescription("Connection to database failed!");
            rooms = null;
        }

        String getSQL = "SELECT * from room r JOIN user_in_room ur ON r.id = ur.room_id where ur.user_id = ?";
        try {
            rooms = new ArrayList<Room>();
            stmt = conn.prepareStatement(getSQL);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Room get = processRowRoom(rs);
                rooms.add(get);
            }


        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri vykonavani dopytu na DB",e);
            e.printStackTrace();
            resp.setCode(Response.error);
            resp.setDescription("Error with database query.");
            rooms = null;

        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
            }

            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                LOG.log(Level.SEVERE,"Chyba pri zatvarani DB connection",e);
            }

            resp.setData(rooms);
            return resp;
        }
    }

    @Override
    public Response getRoomTypes() {
        Response resp = new Response();
        PreparedStatement stmt = null;
        Connection conn = null;
        List<RoomType> rooms = null;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani DB connection",e);
            e.printStackTrace();
            resp.setCode(Response.error);
            resp.setDescription("Connection to database failed!");
            rooms = null;
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


        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri vykonavani dopytu na DB",e);
            e.printStackTrace();
            resp.setCode(Response.error);
            resp.setDescription("Error with database query.");
            rooms = null;

        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
            }

            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                LOG.log(Level.SEVERE,"Chyba pri zatvarani DB connection",e);
            }

            resp.setData(rooms);
            return resp;
        }
    }

    public Response getRoom(int id)
    {
        Response resp = new Response();
        PreparedStatement stmt = null;
        Connection conn = null;
        Room room = null;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani DB connection",e);
            resp.setCode(Response.error);
            resp.setDescription("Connection to database failed!");
            e.printStackTrace();
        }

        String getSQL = "SELECT * from room WHERE id = ?";
        try {
            stmt = conn.prepareStatement(getSQL);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                room = processRowRoom(rs);
            }

        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri vykonavani dopytu na DB",e);
            e.printStackTrace();
            resp.setCode(Response.error);
            resp.setDescription("Error with database query.");
            room = null;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
                room = null;
            }

            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                LOG.log(Level.SEVERE,"Chyba pri zatvarani DB connection",e);
                room = null;
            }

        }

        resp.setData(room);
        return resp;
    }

    public boolean isUserInRoom(int userID, int roomID) {
        PreparedStatement stmt = null;
        Connection conn = null;
        Room room = null;
        boolean result = true;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani DB connection",e);
            e.printStackTrace();
        }

        String getSQL = "SELECT * from user_in_room WHERE user_id = ? AND room_id = ?";
        try {
            stmt = conn.prepareStatement(getSQL);
            stmt.setInt(1, userID);
            stmt.setInt(2, roomID);
            ResultSet rs = stmt.executeQuery();

            result = rs.next();

        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri vykonavani dopytu na DB",e);
            e.printStackTrace();
            result = true;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
                result = true;
            }

            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                LOG.log(Level.SEVERE,"Chyba pri zatvarani DB connection",e);
                result = true;
            }


        }

        return result;
    }


    public RoomType processRowRoomType(ResultSet rs) {
        try {
            return new RoomType(rs.getInt("id"),rs.getString("type"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri spracovani resultu z DB",e);
            e.printStackTrace();
            return null;
        }
    }

    public Movie processMovieRow (ResultSet rs) {
        try {
            return new Movie(rs.getString("imdbid"),rs.getString("title"),rs.getString("year"),rs.getString("director"),
                    rs.getString("length"),rs.getString("genre"),rs.getString("imdbrating"),rs.getTimestamp("added_at"),rs.getInt("added_by"));
        }catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri spracovani resultu z DB",e);
            e.printStackTrace();
            return null;
        }
    }

    public Room processRowRoom(ResultSet rs) {
        try {
            return new Room(rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getInt("type_id"), rs.getTimestamp("created_at"), rs.getInt("created_by"), rs.getString("description"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri spracovani resultu z DB",e);
            e.printStackTrace();
            return null;
        }
    }

    public int getUsersCount(int roomID) {
        LOG.log(Level.INFO,"Zacinam nacitavat pocet userov pre room: " + roomID);
        PreparedStatement stmt = null;
        Connection conn = null;
        Room room = null;
        int result = -1;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani DB connection",e);
            e.printStackTrace();
        }

        String getSQL = "SELECT room_id, count(*) FROM user_in_room " +
                        "GROUP BY room_id " +
                        "HAVING room_id = ?";
        try {
            stmt = conn.prepareStatement(getSQL);
            stmt.setInt(1, roomID);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                result = rs.getInt("count");
            }
            else{
                result = 0;
            }

        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri vykonavani dopytu na DB",e);
            e.printStackTrace();
            result = -1;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
                result = -1;
            }

            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                LOG.log(Level.SEVERE,"Chyba pri zatvarani DB connection",e);
                result = -1;
            }
        }

        LOG.log(Level.INFO,"Nacitavanie poctu userov pre room: " + roomID + " ukoncene uspesne");
        return result;
    }

    public int getFilmsCount(int roomID) {
        LOG.log(Level.INFO,"Zacinam nacitavat pocet filmov pre room: " + roomID);
        PreparedStatement stmt = null;
        Connection conn = null;
        Room room = null;
        int result = -1;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani DB connection",e);
            e.printStackTrace();
            result = -1;
        }

        String getSQL = "SELECT room_id, count(*) FROM film " +
                "GROUP BY room_id " +
                "HAVING room_id = ?";
        try {
            stmt = conn.prepareStatement(getSQL);
            stmt.setInt(1, roomID);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                result = rs.getInt("count");
            }
            else{
                result = 0;
            }



        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri vykonavani dopytu na DB",e);
            e.printStackTrace();
            result = -1;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
            }

            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                LOG.log(Level.SEVERE,"Chyba pri zatvarani DB connection",e);
            }


        }

        LOG.log(Level.INFO,"Nacitavanie filmov pre room: " + roomID + " ukoncene uspesne");
        return result;
    }

    public int getRoomsCount(int userID) {
        PreparedStatement stmt = null;
        Connection conn = null;
        Room room = null;
        int result = -1;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani DB connection",e);
            e.printStackTrace();
            result = -1;
        }

        String getSQL = "SELECT user_id, count(*) FROM user_in_room " +
                        "GROUP BY user_id " +
                        "HAVING user_id = ?";
        try {
            stmt = conn.prepareStatement(getSQL);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                result = rs.getInt("count");
            }


        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri vykonavani dopytu na DB",e);
            e.printStackTrace();
            result = -1;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                LOG.log(Level.WARNING,"Chyba pri zatvarani statementu",e);
            }

            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                LOG.log(Level.SEVERE,"Chyba pri zatvarani DB connection",e);
            }


        }

        return result;
    }

    public Response getMovies(int roomID) {
        LOG.log(Level.INFO,"Nacitavam vsetky filmy pre miestnost : " + roomID);
        Response resp = new Response();


        PreparedStatement stmt = null;
        Connection conn = null;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nadviazaní  DB connection",e);
            e.printStackTrace();
            resp.setCode(Response.error);
            resp.setDescription("Connection to database failed!");
            return resp;
        }
        List<Movie> movies = new LinkedList<>();
        String getSQL = "SELECT * FROM film WHERE room_id = ?";

        try {
            stmt = conn.prepareStatement(getSQL);
            stmt.setInt(1,roomID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movie get = processMovieRow(rs);
                movies.add(get);
            }


            resp.setData(movies);

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

    public Response addMovie (ApiMovie movie, int roomID, int userID) {
        LOG.log(Level.INFO,"Pridavam film");
        Response resp = new Response();
        if(movie == null || roomID < 1){
            resp.setCode(Response.error);
            resp.setDescription("Invalid add, missing parameters");
            LOG.log(Level.WARNING,"Pri pridani filmu chybaju parametre");
            return resp;
        }

        PreparedStatement stmt = null;
        Connection conn = null;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nadviazaní DB connection",e);
            e.printStackTrace();
            resp.setCode(Response.error);
            resp.setDescription("Connection to database failed!");
            return resp;
        }

            String insertSQL = "INSERT INTO film VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?)";
        try {
            stmt = conn.prepareStatement(insertSQL);
            stmt.setString(1,movie.getImdbID());
            stmt.setString(2,movie.getTitle());
            stmt.setString(3,movie.getYear());
            stmt.setString(4,movie.getDirector());
            stmt.setString(5,movie.getRuntime());
            stmt.setString(6,movie.getGenre());
            stmt.setString(7,movie.getImdbRating());
            stmt.setInt(8,roomID);
            stmt.setTimestamp(9,new Timestamp(Calendar.getInstance().getTime().getTime()));
            stmt.setInt(10,userID);


            stmt.executeUpdate();



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

}
