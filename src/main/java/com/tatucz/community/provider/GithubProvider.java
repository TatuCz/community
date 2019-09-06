package com.tatucz.community.provider;

import com.alibaba.fastjson.JSONObject;
import com.tatucz.community.dto.AccessTokenDTO;
import com.tatucz.community.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created on 19-9-3.
 */
@Component
public class GithubProvider {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${github.client.id}")
    private String CLIENT_ID;

    @Value("${github.client.secret}")
    private String CLIENT_SECRET;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Value("${github.url.access.token}")
    private String URL_ACCESS_TOKEN;

    @Value("${github.url.user}")
    private String USER_API;

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("client_id", CLIENT_ID);
        paramMap.put("client_secret", CLIENT_SECRET);
        paramMap.put("code", accessTokenDTO.getCode());
        paramMap.put("state", accessTokenDTO.getState());
        paramMap.put("redirect_uri", redirectUri);

        HttpEntity httpEntity = new HttpEntity(paramMap, httpHeaders);
        try {
            JSONObject res = restTemplate.postForObject(URL_ACCESS_TOKEN, httpEntity, JSONObject.class);
            return res.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<String> list = new ArrayList<>(1);
        list.add("token " + accessToken);
        httpHeaders.put("Authorization", list);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, httpHeaders);
        try {
            ResponseEntity<String> response = restTemplate.exchange(USER_API, HttpMethod.GET, httpEntity, String.class, new HashMap<String, Object>());
            String res = response.getBody();
            System.out.println(res);
            GithubUser githubUser = JSONObject.parseObject(res, GithubUser.class);
            githubUser.setToken(UUID.randomUUID().toString());
            return githubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
