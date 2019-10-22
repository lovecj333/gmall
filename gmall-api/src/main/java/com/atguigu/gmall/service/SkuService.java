package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsSkuInfo;
import java.util.List;

public interface SkuService {

    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuInfoById(long skuId);

    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(long productId);

    List<PmsSkuInfo> getAllSku();
}
