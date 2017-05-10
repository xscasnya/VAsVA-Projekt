package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Trieda reprezentujúca samotnú miestnosť.
 */
public class Room implements Serializable {
    private int id;
    private String name;
    private String password;
    private int type_id;
    private Timestamp created_at;
    private int created_by;
    private String description;
    private byte[] byte_image;

    public Room(int id, String name, String password, int type_id, Timestamp created_at, int created_by, String description, byte[] byte_image) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type_id = type_id;
        this.created_at = created_at;
        this.created_by = created_by;
        this.description = description;
        this.byte_image = byte_image;
    }

    // Constructor used when creating room and sending object to EJB
    public Room(String name, String password, int type_id, Timestamp created_at, int created_by, String description, byte[] byte_image) {
        this.name = name;
        this.password = password;
        this.type_id = type_id;
        this.created_at = created_at;
        this.created_by = created_by;
        this.description = description;
        this.byte_image = byte_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public byte[] getByte_image() {
        return byte_image;
    }
}
