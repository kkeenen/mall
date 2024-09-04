package com.sgc.serviceproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sgc.serviceproduct.entity.Category;
import com.sgc.serviceproduct.mapper.CategoryMapper;
import com.sgc.serviceproduct.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shi
 * @description 针对表【p_category(商品三级分类)】的数据库操作Service实现
 * @createDate 2024-09-02 09:07:30
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {


    @Override
    public void removeByCatId(Integer catId) {
        CategoryMapper categoryMapper = this.getBaseMapper();
        categoryMapper.removeByCatId(catId);
    }

    @Override
    public List<Category> getCategoryTree() {
        BaseMapper<Category> baseMapper = this.getBaseMapper();
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<Category>();
        queryWrapper.eq(Category::getCatLevel, 1);
        // 所有
        List<Category> categories = baseMapper.selectList(null);
        // 一级目录
        List<Category> level_1 = baseMapper.selectList(queryWrapper);

        List<Category> tree = level_1.stream().map(level_1_item -> {
            List<Category> level_2 = categories.stream().filter(item -> {
                return item.getParentCid().equals(level_1_item.getCatId());
            }).map(level_2_item -> {
                List<Category> level_3 = categories.stream().filter(item -> {
                    return item.getParentCid().equals(level_2_item.getCatId());
                }).collect(Collectors.toList());
                level_2_item.setChildren(level_3);
                return level_2_item;
            }).collect(Collectors.toList());
            level_1_item.setChildren(level_2);
            return level_1_item;
        }).collect(Collectors.toList());
        return tree;
    }
}




