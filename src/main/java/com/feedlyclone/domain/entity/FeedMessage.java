package com.feedlyclone.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * represent one rss message
 */
@Entity
@Table(name = "feed_message")
public class FeedMessage extends AbstractModel {

    private String title;

    private String description;

    private String link;

    private String author;

    private String guid;
    /** url for image */
    private String image;
    /** url for video */
    private String video;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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
}
