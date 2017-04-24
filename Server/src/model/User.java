package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Author : Andrej Ščasný
 * Timestamp : 24.04.2017
 */
public class User implements Serializable{
    private int id;
    private String email;
    private String nickname;
    private String password;
    private Timestamp registered_at;
    
    public User(int id, String email, String nickname, String password, Timestamp registered_at) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.registered_at = registered_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getRegistered_at() {
        return registered_at;
    }

    public void setRegistered_at(Timestamp registered_at) {
        this.registered_at = registered_at;
    }
}
