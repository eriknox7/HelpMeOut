package com.example.helpmeoutapp;

public class Model {

    String name;
    String email;
    String mno;
    String imageurl;

    public Model() {
    }

    public Model(String name, String email, String mno, String imageurl) {
        this.name = name;
        this.email = email;
        this.mno = mno;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
