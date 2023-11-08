package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId) {
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    @PostMapping
    public Result<String> addDish(@RequestBody DishDTO dishDTO) {
        dishService.addWithFlavor(dishDTO);
        Long categoryId = dishDTO.getCategoryId();
        String key = "dish_" + categoryId;
        cleanCash(key);
        return Result.success();

    }

    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.queryForPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result deleteBatch(@RequestParam List<Long> ids) {
        dishService.deleteBatch(ids);
        cleanCash("dish_*");
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody DishVO dishVO) {
        dishService.update(dishVO);
        cleanCash("dish_*");
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<DishVO> getDishById(@PathVariable("id") long id) {
        DishVO dishVO = dishService.getDishById(id);
        return Result.success(dishVO);
    }

    public void cleanCash(String patton) {
        Set keys = redisTemplate.keys(patton);
        redisTemplate.delete(keys);
    }
}
