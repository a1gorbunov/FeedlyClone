package com.feedlyclone.web;

import com.feedlyclone.domain.UserRepository;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
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

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public
    @ResponseBody
    String printWelcome(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return null;
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String addUser(@RequestParam String username, @RequestParam String password, ModelMap modelMap){
        LOGGER.debug("trying to add new user");
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
            if (userService.getUser(username) != null){
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
