package com.feedlyclone.util;

import com.feedlyclone.dto.FeedMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * used as wrapper for hold full feed info
 */

public class SyndFeedHolder {

    private List<FeedMessage> feedMessages;
    @Deprecated
    private String description;
    @Deprecated
    private String titile;
    @Deprecated
    private String link;
    @Deprecated
    private Date publishedDate;

    public SyndFeedHolder() {
    }

    public SyndFeedHolder(List<FeedMessage> feedMessages) {
        this.feedMessages = feedMessages;
    }
    @Deprecated
    public SyndFeedHolder(List<FeedMessage> feedMessages, String description, String titile, String link, Date publishDate) {
        this.feedMessages = feedMessages;
        this.description = description;
        this.titile = titile;
        this.link = link;
        this.publishedDate = publishDate;
    }

    public List<FeedMessage> getFeedMessages() {
        return feedMessages;
    }

    public void setFeedMessages(List<FeedMessage> feedMessages) {
        this.feedMessages = feedMessages;
    }
    @Deprecated
    public String getDescription() {
        return description;
    }
    @Deprecated
    public void setDescription(String description) {
        this.description = description;
    }
    @Deprecated
    public String getTitle() {
        return titile;
    }
    @Deprecated
    public void setTitle(String titile) {
        this.titile = titile;
    }
    @Deprecated
    public String getLink() {
        return link;
    }
    @Deprecated
    public void setLink(String link) {
        this.link = link;
    }
    @Deprecated
    public Date getPublishedDate() {
        return publishedDate;
    }
    @Deprecated
    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void addFeedMessages(List<FeedMessage> feedMessages){
        if (feedMessages == null){
            feedMessages = new ArrayList<>();
        }
        this.feedMessages.addAll(feedMessages);
    }
}
