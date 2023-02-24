package com.hetun.datacenter.tripartite.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseNetBean<T> {

    private int code;
    private String message;
    private List<T> result;
}
