package com.seoul.cms.model;


import com.seoul.cms.base.BaseModel;

public class AddressInfo extends BaseModel {
    public int rnum;
    public String juso;
    public String dong;

    @Override
    public String toString() {
        return "AddressInfo{" +
                "rnum=" + rnum +
                ", juso=" + juso +
                ", dong='" + dong + '\'' +
                "} " + super.toString();
    }
}