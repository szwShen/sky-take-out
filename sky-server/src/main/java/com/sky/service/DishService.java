package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @Auther: szw
 * @Date: 2023/10/27
 * @Description: com.sky.service
 * @version: 1.0
 */
public interface DishService {
    void addWithFlavor(DishDTO dishDTO);

    PageResult queryForPage(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);


    void update(DishVO dishVO);

    DishVO getDishById(long id);

    List<Dish> list(Long categoryId);

    List<DishVO> listWithFlavor(Dish dish);
}
