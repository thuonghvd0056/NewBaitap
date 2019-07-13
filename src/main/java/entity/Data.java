package entity;

import com.googlecode.objectify.annotation.Id;

import java.util.List;

public class Data {


    private String status;
    private String message;
    private Game data;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Game getData() {
        return data;
    }

    public void setData(Game data) {
        this.data = data;
    }
}
