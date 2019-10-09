package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.bean.PmsSkuImage;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuImageMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.atguigu.gmall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class SkuServiceImpl implements SkuService{

    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        //skuInfo
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        long skuId = pmsSkuInfo.getId();
        //skuAttrValue
        List<PmsSkuAttrValue> attrValues = pmsSkuInfo.getSkuAttrValueList();
        for(PmsSkuAttrValue attrValue : attrValues){
            attrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(attrValue);
        }
        //skuSaleAttrValue
        List<PmsSkuSaleAttrValue> saleAttrValues = pmsSkuInfo.getSkuSaleAttrValueList();
        for(PmsSkuSaleAttrValue saleAttrValue : saleAttrValues){
            saleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(saleAttrValue);
        }
        //skuImage
        List<PmsSkuImage> skuImages = pmsSkuInfo.getSkuImageList();
        for(PmsSkuImage skuImage : skuImages){
            skuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(skuImage);
        }
    }
}
