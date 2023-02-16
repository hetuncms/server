package com.hetun.datacenter.controller;

import com.hetun.datacenter.bean.PredictionsBean;
import com.hetun.datacenter.service.PredictionsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PredictionsController {
    PredictionsService predictionsService;

    public PredictionsController(PredictionsService predictionsService) {
        this.predictionsService = predictionsService;
    }

    @PostMapping("add_predictions")
    public void addPredictions(PredictionsBean predictionsBean){
        predictionsService.addPredictions(predictionsBean);
    }

    @GetMapping("get_predictions")
    public List<PredictionsBean> getPredictions(){
        return predictionsService.getAll();
    }
}
