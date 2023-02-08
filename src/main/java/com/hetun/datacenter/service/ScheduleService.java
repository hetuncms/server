package com.hetun.datacenter.service;

import com.hetun.datacenter.tripartite.FootballService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ScheduleService {
    private final BallTeamService ballTeamService;
    private final FootballService footballService;
    private final RateOddsService rateOddsService;
    DataService dataService;


    @Value("${app.teamupdate}")
    public Boolean teamUpdate;
    @Autowired
    public ScheduleService(DataService dataService, BallTeamService ballTeamService, FootballService footballService,RateOddsService rateOddsService) {
        this.dataService = dataService;
        this.footballService = footballService;
        this.rateOddsService = rateOddsService;
        this.ballTeamService = ballTeamService;
    }
    @Async
    @Scheduled(fixedRate = 5,timeUnit = TimeUnit.MINUTES)
    public void schedule() {
        System.out.println("update====match");
        dataService.requestData();
    }
    @Async
    @Scheduled(fixedRate = 1,timeUnit = TimeUnit.MINUTES)
    public void scheduleVideo() {
        dataService.requestVideoData();
    }


    @Async
    @Scheduled(fixedRate = 3,timeUnit = TimeUnit.DAYS)
    public void scheduleTeamInfo(){
        if (teamUpdate) {
            ballTeamService.getTeams();
        }
    }

    @Async
    @Scheduled(fixedRate = 3,timeUnit = TimeUnit.DAYS)
    public void scheduleLeague(){
        footballService.requestLeague();
    }

    @Async
    @Scheduled(fixedRate = 7,timeUnit = TimeUnit.DAYS)
    public void scheduleRateCompanyInfo(){
        rateOddsService.getRateCompanyInfo();
    }


}
