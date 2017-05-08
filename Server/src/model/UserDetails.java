package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Dominik on 8.5.2017.
 */
public class UserDetails implements Serializable{

    private int id;
    private String nickname;
    private Timestamp joined_at;
    private int addedMoviesCount;



    public UserDetails(int id, String nickname, Timestamp joined_at, int addedMoviesCount) {
        this.id = id;
        this.nickname = nickname;
        this.joined_at = joined_at;
        this.addedMoviesCount = addedMoviesCount;
    }



    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public Timestamp getJoined_at() {
        return joined_at;
    }

    public int getAddedMoviesCount() {
        return addedMoviesCount;
    }



}
