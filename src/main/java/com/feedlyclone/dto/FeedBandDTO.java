package com.feedlyclone.dto;

/**
 *  represent full band of news from all user's feeds
 */

public class FeedBandDTO {

    private SyndFeedDTO syndFeed;

    public SyndFeedDTO getSyndFeed() {
        return syndFeed;
    }

    public void setSyndFeed(SyndFeedDTO syndFeed) {
        this.syndFeed = syndFeed;
    }
}
