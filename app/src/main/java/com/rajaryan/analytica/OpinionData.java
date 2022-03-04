package com.rajaryan.analytica;

public class OpinionData {
    String Id,Opinion,User,Name,Image;

    public OpinionData() {
    }

    public OpinionData(String id, String opinion, String user, String name, String image) {
        Id = id;
        Opinion = opinion;
        User = user;
        Name = name;
        Image = image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOpinion() {
        return Opinion;
    }

    public void setOpinion(String opinion) {
        Opinion = opinion;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}

