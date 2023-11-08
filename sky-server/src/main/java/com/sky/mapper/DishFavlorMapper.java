package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: szw
 * @Date: 2023/10/27
 * @Description: com.sky.mapper
 * @version: 1.0
 */
@Mapper
public interface DishFavlorMapper {
    void delBtDIshId(Long id);
}
