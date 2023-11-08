package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: szw
 * @Date: 2023/11/2
 * @Description: com.sky.mapper
 * @version: 1.0
 */
@Mapper
public interface OrderMapper {
    void insert(Orders orders);

    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    void update(Orders orders);
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrdertimeLT(Integer status, LocalDateTime orderTime);
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    Double sumByMap(HashMap<String, Object> map);
}
