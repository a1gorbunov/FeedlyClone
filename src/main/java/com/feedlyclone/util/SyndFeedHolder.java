package com.feedlyclone.util;

import com.feedlyclone.dto.FeedMessageView;

import java.util.Date;
import java.util.List;

/**
 * used as wrapper for hold full feed info
 */
public class SyndFeedHolder {

    private List<FeedMessageView> feedMessages;

    private String description;

    private String titile;

    private String link;

    private Date publishedDate;

    public SyndFeedHolder(List<FeedMessageView> feedMessages) {
        this.feedMessages = feedMessages;
    }

    public SyndFeedHolder(List<FeedMessageView> feedMessages, String description, String titile, String link, Date publishDate) {
        this.feedMessages = feedMessages;
        this.description = description;
        this.titile = titile;
        this.link = link;
        this.publishedDate = publishDate;
    }

    public List<FeedMessageView> getFeedMessages() {
        return feedMessages;
    }

    public void setFeedMessages(List<FeedMessageView> feedMessages) {
        this.feedMessages = feedMessages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return titile;
    }

    public void setTitle(String titile) {
        this.titile = titile;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
}
