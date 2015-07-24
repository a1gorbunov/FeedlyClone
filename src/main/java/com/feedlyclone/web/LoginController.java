package com.feedlyclone.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public
    @ResponseBody
    String printWelcome(Principal principal) {
        return principal.getName();
    }

    @RequestMapping(value = "/logout")
    public String logout(){
        SecurityContextHolder.clearContext();
        return "login";
    }
}
