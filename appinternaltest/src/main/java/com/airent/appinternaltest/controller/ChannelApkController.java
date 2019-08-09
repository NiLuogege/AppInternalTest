package com.airent.appinternaltest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ChannelApkController {

    @RequestMapping("/channel")
    public String channelApk(Model model) throws Exception {
        return "channelApk";
    }


    @RequestMapping("/startChannelApk")
    public void startChannelApk(HttpSession session, HttpServletResponse response, String md5Name) throws Exception {
        System.out.println("开始我bavbaole");
    }
}
