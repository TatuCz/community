package com.tatucz.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created on 19-9-5.
 */
@Controller
public class ArticleController {

    @GetMapping("/publish")
    public String publish() {
        return "article/add";
    }
}
