package com.seoul.cms.model;

import com.seoul.cms.base.BaseModel;

import java.util.List;

public class AddressList extends BaseModel {
    public int count;
    public List<AddressInfo> list;

    @Override
    public String toString() {
        return "AddressList{" +
                "count=" + count +
                ", list=" + list +
                '}';
    }
}