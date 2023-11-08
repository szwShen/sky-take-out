package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Auther: szw
 * @Date: 2023/10/31
 * @Description: com.sky.mapper
 * @version: 1.0
 */
@Mapper
public interface ShoppingCartMapper {

    @Update("update shoping_cart set  number=#{number} where id=#{id}")
    void updateById(ShoppingCart shoppingCart);

    @Insert("insert into shopping_cart (name, user_id, dish_id, setmeal_id, dish_flavor, number, amount, image, create_time)  values (#{name},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{image},#{createTime})")
    void insert(ShoppingCart shoppingCart);

    List<ShoppingCart> list(ShoppingCart shoppingCart);

    @Delete("delete  from shopping_cart where  user_id=#{currentId}")
    void cleanById(Long currentId);

    @Delete("delete  from shopping_cart where  user_id=#{currentId}")
    void deleteByUserId(Long id);
}
