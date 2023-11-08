package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: szw
 * @Date: 2023/10/30
 * @Description: com.sky.controller.admin
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    //- 新增套餐
//- 套餐分页查询
//- 删除套餐
//- 修改套餐
//- 起售停售套餐
    @PostMapping
    @CacheEvict(cacheNames ="setmealCache",key = "#setmealDTO.id")
    public Result add(@RequestBody SetmealDTO setmealDTO) {
        setmealService.add(setmealDTO);
        return Result.success();
    }

    @PutMapping
    @CacheEvict(cacheNames ="setmealCache",key = "#setmealDTO.id")
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();

    }

    @DeleteMapping
    @CacheEvict(cacheNames ="setmealCache",allEntries = true)
    public Result delete(List<Long> ids) {
        setmealService.deleteBatchIds(ids);
        return Result.success();

    }

    @GetMapping("/{id}")
    public Result<SetmealDTO> add(@PathVariable("id") Long id) {
        SetmealDTO setmealDTO = setmealService.findById(id);
        return Result.success(setmealDTO);
    }

    //改变状态
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    @CacheEvict(cacheNames ="setmealCache",key = "#id")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id) {
        setmealService.startOrStop(status, id);
        return Result.success();
    }

    @GetMapping("/page")
    @CacheEvict(cacheNames ="setmealCache",key = "#setmealPageQueryDTO.id")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult result = setmealService.queryForPage(setmealPageQueryDTO);
        return Result.success(result);
    }
}
