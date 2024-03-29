package com.atguigu.gmall.bean;

import java.io.Serializable;
import java.util.List;

public class PmsSearchParam implements Serializable{

    private long catalog3Id;

    private String keyword;

    private List<PmsSkuAttrValue> skuAttrValueList;

    public long getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(long catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<PmsSkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<PmsSkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }
}
