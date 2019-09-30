package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import java.util.List;

public interface BaseAttrService {

    List<PmsBaseAttrInfo> attrInfoList(int catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);
}
