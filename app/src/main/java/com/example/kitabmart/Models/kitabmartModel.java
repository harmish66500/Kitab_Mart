package com.example.kitabmart.Models;

import com.google.gson.annotations.SerializedName;

public class kitabmartModel {

    @SerializedName("id")
    String id;

    @SerializedName("cat_id")
    String cat_id;

    @SerializedName("TitalName")
    String TitalName;

    @SerializedName("Type")
    String Type;

    @SerializedName("Author")
    String Author;

    @SerializedName("Price")
    String Price;

    @SerializedName("Sine")
    String Sine;

    @SerializedName("Description")
    String Description;

    @SerializedName("Photo")
    String Photo;

    @SerializedName("pdf")
    String pdf;



    public kitabmartModel(String id, String titalName, String author, String type, String sine,String photo, String price, String pdf ) {
        this.id = id;
        this.TitalName = titalName;
        this.Author = author;
        this.Type = type;
        this.Sine = sine;
        this.Photo = photo;
        this.Price = price;
        this.pdf = pdf;

    }


    public kitabmartModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getTitalName() {
        return TitalName;
    }

    public void setTitalName(String titalName) {
        this.TitalName = titalName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getSine() {
        return Sine;
    }

    public void setSine(String sine) {
        this.Sine = sine;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        this.Photo = photo;
    }
    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}