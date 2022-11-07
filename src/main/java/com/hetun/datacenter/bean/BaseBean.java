package com.hetun.datacenter.bean;

public class BaseBean<T> {
    public Integer code;
    public T data;
    public String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    private BaseBean() {
    }

    public void setData(T data) {
        this.data = data;
    }
    public static class Builder{
        public <T> BaseBean<T>  build(T data){
            BaseBean<T> baseBean = new BaseBean<>();
            baseBean.setCode(20000);
            baseBean.setData(data);
            return baseBean;
        }
        public <T> BaseBean<T>  buildError(String msg){
            BaseBean<T> baseBean = new BaseBean<>();
            baseBean.setCode(50000);
            baseBean.setMsg(msg);
            return baseBean;
        }
    }
}
