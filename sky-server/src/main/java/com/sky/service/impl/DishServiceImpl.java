package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: szw
 * @Date: 2023/10/27
 * @Description: com.sky.service.impl
 * @version: 1.0
 */
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorsMapper dishFlavorsMapper;

    @Override
    @Transactional
    public void addWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor ->
                    dishFlavor.setDishId(id));
            dishFlavorsMapper.insetBatch(flavors);
        }
    }
}
