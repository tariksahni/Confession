package com.example.tarik.firstapplication;

/**
 * Created by tarik on 21/2/16.
 */


        import java.io.Serializable;
        import java.sql.Timestamp;

public class Confession implements Serializable {
    private String to;
    private String from;
    private String des;
    private Timestamp timestamp;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }



}
