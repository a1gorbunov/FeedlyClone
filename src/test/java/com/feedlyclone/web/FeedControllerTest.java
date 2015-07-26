package com.feedlyclone.web;

import com.feedlyclone.Application;
import com.feedlyclone.dto.FeedMessage;
import com.feedlyclone.service.FeedWorkerService;
import com.feedlyclone.util.SyndFeedHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedControllerTest extends BaseWebSpringTest{

    @InjectMocks
    private FeedController feedController;

    @Mock
    private FeedWorkerService feedWorkerService;

    private MockMvc mvc;

    private SyndFeedHolder feedHolder;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(feedController)
                .build();

        final Date date = new Date();
        FeedMessage feedMessage = new FeedMessage();
        feedMessage.setTitle("test title");
        feedHolder = new SyndFeedHolder(Collections.singletonList(feedMessage), "description", "title", "link", date);
    }

    @Test
    public void addFeed() throws Exception {
        when(feedWorkerService.readFeedFromUrl(any())).then(invocation -> feedHolder);

        mvc.perform(MockMvcRequestBuilders.post("/addFeed").param("newFeedValue", ""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("home"))
                .andExpect(MockMvcResultMatchers.model().attribute("feedMessages", feedHolder.getFeedMessages()))
                .andExpect(MockMvcResultMatchers.model().attribute("categoryName", feedHolder.getTitle()));
    }

}
