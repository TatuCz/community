package com.tatucz.community.dto;

/**
 * Created on 19-9-3.
 */
public class AccessTokenDTO {
    private String code;
    private int state;

    public AccessTokenDTO(String code, int state) {
        this.code = code;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
