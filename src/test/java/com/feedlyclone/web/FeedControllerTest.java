package com.feedlyclone.web;

import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.FeedMessageDTO;
import com.feedlyclone.dto.RssCategoryDTO;
import com.feedlyclone.dto.SyndFeedDTO;
import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.exceptions.NotFoundException;
import com.feedlyclone.service.AccountService;
import com.feedlyclone.service.FeedSecurityService;
import com.feedlyclone.service.FeedWorkerService;
import com.feedlyclone.service.RssCategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedControllerTest extends BaseWebSpringTest{

    @InjectMocks
    private FeedController feedController;

    @Mock
    private AccountService accountService;

    @Mock
    private RssCategoryService categoryService;

    @Mock
    private FeedSecurityService feedSecurityService;

    @Mock
    private FeedWorkerService feedWorkerService;

    private MockMvc mvc;

    private SyndFeedDTO feedHolder;

    private String categoryName = "categoryName";

    private UserDTO userDTO;

    private String userName = "test user name";

    @Before
    public void setUp(){
        // to avoid Circular view path
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/test/");

        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(feedController)
                .setViewResolvers(viewResolver)
                .build();

        final Date date = new Date();
        FeedMessageDTO feedMessage = new FeedMessageDTO();
        feedMessage.setTitle("test title");
        feedHolder = new SyndFeedDTO(Collections.singletonList(feedMessage), "description", categoryName, "link", date);

        userDTO = new UserDTO();
        userDTO.setName(userName);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        userDTO.setAccount(accountDTO);
    }

    @Test
    public void addFeed() throws Exception {
        when(feedWorkerService.readFeedFromUrl(any())).then(invocation -> feedHolder);
        when(feedSecurityService.getCurrentUser()).thenReturn(userDTO);

        // positive scenario
        mvc.perform(MockMvcRequestBuilders.post("/addFeed").param("newFeedValue", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("home"));

        doThrow(new NotFoundException("")).when(accountService).addFeedToAccount(1L, categoryName, "");
        ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/addFeed").param("newFeedValue", ""));
                result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("/test/login"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("feedMessages", "categoryName"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("invalidate"));

        // if not user yet
        doThrow(new NotFoundException("")).when(feedSecurityService).getCurrentUser();
        mvc.perform(MockMvcRequestBuilders.post("/addFeed").param("newFeedValue", ""))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/test/login"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("feedMessages", "categoryName"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("invalidate"));
    }

    @Test
    public void home() throws Exception {
        RssCategoryDTO category = new RssCategoryDTO();
        category.setTitle(categoryName);
        List<RssCategoryDTO> categories = Collections.singletonList(category);
        List<SyndFeedDTO> feedHolderList = Collections.singletonList(feedHolder);

        when(categoryService.getCategoriesForAccount(any())).thenReturn(categories);
        when(feedSecurityService.getCurrentUser()).thenReturn(userDTO);
        when(feedWorkerService.getAggregateFeedForUser(userDTO)).thenReturn(feedHolderList);

        // positive scenario
        mvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/test/home"))
                .andExpect(MockMvcResultMatchers.model().attribute("categories", categories))
                .andExpect(MockMvcResultMatchers.model().attribute("feedHolder", feedHolderList))
                .andExpect(MockMvcResultMatchers.model().attribute("username", userName))
                .andExpect(MockMvcResultMatchers.model().attribute("categoryName", "All your feeds"));

        doThrow(new NotFoundException("")).when(feedSecurityService).getCurrentUser();
        mvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/test/login"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("categories", "feedHolder", "username", "categoryName"));
    }
}
