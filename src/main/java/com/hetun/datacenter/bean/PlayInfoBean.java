package com.hetun.datacenter.bean;

public class PlayInfoBean {

    private Integer code;
    private String msg;
    private LiveItem data;
    private PoXiaoZiJieLiveInfoBean.Result LiveInfoBean;

    public Integer getCode() {
        return code;
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
