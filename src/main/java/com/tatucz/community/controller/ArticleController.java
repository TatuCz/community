package com.tatucz.community.controller;

import com.tatucz.community.dto.ArticleDTO;
import com.tatucz.community.dto.UserDTO;
import com.tatucz.community.mapper.ArticleMapper;
import com.tatucz.community.mapper.UserMapper;
import com.tatucz.community.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created on 19-9-5.
 */
@RequestMapping("/article")
@Controller
public class ArticleController {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "article/add";
    }

    @PostMapping("/publish")
    public String doPublish(HttpServletRequest httpServletRequest,
                            @RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("tag") String tag,
                            Model model) {
        UserDTO user = (UserDTO) httpServletRequest.getSession().getAttribute(SessionUtil.USER_ATTRIBUTE);
        if (user == null) {
            model.addAttribute("errorMsg", "用户未登录");
            return "article/add";
        }

        if (StringUtils.isEmpty(title)) {
            model.addAttribute("errorMsg", "标题不能为空");
            return "article/add";
        }

        if (StringUtils.isEmpty(content)) {
            model.addAttribute("errorMsg", "内容不能为空");
            return "article/add";
        }

        ArticleDTO articleDTO = buildArticle(user.getId(), title, content, tag);
        if (articleDTO != null) {
            articleMapper.publish(articleDTO);
        }

//            model.addAttribute("msg", "发布成功");
        return "redirect:/";

    }

    private ArticleDTO buildArticle(int uid, String title, String content, String tag) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setAuthorId(uid);
        articleDTO.setTitle(title);
        articleDTO.setContent(content);
        articleDTO.setTag(tag);
        articleDTO.setCreateTime(System.currentTimeMillis());
        articleDTO.setModifiedTime(articleDTO.getCreateTime());
        return articleDTO;
    }
}
