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

    @Insert("INSERT INTO user(account_id, nickname, token, create_time, modified_time) VALUES (#{accountId}, #{nickname}, #{token}, #{createTime}, #{modifiedTime})")
    void insert(UserDTO userDTO);

//    @Select("")
//
}
