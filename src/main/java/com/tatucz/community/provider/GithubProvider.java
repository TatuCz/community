package com.tatucz.community.provider;

import com.alibaba.fastjson.JSONObject;
import com.tatucz.community.dto.AccessTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 19-9-3.
 */
@Component
public class GithubProvider {
    private static final String URL_ACCESS_TOKEN = "https://github.com/login/oauth/access_token";
    private static final String CLIENT_ID = "385ae7feadc372282cdd";
    private static final String CLIENT_SECRET = "09d3d50b862d064d41e0a35e446fcd9f16b05a37";
    private static final String USER_API = "https://api.github.com/user";

    @Autowired
    private RestTemplate restTemplate;

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("client_id", CLIENT_ID);
        paramMap.put("client_secret", CLIENT_SECRET);
        paramMap.put("code", accessTokenDTO.getCode());
        paramMap.put("state", accessTokenDTO.getState());

        HttpEntity httpEntity = new HttpEntity(paramMap, httpHeaders);
        try {
            JSONObject res = restTemplate.postForObject(URL_ACCESS_TOKEN, httpEntity, JSONObject.class);
            return res.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<String> list = new ArrayList<>(1);
        list.add("token " + accessToken);
        httpHeaders.put("Authorization", list);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(USER_API, HttpMethod.GET, httpEntity, String.class, new HashMap<String, Object>());
        return response.getBody();
    }
}
