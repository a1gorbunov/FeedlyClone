package com.feedlyclone.web;

import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.FeedMessageDTO;
import com.feedlyclone.dto.SyndFeedDTO;
import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.exceptions.NotFoundException;
import com.feedlyclone.service.AccountService;
import com.feedlyclone.service.FeedSecurityService;
import com.feedlyclone.service.FeedWorkerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedControllerTest extends BaseWebSpringTest{

    @InjectMocks
    private FeedController feedController;

    @Mock
    private AccountService accountService;

    @Mock
    private FeedSecurityService feedSecurityService;

    @Mock
    private FeedWorkerService feedWorkerService;

    private MockMvc mvc;

    private SyndFeedDTO feedHolder;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(feedController)
                .build();

        final Date date = new Date();
        FeedMessageDTO feedMessage = new FeedMessageDTO();
        feedMessage.setTitle("test title");
        feedHolder = new SyndFeedDTO(Collections.singletonList(feedMessage), "description", "title", "link", date);
    }

    @Test
    public void addFeed() throws Exception {
        UserDTO userDTO = new UserDTO();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        userDTO.setAccount(accountDTO);

        when(feedWorkerService.readFeedFromUrl(any())).then(invocation -> feedHolder);
        when(feedSecurityService.getCurrentUser()).thenReturn(userDTO);

        mvc.perform(MockMvcRequestBuilders.post("/addFeed").param("newFeedValue", ""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("home"))
                .andExpect(MockMvcResultMatchers.model().attribute("feedMessages", feedHolder.getFeedMessages()))
                .andExpect(MockMvcResultMatchers.model().attribute("categoryName", feedHolder.getTitle()));

        doThrow(new NotFoundException("")).when(accountService).addFeedToAccount(any(), any(), any());
        mvc.perform(MockMvcRequestBuilders.post("/addFeed").param("newFeedValue", ""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("login"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("feedMessages", "categoryName"))
                .andExpect(MockMvcResultMatchers.model().attribute("invalidate", any()));
    }

}
