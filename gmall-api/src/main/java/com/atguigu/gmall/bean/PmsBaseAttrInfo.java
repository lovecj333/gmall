package com.atguigu.gmall.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class PmsBaseAttrInfo implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long id;
    @Column
    private String attrName;
    @Column
    private Integer catalog3Id;
    @Column
    private String isEnabled;
    @Transient
    List<PmsBaseAttrValue> attrValueList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Integer getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Integer catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<PmsBaseAttrValue> getAttrValueList() {
        return attrValueList;
    }

    public void setAttrValueList(List<PmsBaseAttrValue> attrValueList) {
        this.attrValueList = attrValueList;
    }
}
