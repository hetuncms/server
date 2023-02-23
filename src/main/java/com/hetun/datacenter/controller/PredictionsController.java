package com.hetun.datacenter.controller;

import com.hetun.datacenter.bean.BaseBean;
import com.hetun.datacenter.bean.PredictionsBean;
import com.hetun.datacenter.bean.PredictionsIndexBean;
import com.hetun.datacenter.service.PredictionsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class PredictionsController {
    private final PredictionsService predictionsService;

    public PredictionsController(PredictionsService predictionsService) {
        this.predictionsService = predictionsService;
    }

    @PostMapping("add_predictions")
    public void addPredictions(@RequestBody PredictionsBean predictionsBean) {
        predictionsService.addPredictions(predictionsBean);
    }

    @GetMapping("get_predictions")
    public BaseBean<PredictionsIndexBean> getPredictions(@RequestParam(name = "pager", required = false) Integer pager,
                                                         @RequestParam(value = "limit", required = false) Integer limit,
                                                         @RequestParam(value = "date") String date) {
        if (limit == null) {
            limit = 10;
        }
        if (pager == null) {
            pager = 0;
        }
        return predictionsService.getAll(pager, limit,date);
    }

    @GetMapping("del_predictions")
    public BaseBean<Object> delPredictions(PredictionsBean predictionsBean) {
        return predictionsService.delete(predictionsBean);
    }
}
