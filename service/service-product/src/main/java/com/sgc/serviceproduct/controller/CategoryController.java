package com.sgc.serviceproduct.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sgc.serviceproduct.entity.Category;
import com.sgc.serviceproduct.service.CategoryService;
import com.sgc.serviceproduct.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategories")
    public ResponseEntity<Map<String, Object>> getCategories() {
        List<Category> categories = categoryService.getBaseMapper().selectList(null);
        return ResponseEntity.ok(Map.of("success", true, "categories", categories));
    }
    @GetMapping("/getCategoryTree")
    public ResponseEntity<Map<String, Object>> getCategoryTree() {
        List<Category> tree = categoryService.getCategoryTree();
        return ResponseEntity.ok(Map.of("success", true, "categories", tree));
    }
    // 新增
    @PostMapping("category")
    public ResponseEntity<Map<String, Object>> addCategory(@RequestBody CategoryVo categoryVo) {
        BaseMapper<Category> baseMapper = categoryService.getBaseMapper();
        Category category = new Category();
        category.setName(categoryVo.getChildName());
        category.setParentCid(categoryVo.getParentCid());

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName, categoryVo.getName());
        category.setCatLevel(baseMapper.selectOne(queryWrapper).getCatLevel() + 1);

        baseMapper.insert(category);

        return ResponseEntity.ok(Map.of("success", true));
    }
    // 修改
    @PutMapping("category")
    public ResponseEntity<Map<String, Object>> updateCategory(@RequestBody CategoryVo categoryVo) {
        BaseMapper<Category> baseMapper = categoryService.getBaseMapper();
        LambdaUpdateWrapper<Category> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(Category::getCatId, categoryVo.getCatId()).set(Category::getName, categoryVo.getChildName());
        baseMapper.update(null, queryWrapper);
        System.out.println(categoryVo);
        return ResponseEntity.ok(Map.of("success", true));
    }
    // 删除
    @DeleteMapping("category/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable(value = "id") Integer catId) {
        System.out.println("deleteCategory");
        categoryService.removeByCatId(catId);
        return ResponseEntity.ok(Map.of("success", true));
    }


    @GetMapping("category/{id}")
    public ResponseEntity<Map<String, Object>> getCategory(@PathVariable(value = "id") Integer id) {
        BaseMapper<Category> baseMapper = categoryService.getBaseMapper();
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getCatId, id);
        Category category = baseMapper.selectOne(queryWrapper);
        return ResponseEntity.ok(Map.of("success", true, "data", category));
    }




}
