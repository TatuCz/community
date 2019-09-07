package com.tatucz.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zihui on 19-8-27.
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest) {
        return "index";
    }
}
