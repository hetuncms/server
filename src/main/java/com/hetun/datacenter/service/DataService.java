package com.hetun.datacenter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hetun.datacenter.Config;
import com.hetun.datacenter.bean.*;
import com.hetun.datacenter.net.MainDataInterface;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.net.PoXiaoZijieNetInterface;
import com.hetun.datacenter.repository.*;
import com.hetun.datacenter.tools.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DataService {
    private final LiveBeanRepository liveBeanRepository;
    private final PoXiaoFootBallTeamRepository poXiaoFootBallTeamRepository;
    private final PoXiaoBasketBallTeamRepository poXiaoBasketBallTeamRepository;
    private final PoXiaoLiveInfoRepository poXiaoLiveInfoRepository;
    private final RateOddsRepository rateOddsRepository;
    private final MainDataInterface netInterface;
    private final PoXiaoZijieNetInterface poXiaoZijieNetInterface;
    private final IndexService indexService;
    Config config;
    NetService netService;
    @Value("${app.teamupdate}")
    Boolean teamUpdate;
    ExecutorService rateOddsThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @Autowired
    public DataService(LiveBeanRepository liveBeanRepository, RateOddsRepository rateOddsRepository, IndexService indexService, PoXiaoFootBallTeamRepository poXiaoFootBallTeamRepository, PoXiaoBasketBallTeamRepository poXiaoBasketBallTeamRepository, PoXiaoLiveInfoRepository poXiaoLiveInfoRepository, Config config, NetService netService) {
        this.poXiaoFootBallTeamRepository = poXiaoFootBallTeamRepository;
        this.poXiaoBasketBallTeamRepository = poXiaoBasketBallTeamRepository;
        this.poXiaoLiveInfoRepository = poXiaoLiveInfoRepository;
        this.indexService = indexService;
        this.liveBeanRepository = liveBeanRepository;
        this.config = config;
        this.netService = netService;
        this.rateOddsRepository = rateOddsRepository;
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
        Optional<LiveItem> byId = liveBeanRepository.findById(item.getId());

        if (!byId.isEmpty()) {
            LiveItem sqlItem = byId.get();
            item.setUpDataCount(sqlItem.getUpDataCount() + 1);
        }

        item.setOld(false);

        item.setUpDataCount(1L);
        item.setUpDataTime(System.currentTimeMillis());
        liveBeanRepository.save(item);
    }

    private void deleteOldItem() {

        if (liveBeanRepository.findAll().size() != 0) {
            liveBeanRepository.deleteAllByOld();
        }
    }

    LiveItem tripartiteLiveBeanToLiveBean(PoXiaoZiJieFootBallBean.ResultDTO pxzjBean) {
        LiveItem liveItem = new LiveItem();

        liveItem.setIsTop(false);
        liveItem.setLiveType(1);
        liveItem.setLiveSource("poxiaozijie");
        liveItem.setLiveStatus(pxzjBean.getStatus());
        liveItem.setId(pxzjBean.getId());
        liveItem.setLongTime(Long.valueOf(pxzjBean.getMatchStartTime()));
        liveItem.setLiveing(indexService.getLiveing(pxzjBean.getId()));
        liveItem.setHasOdds(pxzjBean.getHasOdds());
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
        liveItem.setLiveId(String.valueOf(leftTeam.getTeamId()) + rightTeam.getTeamId() + pxzjBean.getMatchStartTime());
        return liveItem;
    }

    LiveItem tripartiteLiveBeanToLiveBean(PoXiaoZiJieBasketBallBean.ResultDTO pxzjBean) {
        LiveItem liveItem = new LiveItem();
        PoXiaoZiJieBasketBallBean.ResultDTO.TeamDTO leftTeam = null;
        PoXiaoZiJieBasketBallBean.ResultDTO.TeamDTO rightTeam = null;
        PoXiaoZiJieBasketBallTeamBean.Result leftTeamDetail = null;
        PoXiaoZiJieBasketBallTeamBean.Result rightTeamDetail = null;
        try {
            leftTeam = pxzjBean.getTeam().get(0);
            rightTeam = pxzjBean.getTeam().get(1);
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
        liveItem.setId(pxzjBean.getId());
        liveItem.setLongTime(Long.valueOf(pxzjBean.getMatchStartTime()));
        liveItem.setLiveing(indexService.getLiveing(pxzjBean.getId()));
        liveItem.setHasOdds(pxzjBean.getHasOdds());
        return liveItem;
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
        if (teamUpdate) {
            getTeams();
        }


        if (liveBeanRepository.findAll().size() != 0) {
            liveBeanRepository.setAllItemIsOld();
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
            Call<PoXiaoZiJieBasketBallBean> basketBallMatch = poXiaoZijieNetInterface.getBasketBallMatch(DateUtils.now(), startId, start_time_after);

            try {
                poXiaoZiJieBasketBallBean = basketBallMatch.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            for (PoXiaoZiJieBasketBallBean.ResultDTO resultDTO : poXiaoZiJieBasketBallBean.getResult()) {
                LiveItem netItem = tripartiteLiveBeanToLiveBean(resultDTO);

                if (netItem != null) {
                    updateItem(netItem);
                    indexService.getBasketballRateOdds(netItem.getId());
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
        int timeoutCount = 0;

//        int count = 0;
        while (true) {

//            if (count > 5) {
//                break;
//            }
//            count++;
            PoXiaoZiJieFootBallBean poXiaoZiJieFootBallBean = null;
            try {
                Call<PoXiaoZiJieFootBallBean> footBallMatch = poXiaoZijieNetInterface.getFootBallMatch(DateUtils.now(), startId, start_time_after);
                poXiaoZiJieFootBallBean = footBallMatch.execute().body();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                continue;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                continue;
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            for (PoXiaoZiJieFootBallBean.ResultDTO resultDTO : poXiaoZiJieFootBallBean.getResult()) {
                LiveItem netItem = tripartiteLiveBeanToLiveBean(resultDTO);

                if (netItem != null) {
                    updateItem(netItem);
                    indexService.getFootballRateOdds(netItem.getId());

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
            Call<PoXiaoZiJieFootBallTeamBean> teams = poXiaoZijieNetInterface.getFootBallTeams(DateUtils.now(), startId);
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
            Call<PoXiaoZiJieBasketBallTeamBean> teams = poXiaoZijieNetInterface.getBasketBallTeams(DateUtils.now(), startId);
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

    public void requestVideoData() {

        List<Integer> LiveingMatchId = new ArrayList<>();
        int startid = 0;
        int count = 0;
        while (true) {

            if (count > 5) {
                break;
            }
            count++;
            Call<PoXiaoZiJieLiveInfoBean> realTimeVideo = poXiaoZijieNetInterface.getRealTimeVideo(DateUtils.now(), 1, startid, 100);
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
                LiveingMatchId.add(result1.getMatch_id());
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
            Call<PoXiaoZiJieLiveInfoBean> realTimeVideo = poXiaoZijieNetInterface.getRealTimeBasketballVideo(DateUtils.now(), 1, startid, 100);
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
                LiveingMatchId.add(result1.getMatch_id());
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


        List<PoXiaoZiJieBasketBallTeamBean.Result> all = poXiaoBasketBallTeamRepository.findAll();


        poXiaoLiveInfoRepository.deleteAllByOld();
        poXiaoLiveInfoRepository.setAllItemIsOld();
    }

}
