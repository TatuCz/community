package com.tatucz.community.mapper;

import com.tatucz.community.dto.ArticleDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 帖子内容mapper
 * Created on 19-9-5.
 */
@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article(title, content, create_time, modified_time, author_id, tag) VALUES(#{title}, #{content}, #{createTime}, #{modifiedTime}, #{authorId}, #{tag})")
    boolean publish(ArticleDTO article);

    @Select("SELECT id, title, content, create_time, modified_time, authorId, comment_count, view_count, like_count, tag WHERE id = #{id}")
    ArticleDTO findById(int id);
}
