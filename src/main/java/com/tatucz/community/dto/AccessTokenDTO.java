package com.tatucz.community.dto;

import lombok.Data;

/**
 * Created on 19-9-3.
 */
@Data
public class AccessTokenDTO {
    private String code;
    private int state;

    public AccessTokenDTO(String code, int state) {
        this.code = code;
        this.state = state;
    }
}
