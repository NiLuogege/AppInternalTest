package com.airent.appinternaltest.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.util.Date;

@Controller
public class HomeController {
  
    @RequestMapping("/home")
    public String home(Model m) throws Exception {

        return "home";
    }
}