package com.hetun.datacenter.service;

import com.hetun.datacenter.bean.PoXiaoZiJieBasketBallTeamBean;
import com.hetun.datacenter.bean.PoXiaoZiJieFootBallTeamBean;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.net.PoXiaoZijieNetInterface;
import com.hetun.datacenter.repository.PoXiaoBasketBallTeamRepository;
import com.hetun.datacenter.repository.PoXiaoFootBallTeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;
@Slf4j
@Service
public class BallTeamService {

    private final PoXiaoFootBallTeamRepository poXiaoFootBallTeamRepository;
    private final PoXiaoZijieNetInterface poXiaoZijieNetInterface;
    private final PoXiaoBasketBallTeamRepository poXiaoBasketBallTeamRepository;


    @Autowired
    public BallTeamService(NetService netService, PoXiaoFootBallTeamRepository poXiaoFootBallTeamRepository, PoXiaoBasketBallTeamRepository poXiaoBasketBallTeamRepository) {

        poXiaoZijieNetInterface = netService.getRetrofit().create(PoXiaoZijieNetInterface.class);
        this.poXiaoFootBallTeamRepository = poXiaoFootBallTeamRepository;
        this.poXiaoBasketBallTeamRepository = poXiaoBasketBallTeamRepository;

    }

    public void getTeams() {
        String footTeams = getFootTeams();
        String basketBallTeamsMsg = getBasketBallTeams();
        log.info("getTeams() called");
    }


    private String getFootTeams() {
        int startId = 0;
        String msg;
        while (true) {
            Call<PoXiaoZiJieFootBallTeamBean> teams = poXiaoZijieNetInterface.getFootBallTeams(startId);
            PoXiaoZiJieFootBallTeamBean body;
            try {
                body = teams.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (body == null) {
                continue;
            }
            if (body.getCode() == 10004) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            if (body.getCode() != 0) {
                msg = body.getMessage();
                return msg + "code" + body.getCode() + "===curId:" + startId;
            }
            poXiaoFootBallTeamRepository.saveAll(body.getResult());
            startId = body.getResult().get(body.getResult().size() - 1).getId() + 1;
        }
    }

    private String getBasketBallTeams() {
        int startId = 0;
        String msg;
        while (true) {
            Call<PoXiaoZiJieBasketBallTeamBean> teams = poXiaoZijieNetInterface.getBasketBallTeams(startId);
            PoXiaoZiJieBasketBallTeamBean body = null;

            try {
                body = teams.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (body.getCode() == 10004) {
                log.info("getBasketBallTeams: "+"请求限制，5秒后自动重新请求");


                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            } else if (body.getCode() != 0) {
                msg = body.getMessage();
                return msg + "code" + body.getCode() + "===curId:" + startId;
            }

            if (body.getCode() == 10004) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            poXiaoBasketBallTeamRepository.saveAll(body.getResult());

            startId = body.getResult().get(body.getResult().size() - 1).getId() + 1;
        }
    }

    public void addTeam(String payload) {
        // todo
        log.info("addTeam() called with: payload = [" + payload + "]");
    }

    public void updateTeam(String payload) {
        log.info("updateTeam() called with: payload = [" + payload + "]");
    }

    public void delTeam(String payload) {
        log.info("delTeam() called with: payload = [" + payload + "]");
    }

    public PoXiaoZiJieBasketBallTeamBean.Result getBasketBallTeam(Integer teamId) {
        Call<PoXiaoZiJieBasketBallTeamBean> teams = poXiaoZijieNetInterface.getBasketBallTeam(teamId, 1);
        PoXiaoZiJieBasketBallTeamBean body = null;

        try {
            body = teams.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (body != null) {
            List<PoXiaoZiJieBasketBallTeamBean.Result> result = body.getResult();
            if (result != null && !result.isEmpty()) {
                PoXiaoZiJieBasketBallTeamBean.Result result1 = result.get(0);
                if (result1.getId() == teamId) {
                    poXiaoBasketBallTeamRepository.save(result1);
                    return result1;
                }
            }
        }
        log.info("getBasketBallTeam: teamid:"+teamId);
        return null;
    }

    public PoXiaoZiJieFootBallTeamBean.Result getFootBallTeam(Integer teamId) {
        Call<PoXiaoZiJieFootBallTeamBean> teams = poXiaoZijieNetInterface.getFootBallTeam(teamId, 1);
        PoXiaoZiJieFootBallTeamBean body = null;

        try {
            body = teams.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (body != null) {
            List<PoXiaoZiJieFootBallTeamBean.Result> result = body.getResult();
            if (body.getCode() == 10004) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getFootBallTeam(teamId);
            }
            if (result != null && !result.isEmpty()) {
                PoXiaoZiJieFootBallTeamBean.Result result1 = result.get(0);
                if (result1.getId() == teamId) {
                    poXiaoFootBallTeamRepository.save(result1);
                    return result1;
                }
            }
        }
        log.info("getFootBallTeam: "+teamId);
        return null;
    }
}
