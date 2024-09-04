package com.sgc.serviceproduct.service;

import com.sgc.serviceproduct.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author shi
* @description 针对表【p_brand(品牌)】的数据库操作Service
* @createDate 2024-09-02 09:07:30
*/
public interface BrandService extends IService<Brand> {

    Brand selectByBrandId(int ids);

    boolean updateByBrandId(Brand brand);

    int removeByBrandIds(String ids);

    boolean updateBrand(String brandId, String name, MultipartFile logo, String descript);

    int saveBrand(String name, MultipartFile logo, String descript);

    //获取临时签名URL
    String getImageUrl(String path);
}
