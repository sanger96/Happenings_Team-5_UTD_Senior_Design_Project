package com.services.scraper.entity;

import lombok.Data;

@Data
public class PageItem {
    public String title;
    public String url;
    public PageItem(){};
    public PageItem(String title, String url){ this.title = title; this.url = url; }
}
