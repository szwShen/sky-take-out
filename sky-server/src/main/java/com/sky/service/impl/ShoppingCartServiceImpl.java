package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: szw
 * @Date: 2023/10/31
 * @Description: com.sky.service.impl
 * @version: 1.0
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        if (shoppingCartList != null && shoppingCartList.size() == 1) {
            ShoppingCart cart = shoppingCartList.get(0);
            shoppingCart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateById(shoppingCart);
        } else {
            Long dishId = shoppingCartDTO.getDishId();

            if (dishId != null) {
                Dish dishById = dishMapper.getDishById(dishId);
                shoppingCart.setName(dishById.getName());
                shoppingCart.setImage(dishById.getImage());
                shoppingCart.setAmount(dishById.getPrice());
            } else {

                Setmeal setmealByDishId = setMealDishMapper.getSetmealById(dishId);
                shoppingCart.setName(setmealByDishId.getName());
                shoppingCart.setImage(setmealByDishId.getImage());
                shoppingCart.setAmount(setmealByDishId.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
        return shoppingCartMapper.list(ShoppingCart.builder().userId(BaseContext.getCurrentId()).build());
    }

    @Override
    public void clean() {
        shoppingCartMapper.cleanById(BaseContext.getCurrentId());
    }
}
