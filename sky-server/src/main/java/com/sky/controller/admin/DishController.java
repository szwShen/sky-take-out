package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: szw
 * @Date: 2023/10/27
 * @Description: com.sky.controller.admin
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = {"dish接口"})
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    public Result<String> addDish(@RequestBody DishDTO dishDTO) {
        dishService.addWithFlavor(dishDTO);
        return Result.success();

    }

}
