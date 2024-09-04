package com.sgc.serviceproduct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName p_brand
 */
@TableName(value ="p_brand")
@Data
public class Brand implements Serializable {
    private Long brandId;

    private String name;

    private String logo;

    private String descript;

    private static final long serialVersionUID = 1L;
}