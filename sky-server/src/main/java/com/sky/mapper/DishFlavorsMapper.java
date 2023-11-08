package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    Page<DishVO> queryForPage(DishPageQueryDTO dishPageQueryDTO);

    void delByIds(List<Long> ids);

    @Select("select  * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> findFlavorByDishId(long id);

    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
