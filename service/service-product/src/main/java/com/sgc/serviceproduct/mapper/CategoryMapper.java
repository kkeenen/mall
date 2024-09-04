package com.sgc.serviceproduct.mapper;

import com.sgc.serviceproduct.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author shi
* @description 针对表【p_category(商品三级分类)】的数据库操作Mapper
* @createDate 2024-09-02 09:07:30
* @Entity com.sgc.serviceproduct.entity.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {

    void removeByCatId(Integer catId);
}




