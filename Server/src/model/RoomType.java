package model;

import java.io.Serializable;

/**
 * Author : Andrej Ščasný
 * Date : 26.04.2017
 */
public class RoomType implements Serializable{
    private int id;
    private String type;

    public RoomType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
