package com.feedlyclone.integration;

import com.feedlyclone.TestApplication;
import com.feedlyclone.domain.entity.Account;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.domain.repository.AccountRepository;
import com.feedlyclone.domain.repository.UserRepository;
import com.feedlyclone.dto.RssCategoryDTO;
import com.feedlyclone.dto.SyndFeedDTO;
import com.feedlyclone.exceptions.FeedServiceException;
import com.feedlyclone.service.FeedWorkerService;
import com.feedlyclone.service.RssCategoryService;
import com.feedlyclone.web.FeedController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import sun.security.acl.PrincipalImpl;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class WebIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Value("${local.server.port}")
    int serverPort;

    private MockMvc mvc;

    private String testUrl = "http://news.tut.by/rss/index.rss";

    @Autowired
    private RssCategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FeedWorkerService feedWorkerService;

    private String userName = "user";

    private String pass = "password";

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    private void prepareData(){
        User user = new User();
        user.setName(userName);
        user.setPassword(pass);
        Account account = new Account();
        account.setUser(user);
        user.setAccount(account);
        userRepository.save(user);

        FeedWorkerService spyFeedWorkerService = spy(feedWorkerService);
        FeedController feedController = (FeedController) webApplicationContext.getBean("feedController");
        feedController.setFeedWorkerService(spyFeedWorkerService);

        Set<JaasGrantedAuthority> grantedAuthority = Collections.singleton(new JaasGrantedAuthority("USER", new PrincipalImpl(userName)));
        org.springframework.security.core.userdetails.User userSecurity = new org.springframework.security.core.userdetails.User(userName, pass, grantedAuthority);
        Authentication authentification = new AnonymousAuthenticationToken("user", userSecurity, grantedAuthority);
        SecurityContextHolder.getContext().setAuthentication(authentification);

        URL resourceFile = getClass().getClassLoader().getResource("files/feed.xml");
        assertNotNull(resourceFile);
        try {
            doReturn(spyFeedWorkerService.readFeedFromUrl("file://" + resourceFile.getPath())).when(spyFeedWorkerService).readFeedFromUrl(
                    testUrl);
        } catch (FeedServiceException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    @Test
    public void fullTest() throws Exception {
        prepareData();

        mvc.perform(MockMvcRequestBuilders.post("/addFeed").param("newFeedValue", testUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("invalidate"));

        List<RssCategoryDTO> categories = categoryService.getAll();
        assertFalse(CollectionUtils.isEmpty(categories));
        assertEquals(1, categories.size());
        List<String> urls = categories.get(0).getFeedUrls();
        assertFalse(CollectionUtils.isEmpty(urls));
        assertEquals(testUrl, urls.get(0));

        mvc.perform(MockMvcRequestBuilders.post("/addFeed").param("newFeedValue", testUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("invalidate"));

        Object feedHolder =
                mvc.perform(MockMvcRequestBuilders.get("/home"))
                        .andExpect(status().isOk())
                        .andReturn().getModelAndView().getModel().get("feedHolder");

        assertTrue(feedHolder instanceof List);
        List<SyndFeedDTO> feedHolderList;
        try {
            feedHolderList = (List<SyndFeedDTO>) feedHolder;
        } catch (ClassCastException e){
            throw new AssertionError("cannot cast feedHolder to List<SyndFeedDTO>");
        }
        assertFalse(CollectionUtils.isEmpty(feedHolderList));
    }
}
