package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
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
public interface SetMealDishMapper {
    List<Long> getSetmealByDishId(List<Long> ids);

    @Select("select  * from  setmeal_dish where setmeal_dish =#{id} ")
    Setmeal getSetmealById(Long id);

    List<SetmealDish> getSetmealByDishIds(List<Long> ids);

    void insert(SetmealDish setmealDish);

    void deleteByBatchIds(List<Long> ids);

    void insertBatch(List<SetmealDish> setmealDishes);
}
