package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

/**
 * @Auther: szw
 * @Date: 2023/11/2
 * @Description: com.sky.service
 * @version: 1.0
 */
public interface OrderService {
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    void paySuccess(String outTradeNo);

    void reminder(Long id);
}
