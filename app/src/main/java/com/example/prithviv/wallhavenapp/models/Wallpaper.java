package com.example.prithviv.wallhavenapp.models;

import java.util.List;
import java.util.Map;

public class Wallpaper {
    private String id;
    private String url;
    private String short_url;
    private Uploader uploader;
    private int views;
    private int favorites;
    private String source;
    private String purity;
    private String category;
    private int dimension_x;
    private int dimension_y;
    private String resolution;
    private String ratio;
    private int file_size;
    private String file_type;
    private String created_at;
    private List<String> colors;
    private String path;
    private Map<String, String> thumbs;
    private List<Tags> tags;

    public Wallpaper() {

    }

    public Wallpaper(String id, String url, String short_url, Uploader uploader, int views, int favorites, String source, String purity, String category,
              int dimension_x, int dimension_y, String resolution, String ratio, int file_size, String file_type, String created_at, List<String> colors,
              String path, Map<String, String> thumbs, List<Tags> tags) {

        this.id = id;
        this.url = url;
        this.short_url = short_url;
        this.uploader = uploader;
        this.views = views;
        this.favorites = favorites;
        this.source = source;
        this.purity = purity;
        this.category = category;
        this.dimension_x = dimension_x;
        this.dimension_y = dimension_y;
        this.resolution = resolution;
        this.ratio = ratio;
        this.file_size = file_size;
        this.file_type = file_type;
        this.created_at = created_at;
        this.colors = colors;
        this.path = path;
        this.thumbs = thumbs;
        this.tags = tags;
    }

    public String getID() {
        return this.id;
    }

    public String getURL() { return this.url; }

    public String getShort_url() { return this.short_url; }

    public Uploader getUploader() { return this.uploader; }

    public int getViews() { return this.views; }

    public int getFavorites() { return this.favorites; }

    public String getSource() { return this.source; }

    public String getPurity() { return this.purity; }

    public String getCategory() { return this.category; }

    public int getDimension_x() { return this.dimension_x; }

    public int getDimension_y() { return this.dimension_y; }

    public String getResolution() { return this.resolution; }

    public String getRatio() { return this.ratio; }

    public int getFile_size() { return file_size; }

    public String getFile_type() { return file_type; }

    public String getCreated_at() { return this.created_at; }

    public List<String> getColors() { return this.colors; }

    public String getPath() { return this.path; }

    public Map<String, String> getThumbs() { return thumbs; }

    public String getThumbsSmall() { return thumbs.get("small"); };

    public String getThumbsLarge() { return thumbs.get("large"); };

    public String getThumbsOriginal() { return thumbs.get("original"); };

    public List<Tags> getTags() { return this.tags; }

    /*
    GET https://wallhaven.cc/api/v1/w/94x38z
    {
      "data": {
            "id": "94x38z",
            "url": "https://wallhaven.cc/w/94x38z",
            "short_url": "http://whvn.cc/94x38z",
            "uploader": {
                  "username": "test-user",
                  "group": "User",
                  "avatar": {
                    "200px": "https://wallhaven.cc/images/user/avatar/200/11_3339efb2a813.png",
                    "128px": "https://wallhaven.cc/images/user/avatar/128/11_3339efb2a813.png",
                    "32px": "https://wallhaven.cc/images/user/avatar/32/11_3339efb2a813.png",
                    "20px": "https://wallhaven.cc/images/user/avatar/20/11_3339efb2a813.png"
                  }
            },
            "views": 12,
            "favorites": 0,
            "source": "",
            "purity": "sfw",
            "category": "anime",
            "dimension_x": 6742,
            "dimension_y": 3534,
            "resolution": "6742x3534",
            "ratio": "1.91",
            "file_size": 5070446,
            "file_type": "image/jpeg",
            "created_at": "2018-10-31 01:23:10",
            "colors": [
                  "#000000",
                  "#abbcda",
                  "#424153",
                  "#66cccc",
                  "#333399"
            ],
            "path": "https://w.wallhaven.cc/full/94/wallhaven-94x38z.jpg",
            "thumbs": {
                  "large": "https://th.wallhaven.cc/lg/94/94x38z.jpg",
                  "original": "https://th.wallhaven.cc/orig/94/94x38z.jpg",
                  "small": "https://th.wallhaven.cc/small/94/94x38z.jpg"
            },
            "tags": [
                  {
                    "id": 1,
                    "name": "anime",
                    "alias": "Chinese cartoons",
                    "category_id": 1,
                    "category": "Anime & Manga",
                    "purity": "sfw",
                    "created_at": "2015-01-16 02:06:45"
                  }
            ]
          }
    }
     */
}
