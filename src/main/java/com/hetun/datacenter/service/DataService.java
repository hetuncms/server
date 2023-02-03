package com.hetun.datacenter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hetun.datacenter.Config;
import com.hetun.datacenter.bean.*;
import com.hetun.datacenter.net.MainDataInterface;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.net.PoXiaoZijieNetInterface;
import com.hetun.datacenter.repository.LiveBeanRepository;
import com.hetun.datacenter.repository.PoXiaoBasketBallTeamRepository;
import com.hetun.datacenter.repository.PoXiaoFootBallTeamRepository;
import com.hetun.datacenter.repository.PoXiaoLiveInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DataService {
    private final LiveBeanRepository liveBeanRepository;
    private final PoXiaoFootBallTeamRepository poXiaoFootBallTeamRepository;
    private final PoXiaoBasketBallTeamRepository poXiaoBasketBallTeamRepository;
    private final PoXiaoLiveInfoRepository poXiaoLiveInfoRepository;
    private final MainDataInterface netInterface;
    private final PoXiaoZijieNetInterface poXiaoZijieNetInterface;
    Config config;
    NetService netService;
    @Value("${app.teamupdate}")
    Boolean teamUpdate;

    @Autowired
    public DataService(LiveBeanRepository liveBeanRepository, PoXiaoFootBallTeamRepository poXiaoFootBallTeamRepository, PoXiaoBasketBallTeamRepository poXiaoBasketBallTeamRepository, PoXiaoLiveInfoRepository poXiaoLiveInfoRepository, Config config, NetService netService) {
        this.poXiaoFootBallTeamRepository = poXiaoFootBallTeamRepository;
        this.poXiaoBasketBallTeamRepository = poXiaoBasketBallTeamRepository;
        this.poXiaoLiveInfoRepository = poXiaoLiveInfoRepository;
        this.liveBeanRepository = liveBeanRepository;
        this.config = config;
        this.netService = netService;
        netInterface = netService.getRetrofit().create(MainDataInterface.class);
        poXiaoZijieNetInterface = netService.getRetrofit().create(PoXiaoZijieNetInterface.class);

    }

    public void requestAcbData() {
        AbcBean abcFootBean = null;
        AbcBean abcBasketBallBean;
        try {
            String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
            abcFootBean = netInterface.requestList(curDate, "1").execute().body();
            Call<AbcBean> abcBeanCall = netInterface.requestList(curDate, "2");
            Response<AbcBean> execute = abcBeanCall.execute();
            abcBasketBallBean = execute.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (AbcBean.Data abcItem : abcFootBean.getData()) {
            LiveItem netItem = tripartiteLiveBeanToLiveBean(abcItem);
            netItem.setLiveType(1);
            if (netItem != null) {
                updateItem(netItem);
            }
        }

        for (AbcBean.Data abcItem : abcBasketBallBean.getData()) {
            LiveItem netItem = tripartiteLiveBeanToLiveBean(abcItem);
            netItem.setLiveType(2);
            if (netItem != null) {
                updateItem(netItem);
            }
        }

    }

    private void updateItem(LiveItem item) {
        LiveItem sqlItem = liveBeanRepository.findAllByLiveId(item.getLiveId());
        if (sqlItem != null) {
            item.setId(sqlItem.getId());
            item.setOld(false);
        }
        item.setUpDataCount(sqlItem == null ? 1 : sqlItem.getUpDataCount() + 1);
        item.setUpDataTime(System.currentTimeMillis());

        LiveItem save = liveBeanRepository.save(item);
    }

    private void deleteOldItem() {

        if (liveBeanRepository.findAll().size() != 0) {
            liveBeanRepository.deleteAllByOld();
        }
    }

    LiveItem tripartiteLiveBeanToLiveBean(PoXiaoZiJieFootBallBean.ResultDTO pxzjBean) {
        LiveItem liveItem = new LiveItem();
        PoXiaoZiJieFootBallBean.ResultDTO.TeamDTO leftTeam = pxzjBean.getTeam().get(0);
        PoXiaoZiJieFootBallBean.ResultDTO.TeamDTO rightTeam = pxzjBean.getTeam().get(1);
        PoXiaoZiJieFootBallTeamBean.Result leftTeamDetail = null;
        PoXiaoZiJieFootBallTeamBean.Result rightTeamDetail = null;
        try {
            leftTeamDetail = poXiaoFootBallTeamRepository.findById(leftTeam.getTeamId()).get();
            rightTeamDetail = poXiaoFootBallTeamRepository.findById(rightTeam.getTeamId()).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        liveItem.setLeftImg(leftTeamDetail.getPic());
        liveItem.setRightImg(rightTeamDetail.getPic());
        liveItem.setLeftName(leftTeamDetail.getName_zh());
        liveItem.setRightName(rightTeamDetail.getName_zh());
        liveItem.setIsTop(false);
        liveItem.setLiveType(1);
        liveItem.setLiveSource("poxiaozijie");
        liveItem.setLiveId(String.valueOf(leftTeam.getTeamId()) + rightTeam.getTeamId() + pxzjBean.getMatchStartTime());
        liveItem.setLiveStatus(pxzjBean.getStatus());
        liveItem.setMatchId(Long.valueOf(pxzjBean.getId()));
        liveItem.setLongTime(Long.valueOf(pxzjBean.getMatchStartTime()));
        liveItem.setLiveing(getLiveing(Long.valueOf(pxzjBean.getId())));
        return liveItem;
    }

    LiveItem tripartiteLiveBeanToLiveBean(PoXiaoZiJieBasketBallBean.ResultDTO pxzjBean) {
        LiveItem liveItem = new LiveItem();
        PoXiaoZiJieBasketBallBean.ResultDTO.TeamDTO leftTeam = pxzjBean.getTeam().get(0);
        PoXiaoZiJieBasketBallBean.ResultDTO.TeamDTO rightTeam = pxzjBean.getTeam().get(1);
        PoXiaoZiJieBasketBallTeamBean.Result leftTeamDetail = null;
        PoXiaoZiJieBasketBallTeamBean.Result rightTeamDetail = null;
        try {
            leftTeamDetail = poXiaoBasketBallTeamRepository.findById(leftTeam.getTeamId()).get();
            rightTeamDetail = poXiaoBasketBallTeamRepository.findById(rightTeam.getTeamId()).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        liveItem.setLeftImg(leftTeamDetail.getPic());
        liveItem.setRightImg(rightTeamDetail.getPic());
        liveItem.setLeftName(leftTeamDetail.getName_zh());
        liveItem.setRightName(rightTeamDetail.getName_zh());
        liveItem.setIsTop(false);
        liveItem.setLiveType(2);
        liveItem.setLiveSource("poxiaozijie");
        liveItem.setLiveId(String.valueOf(leftTeam.getTeamId()) + rightTeam.getTeamId() + pxzjBean.getMatchStartTime());
        liveItem.setLiveStatus(pxzjBean.getHasStream());
        liveItem.setMatchId(Long.valueOf(pxzjBean.getId()));
        liveItem.setLongTime(Long.valueOf(pxzjBean.getMatchStartTime()));
        liveItem.setLiveing(getLiveing(Long.valueOf(pxzjBean.getId())));
        return liveItem;
    }

    boolean getLiveing(Long matchId) {
        // 更新直播状态
        return poXiaoLiveInfoRepository.findByMatchId(matchId) != null;
    }

    LiveItem tripartiteLiveBeanToLiveBean(AbcBean.Data abcData) {
        if (abcData == null) {
            return null;
        }
        LiveItem item = new LiveItem();
        item.setLeftName(abcData.getHomeTeamName());
        item.setRightName(abcData.getAwayTeamName());
        item.setIsTop(false);
        item.setLiveType(abcData.getSportId() == 1 ? 2 : abcData.getSportId() == 3 ? 3 : abcData.getSportId());
        item.setLiveSource("abcData");
        item.setLiveId(abcData.getHomeTeamName() + abcData.getAwayTeamName() + abcData.getDataCreateTime());
        item.setLiveStatus(abcData.getIsVisible());
        try {
            item.setLongTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(abcData.getCreateTime()).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    public void requestData() {
        if (liveBeanRepository.findAll().size() != 0) {
            liveBeanRepository.setAllItemIsOld();
        }

        if (teamUpdate) {
            getTeams();
        }
//        requestAcbData();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        long start_time_after = 0;
        try {
            start_time_after = simpleDateFormat.parse(date).getTime() / 1000;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        requestPoXiaoZiJieFootBall(start_time_after);
        requestPoXiaoZiJieBasketBall(start_time_after);
        deleteOldItem();

    }

    private void requestPoXiaoZiJieBasketBall(long start_time_after) {
        int startId = 0;

        while (true) {
            PoXiaoZiJieBasketBallBean poXiaoZiJieBasketBallBean;
            Call<PoXiaoZiJieBasketBallBean> basketBallMatch = poXiaoZijieNetInterface.getBasketBallMatch(System.currentTimeMillis() / 1000, startId, start_time_after);
            try {
                poXiaoZiJieBasketBallBean = basketBallMatch.execute().body();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (PoXiaoZiJieBasketBallBean.ResultDTO resultDTO : poXiaoZiJieBasketBallBean.getResult()) {
                LiveItem netItem = tripartiteLiveBeanToLiveBean(resultDTO);

                if (netItem != null) {
                    updateItem(netItem);
                }
            }

            if (poXiaoZiJieBasketBallBean.getResult().size() < 100) {
                return;
            }

            startId = poXiaoZiJieBasketBallBean.getResult().get(poXiaoZiJieBasketBallBean.getResult().size() - 1).getId() + 1;
        }
    }

    private void requestPoXiaoZiJieFootBall(long start_time_after) {
        int startId = 0;

        while (true) {
            PoXiaoZiJieFootBallBean poXiaoZiJieFootBallBean;
            try {
                Call<PoXiaoZiJieFootBallBean> footBallMatch = poXiaoZijieNetInterface.getFootBallMatch(System.currentTimeMillis() / 1000, startId, start_time_after);
                poXiaoZiJieFootBallBean = footBallMatch.execute().body();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (PoXiaoZiJieFootBallBean.ResultDTO resultDTO : poXiaoZiJieFootBallBean.getResult()) {
                LiveItem netItem = tripartiteLiveBeanToLiveBean(resultDTO);
                if (netItem != null) {
                    updateItem(netItem);
                }
            }

            if (poXiaoZiJieFootBallBean.getResult().size() < 100) {
                return;
            }

            startId = poXiaoZiJieFootBallBean.getResult().get(poXiaoZiJieFootBallBean.getResult().size() - 1).getId() + 1;
        }
    }

    private void getTeams() {
        String msg = getFootTeams();
        System.out.println(msg);
        String basketBallTeamsMsg = getBasketBallTeams();
        System.out.println(basketBallTeamsMsg);
    }

    private String getFootTeams() {
        int startId = 0;
        String msg = "";
        while (true) {
            Call<PoXiaoZiJieFootBallTeamBean> teams = poXiaoZijieNetInterface.getFootBallTeams(System.currentTimeMillis() / 1000, startId);
            PoXiaoZiJieFootBallTeamBean body;
            try {
                body = teams.execute().body();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (body.getCode() == 10004) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }

            if (body == null || body.getCode() != 0) {
                msg = body.getMessage();
                return msg + "code" + body.getCode() + "===curId:" + startId;
            }
            for (PoXiaoZiJieFootBallTeamBean.Result resultDTO : body.getResult()) {
                poXiaoFootBallTeamRepository.save(resultDTO);
            }

            System.out.println(body.getCode());
            startId = body.getResult().get(body.getResult().size() - 1).getId() + 1;
        }
    }

    private String getBasketBallTeams() {
        int startId = 0;
        String msg = "";
        while (true) {
            Call<PoXiaoZiJieBasketBallTeamBean> teams = poXiaoZijieNetInterface.getBasketBallTeams(System.currentTimeMillis() / 1000, startId);
            PoXiaoZiJieBasketBallTeamBean body = null;
            try {
                body = teams.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (body == null || body.getCode() != 0) {
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
            for (PoXiaoZiJieBasketBallTeamBean.Result resultDTO : body.getResult()) {
                poXiaoBasketBallTeamRepository.save(resultDTO);
            }
            System.out.println(body.getCode());
            startId = body.getResult().get(body.getResult().size() - 1).getId() + 1;
        }
    }

    private Long now() {
        return System.currentTimeMillis() / 1000;
    }

    public void requestVideoData() {
        long startid = 0;
        while (true) {
            Call<PoXiaoZiJieLiveInfoBean> realTimeVideo = poXiaoZijieNetInterface.getRealTimeVideo(now(), 1, startid, 100);
            Response<PoXiaoZiJieLiveInfoBean> result;
            try {
                result = realTimeVideo.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PoXiaoZiJieLiveInfoBean body = result.body();

            if (body == null || body.getResult() == null || body.getCode() == 10006) {
                break;
            }
            for (PoXiaoZiJieLiveInfoBean.Result result1 : body.getResult()) {
                result1.setOld(false);
                poXiaoLiveInfoRepository.save(result1);

                LiveItem byMatchId = liveBeanRepository.findByMatchId(result1.getMatch_id());
                if (byMatchId != null) {
                    byMatchId.setLiveing(true);
                    liveBeanRepository.save(byMatchId);
                }
            }

            if (body.getResult().size() < 100) {
                break;
            }

            PoXiaoZiJieLiveInfoBean.Result result1 = body.getResult().get(body.getResult().size() - 1);
            startid = result1.getMatch_id() + 1;
        }

        while (true) {
            Call<PoXiaoZiJieLiveInfoBean> realTimeVideo = poXiaoZijieNetInterface.getRealTimeBasketballVideo(now(), 1, startid, 100);
            Response<PoXiaoZiJieLiveInfoBean> result;
            try {
                result = realTimeVideo.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PoXiaoZiJieLiveInfoBean body = result.body();

            if (body == null || body.getResult() == null || body.getCode() == 10006) {
                break;
            }
            for (PoXiaoZiJieLiveInfoBean.Result result1 : body.getResult()) {
                result1.setOld(false);
                poXiaoLiveInfoRepository.save(result1);

                LiveItem byMatchId = liveBeanRepository.findByMatchId(result1.getMatch_id());
                if (byMatchId != null) {
                    byMatchId.setLiveing(true);
                    liveBeanRepository.save(byMatchId);
                }
            }

            if (body.getResult().size() < 100) {
                break;
            }

            PoXiaoZiJieLiveInfoBean.Result result1 = body.getResult().get(body.getResult().size() - 1);
            startid = result1.getMatch_id() + 1;
        }

        poXiaoLiveInfoRepository.deleteAllByOld();
        poXiaoLiveInfoRepository.setAllItemIsOld();
    }
}
