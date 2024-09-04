package com.sgc.serviceproduct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @TableName p_sku_info
 */
@TableName(value ="p_sku_info")
@Data
public class SkuInfo implements Serializable {
    private Integer id;

    private String skuId;

    private String spuId;

    private String skuName;

    private String skuDesc;

    private Long brandId;

    private String skuImg;

    private BigDecimal price;

    private Long saleCount;

    private static final long serialVersionUID = 1L;
}