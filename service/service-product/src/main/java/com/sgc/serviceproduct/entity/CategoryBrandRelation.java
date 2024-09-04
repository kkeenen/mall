package com.sgc.serviceproduct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName p_category_brand_relation
 */
@TableName(value ="p_category_brand_relation")
@Data
public class CategoryBrandRelation implements Serializable {
    private Long id;

    private Long brandId;

    private Long catelogId;

    private String brandName;

    private String catelogName;

    private static final long serialVersionUID = 1L;
}