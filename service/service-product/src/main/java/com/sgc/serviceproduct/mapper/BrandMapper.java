package com.sgc.serviceproduct.mapper;

import com.sgc.serviceproduct.entity.Brand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author shi
* @description 针对表【p_brand(品牌)】的数据库操作Mapper
* @createDate 2024-09-02 09:07:30
* @Entity com.sgc.serviceproduct.entity.Brand
*/

public interface BrandMapper extends BaseMapper<Brand> {

    Brand selectByBrandId(int id);

    boolean updateByBrandId(Brand brand);

    int removeByBrandId(Long id);
}




