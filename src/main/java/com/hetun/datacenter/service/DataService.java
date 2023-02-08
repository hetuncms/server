package com.hetun.datacenter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hetun.datacenter.Config;
import com.hetun.datacenter.bean.*;
import com.hetun.datacenter.net.MainDataInterface;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.net.PoXiaoZijieNetInterface;
import com.hetun.datacenter.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final RateOddsService rateOddsService;
    Config config;
    NetService netService;
    ExecutorService rateOddsThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @Autowired
    public DataService(LiveBeanRepository liveBeanRepository, RateOddsRepository rateOddsRepository, IndexService indexService, RateOddsService rateOddsService, PoXiaoFootBallTeamRepository poXiaoFootBallTeamRepository, PoXiaoBasketBallTeamRepository poXiaoBasketBallTeamRepository, PoXiaoLiveInfoRepository poXiaoLiveInfoRepository, Config config, NetService netService) {
        this.poXiaoFootBallTeamRepository = poXiaoFootBallTeamRepository;
        this.poXiaoBasketBallTeamRepository = poXiaoBasketBallTeamRepository;
        this.poXiaoLiveInfoRepository = poXiaoLiveInfoRepository;
        this.indexService = indexService;
        this.liveBeanRepository = liveBeanRepository;
        this.config = config;
        this.netService = netService;
        this.rateOddsRepository = rateOddsRepository;
        this.rateOddsService = rateOddsService;
        netInterface = netService.getRetrofit().create(MainDataInterface.class);
        poXiaoZijieNetInterface = netService.getRetrofit().create(PoXiaoZijieNetInterface.class);
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

    private Integer deleteOldItem() {

        if (liveBeanRepository.findAll().size() != 0) {
            return liveBeanRepository.deleteAllByOld();
        }
        return -1;
    }

    LiveItem tripartiteLiveBeanToLiveBean(PoXiaoZiJieFootBallBean.ResultDTO pxzjBean) {
        LiveItem liveItem = new LiveItem();

        liveItem.setIsTop(false);
        liveItem.setLiveType(1);
        liveItem.setLiveSource("poxiaozijie");
        liveItem.setLiveStatus(pxzjBean.getStatus());
        liveItem.setLongTime(Long.valueOf(pxzjBean.getMatchStartTime()));
        liveItem.setLiveing(indexService.getLiveing(pxzjBean.getId()));
        liveItem.setHasOdds(pxzjBean.getHasOdds());
        PoXiaoZiJieFootBallBean.ResultDTO.TeamDTO leftTeam = pxzjBean.getTeam().get(0);
        PoXiaoZiJieFootBallBean.ResultDTO.TeamDTO rightTeam = pxzjBean.getTeam().get(1);
        PoXiaoZiJieFootBallTeamBean.Result leftTeamDetail = null;
        PoXiaoZiJieFootBallTeamBean.Result rightTeamDetail = null;
        liveItem.setId(pxzjBean.getId());
        try {
            leftTeamDetail = poXiaoFootBallTeamRepository.findById(leftTeam.getTeamId()).get();
            rightTeamDetail = poXiaoFootBallTeamRepository.findById(rightTeam.getTeamId()).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        liveItem.setLeagueId(pxzjBean.getLeagueId());
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

        liveItem.setId(pxzjBean.getId());
        leftTeam = pxzjBean.getTeam().get(0);
        rightTeam = pxzjBean.getTeam().get(1);

        Optional<PoXiaoZiJieBasketBallTeamBean.Result> leftTeamDetailOptional = poXiaoBasketBallTeamRepository.findById(leftTeam.getTeamId());
        if (leftTeamDetailOptional.isPresent()) {
            leftTeamDetail = leftTeamDetailOptional.get();

            liveItem.setLeftImg(leftTeamDetail.getPic());

            liveItem.setLeftName(leftTeamDetail.getName_zh());
        }
        Optional<PoXiaoZiJieBasketBallTeamBean.Result> rightTeamDetailOptional = poXiaoBasketBallTeamRepository.findById(rightTeam.getTeamId());
        if (rightTeamDetailOptional.isPresent()) {
            rightTeamDetail = rightTeamDetailOptional.get();

            liveItem.setRightImg(rightTeamDetail.getPic());
            liveItem.setRightName(rightTeamDetail.getName_zh());
        }
        liveItem.setLeagueId(pxzjBean.getLeagueId());
        liveItem.setIsTop(false);
        liveItem.setLiveType(2);
        liveItem.setLiveSource("poxiaozijie");
        liveItem.setLiveId(String.valueOf(leftTeam.getTeamId()) + rightTeam.getTeamId() + pxzjBean.getMatchStartTime());
        liveItem.setLiveStatus(pxzjBean.getStatus());
        liveItem.setLongTime(Long.valueOf(pxzjBean.getMatchStartTime()));
        liveItem.setLiveing(indexService.getLiveing(pxzjBean.getId()));
        liveItem.setHasOdds(pxzjBean.getHasOdds());
        return liveItem;
    }


    public void requestData() {
        System.out.println("liveBeanRepository.findAll().size():" + liveBeanRepository.findAll().size());
        if (liveBeanRepository.findAll().size() != 0) {
            Integer count = liveBeanRepository.setAllItemIsOld();
            System.out.println("mark Old item:" + count);
        }

//        requestAcbData();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        String day3date = simpleDateFormat.format(new Date(System.currentTimeMillis() + (long) 2 * 24 * 60 * 60 * 1000));
        long start_time_after = 0;
        long start_time_before = 0;
        try {
            // 如果不减1，则获取不到0点的数据
            start_time_after = (simpleDateFormat.parse(date).getTime() / 1000) - 1;
            start_time_before = simpleDateFormat.parse(day3date).getTime() / 1000;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        requestPoXiaoZiJieFootBall(start_time_after, start_time_before);
        requestPoXiaoZiJieBasketBall(start_time_after, start_time_before);
        Integer count = deleteOldItem();
        System.out.println("delete old item: " + count);
    }

    private void requestPoXiaoZiJieBasketBall(long start_time_after, long start_time_before) {
        int startId = 0;

        while (true) {
            PoXiaoZiJieBasketBallBean poXiaoZiJieBasketBallBean;
            Call<PoXiaoZiJieBasketBallBean> basketBallMatch = poXiaoZijieNetInterface.getBasketBallMatch(startId, start_time_after, start_time_before);

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
//                    indexService.getBasketballRateOdds(netItem.getId());

                }
            }

            if (poXiaoZiJieBasketBallBean.getResult().size() < 100) {
                return;
            }

            startId = poXiaoZiJieBasketBallBean.getResult().get(poXiaoZiJieBasketBallBean.getResult().size() - 1).getId() + 1;
        }
    }

    private void requestPoXiaoZiJieFootBall(long start_time_after, long start_time_before) {
        int startId = 0;
        int timeoutCount = 0;

        while (true) {
            PoXiaoZiJieFootBallBean poXiaoZiJieFootBallBean = null;
            try {
                Call<PoXiaoZiJieFootBallBean> footBallMatch = poXiaoZijieNetInterface.getFootBallMatch(startId, start_time_after, start_time_before);
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
                    rateOddsService.saveFootballRateOdds(netItem.getId());
                }
            }

            if (poXiaoZiJieFootBallBean.getResult().size() < 100) {
                return;
            }

            startId = poXiaoZiJieFootBallBean.getResult().get(poXiaoZiJieFootBallBean.getResult().size() - 1).getId() + 1;
        }
    }

    public void requestVideoData() {

        poXiaoLiveInfoRepository.setAllItemIsOld();

        List<Integer> LiveingMatchId = new ArrayList<>();
        int startid = 0;
        while (true) {
            Call<PoXiaoZiJieLiveInfoBean> realTimeVideo = poXiaoZijieNetInterface.getRealTimeVideo(1, startid, 100);
            Response<PoXiaoZiJieLiveInfoBean> result;

            try {
                result = realTimeVideo.execute();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
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
            Call<PoXiaoZiJieLiveInfoBean> realTimeVideo = poXiaoZijieNetInterface.getRealTimeBasketballVideo(1, startid, 100);
            Response<PoXiaoZiJieLiveInfoBean> result;
            try {
                result = realTimeVideo.execute();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
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

        poXiaoLiveInfoRepository.deleteAllByOld();
    }

}
