package com.hetun.datacenter.bean;

public class BaseListBean<T> extends BaseBean<T> {
    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public static class Builder{

        public <T> BaseBean<T>  build(T data,int totalPages){
            BaseListBean<T> baseBean = new BaseListBean<>();
            baseBean.setCode(20000);
            baseBean.setData(data);
            baseBean.setTotalPages(totalPages);
            return baseBean;
        }
    }
}
