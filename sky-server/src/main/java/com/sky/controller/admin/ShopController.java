package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: szw
 * @Date: 2023/10/28
 * @Description: com.sky.controller.admin
 * @version: 1.0
 */
@RestController("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController {
    public static final String KEY="shopStatus";
    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation(value = "set")
    public Result setStatus(@PathVariable Integer status) {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(KEY, status);
        return Result.success();

    }

    @GetMapping("/status")
    @ApiOperation(value = "status")
    public Result<Integer> getStatus() {
        ValueOperations ops = redisTemplate.opsForValue();
        Integer o = (Integer) ops.get(KEY);
        return Result.success(o);

    }
}
