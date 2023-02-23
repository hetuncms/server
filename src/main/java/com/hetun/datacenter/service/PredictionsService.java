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
    private final PredictionsRepository predictionsRepository;

    public PredictionsService(PredictionsRepository predictionsRepository) {
        this.predictionsRepository = predictionsRepository;
    }

    public void addPredictions(PredictionsBean predictionsBean) {
        predictionsRepository.save(predictionsBean);
    }

    public BaseBean<PredictionsIndexBean> getAll(int pager, int  limit, String dateFormat){
        PageRequest of = PageRequest.of(pager, limit, Sort.by("id"));
        Date currentShowDate;
        try {
            currentShowDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
            return new BaseBean.Builder().buildFailure("日期格式有误");
        }


        Page<PredictionsBean> predictionsBeanPage = predictionsRepository.findAllByStartTime(currentShowDate,of);
        List<Date> allStartTime = predictionsRepository.findAllStartTime();
        List<PredictionsBean> predictionsBeans = predictionsBeanPage.get().toList();


        if ((allStartTime != null&& !allStartTime.isEmpty()) && predictionsBeanPage.isEmpty()) {
            Date date = allStartTime.get(allStartTime.size() - 1);
            predictionsBeanPage = predictionsRepository.findAllByStartTime(date,of);
            predictionsBeans = predictionsBeanPage.getContent();
            currentShowDate = date;
        }
        PredictionsIndexBean predictionsIndexBean = PredictionsIndexBean.builder()
                .allStartTime(allStartTime).predictionsBeans(predictionsBeans).currentShowDate(currentShowDate).build();
        return new BaseListBean.Builder().build(predictionsIndexBean,
                predictionsBeanPage.getTotalPages());
    }

    public BaseBean<Object> delete(PredictionsBean predictionsBean) {
        predictionsRepository.delete(predictionsBean);
        return new BaseBean.Builder().buildSucces();
    }
}
