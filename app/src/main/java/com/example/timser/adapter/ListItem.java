package com.example.timser.adapter;

import java.io.Serializable;

public class ListItem implements Serializable {
    private  String title;
    private  String desc;
    private int id = 0;
    private String date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
