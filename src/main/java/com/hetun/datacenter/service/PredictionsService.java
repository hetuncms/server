package com.hetun.datacenter.service;

import com.hetun.datacenter.bean.BaseBean;
import com.hetun.datacenter.bean.BaseListBean;
import com.hetun.datacenter.bean.PredictionsBean;
import com.hetun.datacenter.bean.PredictionsIndexBean;
import com.hetun.datacenter.repository.PredictionsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PredictionsService {
    PredictionsRepository predictionsRepository;

    public PredictionsService(PredictionsRepository predictionsRepository) {
        this.predictionsRepository = predictionsRepository;
    }

    public void addPredictions(PredictionsBean predictionsBean) {
        predictionsRepository.save(predictionsBean);
    }

    public BaseBean<PredictionsIndexBean> getAll(int pager, int  limit, String dateFormat){
        PageRequest of = PageRequest.of(pager, limit, Sort.by("id"));
        Date parse = null;
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd").parse(dateFormat);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Page<PredictionsBean> predictionsBeanPage = predictionsRepository.findAllByStartTime(parse,of);
        List<Date> allStartTime = predictionsRepository.findAllStartTime();
        List<PredictionsBean> predictionsBeans = predictionsBeanPage.get().toList();
        PredictionsIndexBean predictionsIndexBean = PredictionsIndexBean.builder().allStartTime(allStartTime).predictionsBeans(predictionsBeans).build();
        BaseBean<PredictionsIndexBean> predictionsIndexBeanBaseBean = new BaseListBean.Builder().build(predictionsIndexBean,
                predictionsBeanPage.getTotalPages());
        return predictionsIndexBeanBaseBean;
    }

    public List<PredictionsBean> delete(PredictionsBean predictionsBean) {
        predictionsRepository.delete(predictionsBean);
        return null;
    }
}
