package com.tatucz.community.controller;

import com.tatucz.community.dto.AccessTokenDTO;
import com.tatucz.community.dto.GithubUser;
import com.tatucz.community.dto.UserDTO;
import com.tatucz.community.mapper.UserMapper;
import com.tatucz.community.provider.GithubProvider;
import com.tatucz.community.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取access_token
 * Created on 19-9-3.
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    private String accessToken;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") int state,
                           HttpServletResponse httpServletResponse) {
        accessToken = githubProvider.getAccessToken(new AccessTokenDTO(code, state));
        GithubUser githubUser = githubProvider.getUserInfo(accessToken);
        if (githubUser != null) {
            saveGithubUser(githubUser); //session持久化
            httpServletResponse.addCookie(new Cookie(CookieUtil.TOKEN, githubUser.getToken()));
        }
        return "redirect:/";
    }


    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return githubProvider.getUserInfo(accessToken).toString();
    }

    private void saveGithubUser(GithubUser githubUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setAccountId(String.valueOf(githubUser.getId()));
        userDTO.setNickname(githubUser.getLogin());
        userDTO.setToken(githubUser.getToken());
        userDTO.setCreateTime(System.currentTimeMillis());
        userDTO.setModifiedTime(userDTO.getCreateTime());
        userDTO.setAvatarUrl(githubUser.getAvatarUrl());
        userMapper.insert(userDTO);
    }
}
