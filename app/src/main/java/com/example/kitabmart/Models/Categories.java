package com.example.kitabmart.Models;

import com.google.gson.annotations.SerializedName;

public class Categories {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("ImageView")
    String ImageView;

    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }
    public String getImageView() {

        return ImageView;
    }
}
