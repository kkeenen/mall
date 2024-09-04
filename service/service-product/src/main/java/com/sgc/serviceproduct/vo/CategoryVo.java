package com.sgc.serviceproduct.vo;

import com.sgc.serviceproduct.entity.Category;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryVo implements Serializable {
    private Long catId;

    private String name;

    private Long parentCid;

    private Integer catLevel;

    private Integer productCount;

    private static final long serialVersionUID = 1L;

    private List<Category> children=null;
    private String childName;
}