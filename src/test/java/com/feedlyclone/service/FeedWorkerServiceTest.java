package com.feedlyclone.service;

import com.feedlyclone.Application;
import com.feedlyclone.util.SyndFeedHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class FeedWorkerServiceTest {

    @Autowired
    private FeedWorkerService feedWorkerService;

    @Test
    public void readFeedFromUrlTest(){
        URL resourceFile = getClass().getClassLoader().getResource("files/feed.xml");
        assertNotNull(resourceFile);

        SyndFeedHolder feedHolder = feedWorkerService.readFeedFromUrl("file://" + resourceFile.getPath());
        assertNotNull(feedHolder);
        assertNotNull(feedHolder.getFeedMessages());
        assertFalse(feedHolder.getFeedMessages().isEmpty());

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
        assertEquals("TUT.BY: Новости ТУТ - Экономика и бизнес", feedHolder.getTitle());
        assertEquals("http://news.tut.by/", feedHolder.getLink());
        assertEquals("TUT.BY: Новости ТУТ - Экономика и бизнес", feedHolder.getDescription());
        assertEquals(1437638916000L, feedHolder.getPublishedDate().getTime());
    }
}
