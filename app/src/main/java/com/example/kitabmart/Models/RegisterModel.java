package com.example.kitabmart.Models;

import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
