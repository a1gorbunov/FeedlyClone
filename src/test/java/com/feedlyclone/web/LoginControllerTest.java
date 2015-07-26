package com.feedlyclone.web;

import com.feedlyclone.domain.entity.User;
import com.feedlyclone.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest extends BaseWebSpringTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserService userService;

    private MockMvc mvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void addUser() throws Exception {
        String userName = "user";
        String userPass = "pass";

        mvc.perform(MockMvcRequestBuilders.post("/adduser").param("username", "").param("password", ""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("register"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("invalid"));
        mvc.perform(MockMvcRequestBuilders.post("/adduser").param("username", userName).param("password", userPass))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("login"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("invalid"));

        when(userService.getUser(userName)).thenReturn(new User());
        mvc.perform(MockMvcRequestBuilders.post("/adduser").param("username", userName).param("password", userPass))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("register"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("invalid"));
    }
}
