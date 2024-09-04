package com.sgc.serviceproduct.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sgc.serviceproduct.entity.Brand;
import com.sgc.serviceproduct.entity.CategoryBrandRelation;
import com.sgc.serviceproduct.service.BrandService;
import com.sgc.serviceproduct.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RelationService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/product/")
public class RelationController {

    @Autowired
    private CategoryBrandRelationService relationService;

    @GetMapping("relation/list")
    public ResponseEntity<Map<String,Object>> list(@RequestParam(value = "pageNum") int pageNum,
                                                   @RequestParam(value = "pageSize") int pageSize,
                                                   CategoryBrandRelation relation) {
        Page<CategoryBrandRelation> page = new Page<>(pageNum, pageSize);
        Page<CategoryBrandRelation> relations = null;
        if(StringUtils.hasText(relation.getBrandName()) || StringUtils.hasText(relation.getCatelogName())) {
            LambdaQueryWrapper<CategoryBrandRelation> queryWrapper = new LambdaQueryWrapper<CategoryBrandRelation>();
            if(StringUtils.hasText(relation.getBrandName()))
                queryWrapper.eq(CategoryBrandRelation::getBrandName, relation.getBrandName());
            if(StringUtils.hasText(relation.getCatelogName()))
                queryWrapper.eq(CategoryBrandRelation::getCatelogName, relation.getCatelogName());
            relations = relationService.getBaseMapper().selectPage(page, queryWrapper);
        }else{
            relations = relationService.getBaseMapper().selectPage(page, null);
        }
        return ResponseEntity.ok(Map.of("success", true, "data", relations));
    }
    @GetMapping("relation/{id}")
    public ResponseEntity<Map<String,Object>> brand(@PathVariable(value = "id") int id) {
        CategoryBrandRelation relation = relationService.getBaseMapper().selectById(id);
        return ResponseEntity.ok(Map.of("success", true, "data", relation));
    }
    // 增
    @PostMapping("relation")
    public ResponseEntity<Map<String,Object>> addBrand(CategoryBrandRelation relation) {
        boolean save = relationService.save(relation);
        return ResponseEntity.ok(Map.of("success", save));
    }
    // 删
    @DeleteMapping("relation/{id}")
    public ResponseEntity<Map<String,Object>> deleteBrand(@PathVariable(value = "id") Integer id) {
        boolean b = relationService.removeById(id);
        return ResponseEntity.ok(Map.of("success", b));
    }
    // 改
    @PutMapping("relation")
    public ResponseEntity<Map<String,Object>> updateBrand(CategoryBrandRelation relation) {
        boolean b = relationService.updateById(relation);
        return ResponseEntity.ok(Map.of("success", b));
    }


}