package com.example.prithviv.wallhavenapp.models;

import java.util.HashMap;
import java.util.List;

public class Wallpaper {
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

    String id;
    String url;
    String short_url;
    Uploader uploader;
    int views;
    int favorites;
    String source;
    String purity;
    String category;
    int dimension_x;
    int dimension_y;
    String resolution;
    Float ratio;
    int file_size;
    String file_type;
    String created_at;
    List<String> colors;
    String path;
    HashMap<String, String> thumbs;
    //Tags tags;

}
