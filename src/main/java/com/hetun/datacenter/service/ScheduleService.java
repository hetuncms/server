package com.hetun.datacenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ScheduleService {
    DataService dataService;

    @Autowired
    public ScheduleService(DataService dataService) {
        this.dataService = dataService;
    }

    @Async("taskExecutor")
    @Scheduled(fixedRate = 5,timeUnit = TimeUnit.MINUTES)
    public void schedule() {
        dataService.requestData();
    }

    @Async("taskExecutor")
    @Scheduled(fixedRate = 1,timeUnit = TimeUnit.MINUTES)
    public void scheduleVideo() {
        dataService.requestVideoData();
    }
}
