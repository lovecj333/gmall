package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface PmsProductSaleAttrMapper extends Mapper<PmsProductSaleAttr>{

    List<PmsProductSaleAttr> selectSpuSaleAttrListCheckBySku(@Param("productId") long productId, @Param("skuId") long skuId);
}
