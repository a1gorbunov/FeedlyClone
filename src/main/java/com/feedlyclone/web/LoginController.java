package com.feedlyclone.web;

import com.feedlyclone.dto.UserDTO;
import com.feedlyclone.exceptions.NotFoundException;
import com.feedlyclone.service.FeedSecurityService;
import com.feedlyclone.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class LoginController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getSimpleName());

    @Autowired
    private UserService userService;

    @Autowired
    private FeedSecurityService feedSecurityService;

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public
    @ResponseBody
    String printWelcome(Principal principal) {
        return feedSecurityService.getCurrentUserName();
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String addUser(@RequestParam String username, @RequestParam String password, ModelMap modelMap){
        LOGGER.debug("trying to add new user");
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
            UserDTO user;
            try {
                user = userService.getUser(username);
            } catch (NotFoundException e) {
                user = null;
            }
            if (user != null){
                modelMap.addAttribute("invalid", "this username already exist");
            } else {
                userService.addEmptyUser(username, password);
                LOGGER.debug(String.format("new user with name={%s} stored succesfully", username));
                return "login";
            }

        }
        return "register";
    }
}
