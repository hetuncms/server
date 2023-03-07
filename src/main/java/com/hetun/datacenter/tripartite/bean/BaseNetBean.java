package com.hetun.datacenter.tripartite.bean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@MappedSuperclass
public class BaseNetBean<T extends BaseNetBean.GetIdabble> {

    private int code;
    private String message;
    private List<T> result;

    public interface GetIdabble{
        int getId();
    };
}
