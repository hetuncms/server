package com.hetun.datacenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    DataService dataService;

    @Autowired
    public ScheduleService(DataService dataService) {
        this.dataService = dataService;
    }

    @Scheduled(fixedRate = 500000)
    public void schedule() {
//        dataService.requestData();
    }
}
