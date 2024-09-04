package com.sgc.serviceproduct.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.sgc.serviceproduct.entity.Brand;
import com.sgc.serviceproduct.mapper.BrandMapper;
import com.sgc.serviceproduct.service.BrandService;
import com.sgc.serviceproduct.vo.CosUploadVo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
* @author shi
* @description 针对表【p_brand(品牌)】的数据库操作Service实现
* @createDate 2024-09-02 09:07:30
*/
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand>
    implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Brand selectByBrandId(int id) {
        Brand brand = brandMapper.selectByBrandId(id);
        return brand;
    }

    @Override
    public boolean updateByBrandId(Brand brand) {
        boolean b = brandMapper.updateByBrandId(brand);
        return b;
    }

    @Override
    public int removeByBrandIds(String ids_string) {
        List<Long> ids = Arrays.stream(ids_string.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        ids.forEach(id->{
            brandMapper.removeByBrandId(id);
        });
        return  1 ;
    }

    @Override
    public boolean updateBrand(String brandId, String name, MultipartFile logo, String descript) {

        // 向腾讯云中保存图片，生成路径，
        CosUploadVo upload = this.upload(logo, "brand");
        Brand brand = new Brand();
        brand.setBrandId(Long.valueOf(brandId));
        brand.setName(name);
        brand.setDescript(descript);
        brand.setLogo(upload.getUrl());
        baseMapper.updateByBrandId(brand);
        // 更改数据库
        return true;
    }
    @Override
    public int saveBrand(String name, MultipartFile logo, String descript) {
        Brand brand = new Brand();
        brand.setName(name);
        brand.setDescript(descript);
        CosUploadVo upload = this.upload(logo, "brand");
        brand.setLogo(upload.getShowUrl());

        int i = baseMapper.insert(brand);
        return i;
    }



    public CosUploadVo upload(MultipartFile file, String path) {

        COSClient cosClient = this.getCosClient();

        //元数据信息
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(file.getSize());
        meta.setContentEncoding("UTF-8");
        meta.setContentType(file.getContentType());

        //向存储桶中保存文件
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); //文件后缀名
        String uploadPath = "/mall/" + path + "/" + formattedDate + "/" + UUID.randomUUID().toString().replaceAll("-", "") + fileType;
        // 01.jpg
        // /driver/auth/0o98754.jpg
        PutObjectRequest putObjectRequest = null;
        try {
            //1 bucket名称
            putObjectRequest = new PutObjectRequest("mall-1315117695",
                    uploadPath,
                    file.getInputStream(),
                    meta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        putObjectRequest.setStorageClass(StorageClass.Standard);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest); //上传文件
        cosClient.shutdown();

        // 获得回显地址
        String imageUrl = this.getImageUrl(uploadPath);

        CosUploadVo uploadVo = new CosUploadVo();
        uploadVo.setUrl(uploadPath);
        uploadVo.setShowUrl(imageUrl);
        return uploadVo;
    }
    public COSClient getCosClient() {

        // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = "AKIDTxRCrU0TxMi0EIcwDbzNkw8BDAymVFqE";//用户的 SecretId，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
        String secretKey = "JbigVArSPEsR0b5UktsdEj7MVm3Oiv12";//用户的 SecretKey，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-beijing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        return cosClient;
    }
    //获取临时签名URL
    public String getImageUrl(String path) {

        if(!StringUtils.hasText(path)) return "";
        //获取cosclient对象
        COSClient cosClient = this.getCosClient();
        //GeneratePresignedUrlRequest
        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest("mall-1315117695" ,
                        path, HttpMethodName.GET);
        //设置临时URL有效期为15分钟
        Date date = new DateTime().plusMinutes(15).toDate();
        request.setExpiration(date);
        //调用方法获取
        URL url = cosClient.generatePresignedUrl(request);
        cosClient.shutdown();
        return url.toString();
    }
}




