package com.hetun.datacenter.bean;

import java.util.List;

public class PlayInfoBean {

    private Integer code;
    private String msg;
    private LiveItem data;
    private List<RateOddsBean.Result> oddsItem;
    private PoXiaoZiJieLiveInfoBean.Result LiveInfoBean;

    public Integer getCode() {
        return code;
    }

    public List<RateOddsBean.Result> getOddsItem() {
        return oddsItem;
    }

    public void setOddsItem(List<RateOddsBean.Result> oddsItem) {
        this.oddsItem = oddsItem;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LiveItem getData() {
        return data;
    }

    public void setData(LiveItem data) {
        this.data = data;
    }

    public PoXiaoZiJieLiveInfoBean.Result getLiveInfoBean() {
        return LiveInfoBean;
    }

    public void setLiveInfoBean(PoXiaoZiJieLiveInfoBean.Result liveInfoBean) {
        LiveInfoBean = liveInfoBean;
    }
}
