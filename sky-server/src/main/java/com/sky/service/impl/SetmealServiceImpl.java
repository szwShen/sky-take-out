package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Employee;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: szw
 * @Date: 2023/10/30
 * @Description: com.sky.service.impl
 * @version: 1.0
 */
@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    @Override
    public SetmealDTO findById(Long id) {
        Setmeal setmeal = setmealMapper.findById(id);
        ArrayList<Long> list = new ArrayList<>();
        SetmealDTO setmealDTO = new SetmealDTO();
        BeanUtils.copyProperties(setmeal, setmealDTO);
        list.add(id);
        List<Long> ids = setMealDishMapper.getSetmealByDishId(list);
        List<SetmealDish> dishs = setMealDishMapper.getSetmealByDishIds(ids);
        setmealDTO.setSetmealDishes(dishs);
        return setmealDTO;
    }

    @Override
    public PageResult queryForPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<Setmeal> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        long total = page.getTotal();
        List<Setmeal> result = page.getResult();
        PageResult pageResult = new PageResult();
        pageResult.setRecords(result);
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public void startOrStop(Integer status, Long id) {

        setmealMapper.changeStatus(status, id);
    }

    @Override
    @Transactional
    public void add(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);
        Long id = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(i -> {
            i.setSetmealId(id);


        });
        setMealDishMapper.insertBatch(setmealDishes);


    }

    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);
        Long id = setmeal.getId();
        ArrayList<Long> list = new ArrayList<>();
        List<Long> ids = setMealDishMapper.getSetmealByDishId(list);
        setMealDishMapper.deleteByBatchIds(ids);
        List<SetmealDish> dishes = setmealDTO.getSetmealDishes();
        dishes.forEach(i -> {
            i.setDishId(id);
            setMealDishMapper.insert(i);
        });
    }

    @Override
    @Transactional
    public void deleteBatchIds(List<Long> ids) {
        setmealMapper.delByBatchs(ids);
        setMealDishMapper.deleteByBatchIds(ids);

    }

    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }
}
