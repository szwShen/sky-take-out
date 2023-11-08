package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: szw
 * @Date: 2023/10/27
 * @Description: com.sky.service.impl
 * @version: 1.0
 */
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorsMapper dishFlavorsMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Override
    @Transactional
    public void addWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor ->
                    dishFlavor.setDishId(id));
            dishFlavorsMapper.insetBatch(flavors);
        }
    }

    @Override
    public PageResult queryForPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishFlavorsMapper.queryForPage(dishPageQueryDTO);
        long total = page.getTotal();
        List<DishVO> result = page.getResult();
        PageResult pageResult = new PageResult();
        pageResult.setRecords(result);
        pageResult.setTotal(total);
        return pageResult;


    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.select(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }

        }
        List<Long> setIds = setMealDishMapper.getSetmealByDishId(ids);
        if (setIds != null && setIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);

        }
//        for (Long id : setIds) {
//            dishMapper.delById(id);
//        }
        dishMapper.delByIds(setIds);
        dishFlavorsMapper.delByIds(setIds);
    }

    @Override
    public void update(DishVO dishVO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishVO, dish);

//        if (setIds != null && setIds.size() > 0) {
//            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
//
//        }
        dishMapper.update(dish);
        ArrayList<Long> list = new ArrayList<>();
        list.add(dish.getId());
        dishFlavorsMapper.delByIds(list);
        List<DishFlavor> flavors = dishVO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(flavor ->
                    flavor.setDishId(dish.getId()));
        }
        dishFlavorsMapper.insetBatch(flavors);
    }


    @Override
    public DishVO getDishById(long id) {
        Dish dishById = dishMapper.getDishById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dishById, dishVO);
        List<DishFlavor> dishFlavor = dishFlavorsMapper.findFlavorByDishId(id);
        dishVO.setFlavors(dishFlavor);
        return dishVO;
    }

    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorsMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }
}
