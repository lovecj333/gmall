package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class PmsSkuSaleAttrValue implements Serializable {

    @Id
    @Column
    Long id;
    @Column
    Long skuId;
    @Column
    Long saleAttrId;
    @Column
    Long saleAttrValueId;
    @Column
    String saleAttrName;
    @Column
    String saleAttrValueName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSaleAttrId() {
        return saleAttrId;
    }

    public void setSaleAttrId(Long saleAttrId) {
        this.saleAttrId = saleAttrId;
    }

    public Long getSaleAttrValueId() {
        return saleAttrValueId;
    }

    public void setSaleAttrValueId(Long saleAttrValueId) {
        this.saleAttrValueId = saleAttrValueId;
    }

    public String getSaleAttrName() {
        return saleAttrName;
    }

    public void setSaleAttrName(String saleAttrName) {
        this.saleAttrName = saleAttrName;
    }

    public String getSaleAttrValueName() {
        return saleAttrValueName;
    }

    public void setSaleAttrValueName(String saleAttrValueName) {
        this.saleAttrValueName = saleAttrValueName;
    }
}
