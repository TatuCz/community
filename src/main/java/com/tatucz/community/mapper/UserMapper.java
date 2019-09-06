package com.tatucz.community.mapper;

import com.tatucz.community.dto.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created on 19-9-4.
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(account_id, nickname, token, create_time, modified_time, avatar_url) VALUES (#{accountId}, #{nickname}, #{token}, #{createTime}, #{modifiedTime}, #{avatarUrl})")
    void insert(UserDTO userDTO);

    @Select("SELECT id,account_id, nickname, token, create_time, modified_time FROM user WHERE token=#{token}")
    public UserDTO findByToken(String token);

}
