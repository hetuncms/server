package com.hetun.datacenter.service;

import com.hetun.datacenter.bean.PredictionsBean;
import com.hetun.datacenter.repository.PredictionsRepository;
import org.springframework.stereotype.Service;

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

    public List<PredictionsBean> getAll(){
        return predictionsRepository.findAll();
    }

}
