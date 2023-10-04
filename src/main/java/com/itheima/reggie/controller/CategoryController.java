package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.itheima.reggie.common.R;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    protected CategoryService categoryService;

    @PostMapping
    @ResponseBody
    public R<String> save(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("新增分类成功 ");
    }
    @GetMapping("/page")
    @ResponseBody
    public R<Page> page(int page, int pageSize) {
        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.orderByDesc(Category::getUpdateTime);

        categoryService.page(pageInfo,lambdaQueryWrapper);

        return R.success(pageInfo);
    }

    @ResponseBody
    @DeleteMapping
    public R<String> delete(Long id) {

        categoryService.remove(id);

        return R.success("删除成功");
    }
}
