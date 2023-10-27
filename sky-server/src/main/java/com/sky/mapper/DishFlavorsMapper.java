package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther: szw
 * @Date: 2023/10/27
 * @Description: com.sky.mapper
 * @version: 1.0
 */
@Mapper
public interface DishFlavorsMapper {

    void insetBatch(List<DishFlavor> flavors);
}
