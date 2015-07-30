package com.feedlyclone.service;

import com.feedlyclone.BaseSpringTest;
import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.FeedMessageDTO;
import com.feedlyclone.dto.SyndFeedDTO;
import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.exceptions.FeedServiceException;
import com.feedlyclone.exceptions.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class FeedWorkerServiceTest extends BaseSpringTest{

    @Autowired
    private FeedWorkerService feedWorkerService;

    @Autowired
    private AccountService accountService;

    @Test
    public void readFeedFromUrlTest() throws FeedServiceException {
        SyndFeedDTO feedHolder = readTestFeed();

        feedHolder.getFeedMessages().stream().forEach(feedMessage -> {
            assertEquals("", feedMessage.getAuthor());
            assertEquals("Test description 1", feedMessage.getDescriptionClean());
            assertEquals("<p>Test description 1</p>", feedMessage.getDescriptionFull());
            assertEquals("http://img.tyt.by/n/0f/8/vodovorot_2.jpg", feedMessage.getImage());
            assertEquals("http://news.tut.by/economics/457313.html?utm_campaign=news-feed&utm_medium=rss&utm_source=rss-news",
                    feedMessage.getLink());
            assertEquals("Title test 1", feedMessage.getTitle());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(1437637260000L);
            assertEquals(calendar.getTime(), feedMessage.getPublishDate());
        });
        assertEquals("test title", feedHolder.getTitle());
        assertEquals("http://news.tut.by/", feedHolder.getLink());
        assertEquals("test description", feedHolder.getDescription());
        assertEquals(1437638916000L, feedHolder.getPublishedDate().getTime());
    }

    private SyndFeedDTO readTestFeed() throws FeedServiceException {
        URL resourceFile = getClass().getClassLoader().getResource("files/feed.xml");
        assertNotNull(resourceFile);

        SyndFeedDTO feedHolder = feedWorkerService.readFeedFromUrl("file://" + resourceFile.getPath());
        assertNotNull(feedHolder);
        assertNotNull(feedHolder.getFeedMessages());
        assertFalse(feedHolder.getFeedMessages().isEmpty());

        return feedHolder;
    }
}
