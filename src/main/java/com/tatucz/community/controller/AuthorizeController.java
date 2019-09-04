package com.tatucz.community.controller;

import com.tatucz.community.dto.AccessTokenDTO;
import com.tatucz.community.dto.GithubUser;
import com.tatucz.community.dto.UserDTO;
import com.tatucz.community.mapper.UserMapper;
import com.tatucz.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
                           HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse) {
        accessToken = githubProvider.getAccessToken(new AccessTokenDTO(code, state));
        GithubUser githubUser = githubProvider.getUserInfo(accessToken);
        if (githubUser != null) {
            saveGithubUser(githubUser); //session持久化
            httpServletResponse.addCookie(new Cookie("token", githubUser.getToken()));
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
        userMapper.insert(userDTO);
    }
}
