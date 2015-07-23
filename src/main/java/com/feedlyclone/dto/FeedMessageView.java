package com.feedlyclone.dto;

import com.feedlyclone.domain.entity.AbstractModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * represent one dto rss message
 */
public class FeedMessageView {

    private Integer id;

    private String title;

    private String descriptionClean;

    private String descriptionFull;

    private String link;

    private String author;

    private Date publishDate;
    /** url for image */
    private String image;
    /** url for video */
    private String video;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptionClean() {
        return descriptionClean;
    }

    public void setDescriptionClean(String descriptionClean) {
        this.descriptionClean = descriptionClean;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescriptionFull() {
        return descriptionFull;
    }

    public void setDescriptionFull(String descriptionFull) {
        this.descriptionFull = descriptionFull;
    }
}
