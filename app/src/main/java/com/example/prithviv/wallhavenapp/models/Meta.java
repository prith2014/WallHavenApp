package com.example.prithviv.wallhavenapp.models;

public class Meta {
    private int current_page;
    private int last_page;
    private int per_page;
    private int total;
    private String query;
    private String seed;

    Meta(int current_page, int last_page, int per_page, int total, String query, String seed) {
        this.current_page = current_page;
        this.last_page = last_page;
        this.per_page = per_page;
        this.total = total;
        this.query = query;
        this.seed = seed;
    }

    public int getCurrentPage() { return current_page; }

    public int getLastPage() { return last_page; }

    public int getPerPage() { return per_page; }

    public String getQuery() { return query; }

    public String getSeed() { return seed; }
}
