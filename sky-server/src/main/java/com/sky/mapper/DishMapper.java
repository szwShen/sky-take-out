package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    Dish select(Long id);

    void delById(Long id);

    void delByIds(List<Long> setIds);


    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    @Select("select  * from dish where  id=#{id}")
    Dish getDishById(long id);

    List<Dish> list(Dish dish);
}
