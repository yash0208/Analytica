package com.rajaryan.analytica;

public class Tracker {
    String Cost,Date,Details,Type,Image;

    public Tracker() {
    }

    public Tracker(String cost, String date, String details, String type, String image) {
        Cost = cost;
        Date = date;
        Details = details;
        Type = type;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
