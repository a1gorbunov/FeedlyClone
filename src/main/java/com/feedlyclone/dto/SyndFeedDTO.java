package com.feedlyclone.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * provide complete information from feed url
 */

public class SyndFeedDTO {

    private List<FeedMessageDTO> feedMessages;

    private String description;

    private String titile;

    private String link;

    private Date publishedDate;

    public SyndFeedDTO() {
    }

    public SyndFeedDTO(List<FeedMessageDTO> feedMessages) {
        this.feedMessages = feedMessages;
    }

    public SyndFeedDTO(List<FeedMessageDTO> feedMessages, String description, String titile, String link, Date publishDate) {
        this.feedMessages = feedMessages;
        this.description = description;
        this.titile = titile;
        this.link = link;
        this.publishedDate = publishDate;
    }

    public List<FeedMessageDTO> getFeedMessages() {
        return feedMessages;
    }

    public void setFeedMessages(List<FeedMessageDTO> feedMessages) {
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

    public void addFeedMessages(List<FeedMessageDTO> feedMessages){
        if (this.feedMessages == null){
            this.feedMessages = new ArrayList<>();
        }
        this.feedMessages.addAll(feedMessages);
    }
}
