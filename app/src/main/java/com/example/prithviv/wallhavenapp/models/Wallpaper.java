package com.example.prithviv.wallhavenapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Wallpaper {

    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}