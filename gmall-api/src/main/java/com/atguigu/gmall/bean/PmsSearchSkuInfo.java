package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PmsSearchSkuInfo implements Serializable{

    @Id
    private long id;
    private String skuName;
    private String skuDesc;
    private long catalog3Id;
    private BigDecimal price;
    private String skuDefaultImg;
    private double hotScore;
    private long productId;
    private List<PmsSkuAttrValue> skuAttrValueList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    public long getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(long catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
    }

    public double getHotScore() {
        return hotScore;
    }

    public void setHotScore(double hotScore) {
        this.hotScore = hotScore;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public List<PmsSkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<PmsSkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }
}
