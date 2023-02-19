package com.hetun.datacenter.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class PredictionsIndexBean {
    private List<Date> allStartTime;
    List<PredictionsBean> predictionsBeans;
}
