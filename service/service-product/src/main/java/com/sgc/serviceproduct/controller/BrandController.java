package com.sgc.serviceproduct.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sgc.serviceproduct.entity.Brand;
import com.sgc.serviceproduct.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.SchemaOutputResolver;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product/")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("brand/list")
    public ResponseEntity<Map<String,Object>> list(@RequestParam(value = "pageNum") int pageNum,
                                                   @RequestParam(value = "pageSize") int pageSize,
                                                    Brand brand) {
        Page<Brand> page = new Page<>(pageNum, pageSize);
        Page<Brand> brands = null;
        if(StringUtils.hasText(brand.getName())) {
            LambdaQueryWrapper<Brand> queryWrapper = new LambdaQueryWrapper<Brand>();
            queryWrapper.eq(Brand::getName, brand.getName());
            brands = brandService.getBaseMapper().selectPage(page, queryWrapper);
        }else{
            brands = brandService.getBaseMapper().selectPage(page, null);
        }

        List<Brand> records = brands.getRecords().stream().map(item -> {
            String url = brandService.getImageUrl(item.getLogo());
            if(StringUtils.hasText(url)) {
                item.setLogo(url);
            }
            return item;
        }).collect(Collectors.toList());

        brands.setRecords(records);
        return ResponseEntity.ok(Map.of("success", true, "data", brands));
    }
    @GetMapping("brand/{id}")
    public ResponseEntity<Map<String,Object>> brand(@PathVariable(value = "id") int id) {
        Brand brand = brandService.selectByBrandId(id);
        brand.setLogo(brandService.getImageUrl(brand.getLogo()));
        return ResponseEntity.ok(Map.of("success", true, "data", brand));
    }
    // 增
    @PostMapping("brand")
    public ResponseEntity<Map<String,Object>> addBrand(@RequestParam("name") String name,
                                                       @RequestParam("logo") MultipartFile logo,
                                                       @RequestParam("descript") String descript) {

        int i = brandService.saveBrand(name, logo, descript);
        return ResponseEntity.ok(Map.of("success", true));
    }
    // 删
    @DeleteMapping("brand/{ids}")
    public ResponseEntity<Map<String,Object>> deleteBrand(@PathVariable("ids") String ids) {

        int i = brandService.removeByBrandIds(ids);
        return ResponseEntity.ok(Map.of("success", true));
    }
    // 改
    @PutMapping("brand")
    public ResponseEntity<Map<String,Object>> updateBrand( @RequestParam("id") String brandId,
                                                           @RequestParam("name") String name,
                                                           @RequestParam("logo") MultipartFile logo,
                                                           @RequestParam("descript") String descript) {

        boolean b = brandService.updateBrand(brandId, name, logo, descript);
        return ResponseEntity.ok(Map.of("success", b));
    }


}
