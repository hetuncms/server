package com.hetun.datacenter.bean;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.HashMap;

public class MatchBean {

    private int state;
    private String msg;
    private Result result;

    public class Result {
        @JSONField(name = "2")
        HashMap ballType;
    }

    private class ResultType {
    }
}
