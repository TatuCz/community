package com.tatucz.community.dto;

import lombok.Data;

/**
 * Created on 19-9-3.
 */
@Data
public class GithubUser {
    private String login;
    private int id;
    private String avatarUrl;
    private String token;
}
