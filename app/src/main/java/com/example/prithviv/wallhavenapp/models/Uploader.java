package com.example.prithviv.wallhavenapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Uploader {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("avatar")
    @Expose
    private Avatar avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

}