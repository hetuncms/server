package com.hetun.datacenter.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BaseListDateBean<T> extends BaseListBean<T>{
    public List<Date> getAllDate;

}
