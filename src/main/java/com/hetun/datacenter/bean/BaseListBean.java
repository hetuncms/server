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

        public <T> BaseListBean<T> build(T data,int totalPages){
            BaseListBean<T> baseBean = new BaseListBean<>();
            baseBean.setCode(20000);
            baseBean.setData(data);
            baseBean.setTotalPages(totalPages);
            return baseBean;
        }
        public <T> BaseListBean<T> buildEmptyError(int totalPages){
            BaseListBean<T> baseBean = new BaseListBean<>();
            baseBean.setCode(50001);
            baseBean.setMsg("没有更多数据");
            baseBean.setTotalPages(totalPages);
            return baseBean;
        }
        public <T> BaseBean<T>  build(T data){
            BaseListBean<T> baseBean = new BaseListBean<>();
            baseBean.setCode(20000);
            baseBean.setData(data);
            return baseBean;
        }
    }
}
