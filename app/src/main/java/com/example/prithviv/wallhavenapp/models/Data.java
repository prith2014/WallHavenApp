package com.example.prithviv.wallhavenapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("short_url")
    @Expose
    private String shortUrl;
    @SerializedName("uploader")
    @Expose
    private Uploader uploader;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("favorites")
    @Expose
    private Integer favorites;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("purity")
    @Expose
    private String purity;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("dimension_x")
    @Expose
    private Integer dimensionX;
    @SerializedName("dimension_y")
    @Expose
    private Integer dimensionY;
    @SerializedName("resolution")
    @Expose
    private String resolution;
    @SerializedName("ratio")
    @Expose
    private String ratio;
    @SerializedName("file_size")
    @Expose
    private Integer fileSize;
    @SerializedName("file_type")
    @Expose
    private String fileType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("colors")
    @Expose
    private List<String> colors = null;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("thumbs")
    @Expose
    private Thumbs thumbs;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Uploader getUploader() {
        return uploader;
    }

    public void setUploader(Uploader uploader) {
        this.uploader = uploader;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPurity() {
        return purity;
    }

    public void setPurity(String purity) {
        this.purity = purity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDimensionX() {
        return dimensionX;
    }

    public void setDimensionX(Integer dimensionX) {
        this.dimensionX = dimensionX;
    }

    public Integer getDimensionY() {
        return dimensionY;
    }

    public void setDimensionY(Integer dimensionY) {
        this.dimensionY = dimensionY;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Thumbs getThumbs() {
        return thumbs;
    }

    public void setThumbs(Thumbs thumbs) {
        this.thumbs = thumbs;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}