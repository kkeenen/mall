package com.sgc.serviceproduct.service;

import com.sgc.serviceproduct.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author shi
* @description 针对表【p_category(商品三级分类)】的数据库操作Service
* @createDate 2024-09-02 09:07:30
*/
public interface CategoryService extends IService<Category> {

    void removeByCatId(Integer catId);

    List<Category> getCategoryTree();

}
