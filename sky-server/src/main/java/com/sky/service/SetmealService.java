package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * @Auther: szw
 * @Date: 2023/10/30
 * @Description: com.sky.service
 * @version: 1.0
 */
public interface SetmealService {
    SetmealDTO findById(Long id);

    PageResult queryForPage(SetmealPageQueryDTO setmealPageQueryDTO);

    void startOrStop(Integer status, Long id);

    void add(SetmealDTO setmealDTO);

    void update(SetmealDTO setmealDTO);

    void deleteBatchIds(List<Long> ids);

    List<DishItemVO> getDishItemById(Long id);

    List<Setmeal> list(Setmeal setmeal);
}
