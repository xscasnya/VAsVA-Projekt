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
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stateless beana, ktorá sa stará o získavanie informácií o roomkach.
 * @author Dominik Števlík
 */

@Stateless
@Remote(RoomPersistentBeanRemote.class)
public class RoomPersistentBean implements RoomPersistentBeanRemote {

    private static final Logger LOG = Logger.getLogger("beans.room");

    /**
     * Vytvorí QR kód na základe roomID
     * @param roomID ID danej roomky
     * @param conn pripojenie na databázu
     * @return vráti true ak bol QR kód úspešne vygenerovaný, inak vráti false
     */
    public boolean createQrCode(int roomID, Connection conn) {
        LOG.log(Level.INFO,"Vytvaram QR code");
        byte[] array = null;

        try {
            array = QRCodeGenerator.GenerateQRCode("http://localhost:8180/ClientJSP/content/joinRoom?id=" + roomID);
        } catch (Exception e) {
            e.printStackTrace();
           LOG.log(Level.SEVERE,"Chyba pri generovani QR kodu",e);
           return false;
        }

        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement("UPDATE room SET qrcode = ? WHERE id = ?");
        } catch (Exception e) {
            e.printStackTrace();
            LOG.log(Level.SEVERE,"Chyba pri vytarani dopytu",e);
            return false;
        }

        try {
            stmt.setBytes(1, array);
            stmt.setInt(2,roomID);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.log(Level.SEVERE,"Chyba pri posielani bytov qr kodu",e);
            return false;
        }

        try {
            stmt.executeUpdate();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri vykonani updatu s qr codom",e);
            e.printStackTrace();
            return false;
        }


        return true;
    }

    /**
     * Metóda vráti QR kód ako byte[]
     * @param roomID ID danej roomky
     * @return vráti Response objekt, ktorý obsahuje dáta ako byte[]
     */
    public Response getQrCode(int roomID) {
        Response resp = new Response();

        PreparedStatement getQR = null;
        Connection conn = null;


        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri nacitani DB connection",e);
            e.printStackTrace();
            resp.setCode(Response.error);
            resp.setDescription("DB error");
        }

        String getSQL = "SELECT qrcode FROM room WHERE id = ?";

        try {
            getQR = conn.prepareStatement(getSQL);
            getQR.setInt(1,roomID);

            ResultSet rs = getQR.executeQuery();

            if(rs.next()) {
                resp.setData(rs.getBytes("qrcode"));
            }


        } catch (Exception e) {
            e.printStackTrace();
            LOG.log(Level.SEVERE,"Chyba pri spusteni dopytu",e);
            resp.setCode(Response.error);
            resp.setDescription("Error with database query.");

        } finally {
            try {
                if (getQR != null) getQR.close();
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

    /**
     * Metóda vytvorí roomku
     * @param room objekt roomky, ktorý obsahuje všetky potrebné informácie k vytvoreniu záznamu v DB
     * @return vráti true, ak bol záznam úspešne pridaný, inak false
     */
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
            insertRoom = conn.prepareStatement("INSERT INTO room VALUES (DEFAULT,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertUserRoom = conn.prepareStatement("INSERT INTO user_in_room VALUES (?,?,?,?)");

            insertRoom.setString(1,room.getName());
            insertRoom.setString(2,room.getPassword());
            insertRoom.setInt(3,room.getType_id());
            insertRoom.setTimestamp(4,room.getCreated_at());
            insertRoom.setInt(5,room.getCreated_by());
            insertRoom.setString(6,room.getDescription());
            insertRoom.setBytes(7,null);

            insertRoom.executeUpdate();

            if(insertRoom.getGeneratedKeys().next()) {
                roomID = insertRoom.getGeneratedKeys().getInt(1);
                if(!createQrCode(roomID,conn)){
                    conn.rollback();
                    return false;
                }
            }



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

    /**
     * Metóda vloží záznam do prepojovacej tabuľky
     * @param userID ID usera, ktorý je pridávaný do roomky
     * @param roomID ID roomky do ktorej je user pridaný
     * @return vráti true, ak všetko prebehlo bez chýb, inak false
     */
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


    /**
     * Metóda vráti zoznam všetkých roomok daného usera
     * @param id ID usera
     * @return vráti Response objekt, ktorý obsahuje dáta ako List<Room>
     */
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

    /**
     * Metóda vráti všetky typy roomok z číselníka
     * @return vráti Response objekt, ktorý obsahuje dáta ako List<RoomType>
     */
    @Override
    public Response getRoomTypes() {
        Response resp = new Response();
        PreparedStatement stmt = null;
        Connection conn = null;
        List<RoomType> rooms = null;

        try {
            conn = DatabaseConfig.getInstance().getSource().getConnection();
        } catch (Exception e) {
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

    /**
     * Metóda zistí roomku na základe ID a vráti ju
     * @param id ID roomky
     * @return vráti Response objekt, ktorý obsahuje dáta ako Room
     */
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

    /**
     * Metóda zistí či je daný user v danej roomke
     * @param userID ID usera
     * @param roomID ID roomky
     * @return true ak je user v roomke alebo sa vyskytne chyba, false ak nie je
     */
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

    /**
     * Vytvorí z resultsetu objekt
     * @param rs resultset z DB
     * @return Vráti RoomType alebo null, ak nastala chyba
     */
    public RoomType processRowRoomType(ResultSet rs) {
        try {
            return new RoomType(rs.getInt("id"),rs.getString("type"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri spracovani resultu z DB",e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Vytvorí z resultsetu objekt
     * @param rs resultset z DB
     * @return Vráti Movie alebo null, ak nastala chyba
     */
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

    /**
     * Vytvorí z resultsetu objekt
     * @param rs resultset z DB
     * @return Vráti Room alebo null, ak nastala chyba
     */
    public Room processRowRoom(ResultSet rs) {
        try {
            return new Room(rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getInt("type_id"), rs.getTimestamp("created_at"), rs.getInt("created_by"), rs.getString("description"), rs.getBytes("qrcode"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE,"Chyba pri spracovani resultu z DB",e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metóda zistí počet userov v roomke
     * @param roomID ID roomky
     * @return Vráti -1 pri chybe, inak počet userov
     */
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

    /**
     * Metóda zistí počet filmov v miestnosti
     * @param roomID ID roomky
     * @return Vráti -1 pri chybe, inak počet filmov
     */
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

    /**
     * Metóda zistí počet roomiek pre daného usera
     * @param userID ID usera
     * @return Vráti -1 pri chybe, inak počet filmov
     */
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

    /**
     * Zistí všetky filmy v roomke
     * @param roomID ID roomky
     * @return vráti Response objekt, ktorý obsahuje dáta ako List<Movie>
     */
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

    /**
     * Pridá film do danej roomky
     * @param movie Film zístaný z API
     * @param roomID ID roomky
     * @param userID ID usera, ktorý pridal film
     * @return
     */
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
