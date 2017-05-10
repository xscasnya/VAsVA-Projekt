package model;

import java.io.Serializable;

/**
 * Trieda ktorá reprezentuje Odpoveď zo serveru, zabalujú sa do ňej chybové hlášky, kód o úspechu, a samotné dáta
 * @author Andrej Ščasný
 */
public class Response implements Serializable {
    // -1 Error
    // 1 Success
    private int code;
    public static final int error = -1;
    public static final int success = 1;
    private String description;
    private Object data;

    public Response(int code, String description, Object data) {
        this.description = description;
        this.data = data;
        this.code = code;
    }

    public Response() {
        data = null;
        description = "";
        code = Response.success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static int getError() {
        return error;
    }

    public static int getSuccess() {
        return success;
    }
}
