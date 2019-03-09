package com.kaixin.eshop.cache.ha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: ${description}
 * @author: weikailong on 2019-03-09 23:17
 **/
@Controller
public class HelloController {
    
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(String name){
        return "hello, " + name;
    }
    
}
