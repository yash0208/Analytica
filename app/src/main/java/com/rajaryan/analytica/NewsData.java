package com.rajaryan.analytica;

public class NewsData {
    String Title,Id,News,Src,Link,Image,author,summary,time;

    public NewsData() {
    }

    public NewsData(String title, String id, String news, String src, String link, String image, String author, String summary, String time) {
        Title = title;
        Id = id;
        News = news;
        Src = src;
        Link = link;
        Image = image;
        this.author = author;
        this.summary = summary;
        this.time = time;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNews() {
        return News;
    }

    public void setNews(String news) {
        News = news;
    }

    public String getSrc() {
        return Src;
    }

    public void setSrc(String src) {
        Src = src;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

