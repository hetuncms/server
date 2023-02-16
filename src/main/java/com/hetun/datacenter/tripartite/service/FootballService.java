package com.hetun.datacenter.tripartite.service;

import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.tripartite.bean.LeagueBean;
import com.hetun.datacenter.tripartite.net.PoXiaoNetInterface;
import com.hetun.datacenter.tripartite.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

@Service
public class FootballService {
    private final PoXiaoNetInterface poXiaoNetInterface;
    private final LeagueRepository leagueRepository;
    NetService netService;

    @Autowired
    public FootballService(NetService netService, LeagueRepository leagueRepository) {
        this.netService = netService;
        this.leagueRepository = leagueRepository;
        poXiaoNetInterface = netService.getRetrofit().create(PoXiaoNetInterface.class);
    }

    public void requestLeague() {
        int beginId = 0;
        while (true) {
            Call<LeagueBean> league = poXiaoNetInterface.getFootballLeague(beginId);
            LeagueBean body = null;
            try {
                body = league.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<LeagueBean.Result> result = body.getResult();
            if (body.getCode() == 10004) {
                System.out.println("requestLeague:"+body.getMessage());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            if (result == null || result.size() < 100) {
                break;
            }
            for (LeagueBean.Result item : result) {
                leagueRepository.save(item);
            }


            beginId = result.get(result.size() - 1).getId() + 1;
        }

        beginId = 0;
        while (true) {
            Call<LeagueBean> league = poXiaoNetInterface.getBasketballLeague(beginId);
            LeagueBean body = null;
            try {
                body = league.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            List<LeagueBean.Result> result = body.getResult();
            if (body.getCode() == 10004) {
                System.out.println("requestLeague"+body.getMessage());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            if (result == null || result.size() < 100) {
                break;
            }
            for (LeagueBean.Result item : result) {
                leagueRepository.save(item);
            }
            beginId = result.get(result.size() - 1).getId() + 1;
        }

        System.out.println("赛事信息更新完成");

    }
}
