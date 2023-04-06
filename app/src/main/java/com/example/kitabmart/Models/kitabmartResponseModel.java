package com.example.kitabmart.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class kitabmartResponseModel {

    @SerializedName("kitabmart")
    List<kitabmartModel> kitabmart;

    public List< kitabmartModel > getkitabmart() {
        return kitabmart;
    }

    public void setKitabmart(List< kitabmartModel > kitabmart) {
        this.kitabmart = kitabmart;
    }
}
