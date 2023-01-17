package com.hetun.datacenter.bean;

import java.util.List;


public class LiveBean {

    private int status;
    private Integer code;
    private String info;
    private List<LiveItem> live_item;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<LiveItem> getLive_item() {
        return this.live_item;
    }

    public void setLive_item(List<LiveItem> live_item) {
        this.live_item = live_item;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
