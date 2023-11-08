package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @Auther: szw
 * @Date: 2023/11/2
 * @Description: com.sky.mapper
 * @version: 1.0
 */
@Mapper
public interface OrderDetailMapper {
    void insertBatch(ArrayList<OrderDetail> orderDetails);
}
