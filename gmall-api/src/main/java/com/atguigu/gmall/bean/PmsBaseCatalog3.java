package com.atguigu.gmall.bean;

import java.io.Serializable;

public class PmsBaseCatalog3 implements Serializable {

    private Integer id;
    private String name;
    private Integer catalog2Id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCatalog2Id() {
        return catalog2Id;
    }

    public void setCatalog2Id(Integer catalog2Id) {
        this.catalog2Id = catalog2Id;
    }
}
