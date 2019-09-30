package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;
import com.atguigu.gmall.bean.PmsBaseSaleAttr;
import java.util.List;

public interface BaseAttrService {

    List<PmsBaseAttrInfo> attrInfoList(int catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(long attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();
}
