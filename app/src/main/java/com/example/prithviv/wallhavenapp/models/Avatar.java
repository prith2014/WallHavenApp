package com.example.prithviv.wallhavenapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avatar {

    @SerializedName("200px")
    @Expose
    private String _200px;
    @SerializedName("128px")
    @Expose
    private String _128px;
    @SerializedName("32px")
    @Expose
    private String _32px;
    @SerializedName("20px")
    @Expose
    private String _20px;

    public String get200px() {
        return _200px;
    }

    public void set200px(String _200px) {
        this._200px = _200px;
    }

    public String get128px() {
        return _128px;
    }

    public void set128px(String _128px) {
        this._128px = _128px;
    }

    public String get32px() {
        return _32px;
    }

    public void set32px(String _32px) {
        this._32px = _32px;
    }

    public String get20px() {
        return _20px;
    }

    public void set20px(String _20px) {
        this._20px = _20px;
    }

}