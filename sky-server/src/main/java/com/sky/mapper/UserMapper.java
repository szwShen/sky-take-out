package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @Auther: szw
 * @Date: 2023/10/29
 * @Description: com.sky.mapper
 * @version: 1.0
 */
@Mapper
public interface UserMapper {
    @Select("select  * from user where openid=#{openId}")
    User getByOpenId(String openId);

    void inset(User user);

    @Select("select  * from user where userid=#{userId}")
    User getById(Long userId);
}
