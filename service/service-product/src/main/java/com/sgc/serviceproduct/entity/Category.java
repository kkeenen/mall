package com.sgc.serviceproduct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @TableName p_category
 */
@TableName(value ="p_category")
@Data
public class Category implements Serializable {
    private Long catId;

    private String name;

    private Long parentCid;

    private Integer catLevel;

    private Integer productCount;

    @TableField(exist = false)
    private List<Category> children;

    private static final long serialVersionUID = 1L;

}
