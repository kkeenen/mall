package com.sgc.serviceproduct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName p_spu_info
 */
@TableName(value ="p_spu_info")
@Data
public class SpuInfo implements Serializable {
    private Long id;

    private String spuId;

    private Long catalogId;

    private Long brandId;

    private String spuName;

    private String spuDesc;

    private String img;

    private String publishStatus;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}