package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsProductImage;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import java.util.List;

public interface SpuService {

    List<PmsProductInfo> spuList(long catalog3Id);

    List<PmsProductSaleAttr> spuSaleAttrList(long productId);

    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(long productId, long skuId);

    List<PmsProductImage> spuImageList(long productId);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);
}
