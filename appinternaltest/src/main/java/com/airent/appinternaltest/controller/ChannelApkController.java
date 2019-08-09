package com.airent.appinternaltest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChannelApkController {

    @RequestMapping("/channel")
    public String channelApk(Model model) throws Exception {
        return "channelApk";
    }


    @RequestMapping("/startChannelApk")
    public void startChannelApk(Model model) throws Exception {
        System.out.println("开始我bavbaole");
    }
}
