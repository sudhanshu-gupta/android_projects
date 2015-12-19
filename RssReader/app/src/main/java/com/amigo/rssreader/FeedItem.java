package com.amigo.rssreader;

import java.sql.Date;

/**
 * Created by sudhanshu.gupta on 18/12/15.
 */
public class FeedItem {
    private String title;
    private String link;
    private String description;
    private Date publicationDate;
    private Integer id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
