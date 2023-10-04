package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setMealService;
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishQuery = new LambdaQueryWrapper<>();
        dishQuery.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishQuery);
        if(count1 > 0) {
            throw new CustomException("当前分类下关联了产品,不能删除");
        }

        LambdaQueryWrapper<Setmeal> setMealQuery = new LambdaQueryWrapper<>();
        setMealQuery.eq(Setmeal::getCategoryId, id);
        int count2 = setMealService.count(setMealQuery);
        if(count2 > 0) {
            throw new CustomException("当前分类下关联了套餐,不能删除");
        }
        super.removeById(id);

    }
}
