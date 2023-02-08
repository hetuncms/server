package com.hetun.datacenter.tripartite;

import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.tripartite.bean.FootballLeagueBean;
import com.hetun.datacenter.tripartite.net.PoXiaoFootballNetInterface;
import com.hetun.datacenter.tripartite.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

@Service
public class FootballService {
    private final PoXiaoFootballNetInterface poXiaoFootballNetInterface;
    private final LeagueRepository leagueRepository;
    NetService netService;

    @Autowired
    public FootballService(NetService netService, LeagueRepository leagueRepository) {
        this.netService = netService;
        this.leagueRepository = leagueRepository;
        poXiaoFootballNetInterface = netService.getRetrofit().create(PoXiaoFootballNetInterface.class);
    }

    public void requestLeague() {
        int beginId=0;
        while (true){
            Call<FootballLeagueBean> league = poXiaoFootballNetInterface.getLeague(beginId);
            FootballLeagueBean body = null;
            try {
                body = league.execute().body();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<FootballLeagueBean.Result> result = body.getResult();
            if(body.getCode()==10004){
                System.out.println(body.getMessage());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            if (result == null || result.size()<100) {
                break;
            }
            for (FootballLeagueBean.Result item : result) {
                leagueRepository.save(item);
            }


            beginId = result.get(result.size()-1).getId()+1;
        }
    }
}
