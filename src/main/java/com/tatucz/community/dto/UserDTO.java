package com.tatucz.community.dto;

import lombok.Data;

/**
 * Created on 19-9-4.
 */
@Data
public class UserDTO {
    private Integer id;
    private String accountId;
    private String nickname;
    private String token;
    private Long createTime;
    private Long modifiedTime;
    private String avatarUrl;

}
