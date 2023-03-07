package com.hetun.datacenter.service;

import com.hetun.datacenter.Config;
import com.hetun.datacenter.bean.*;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.net.PoXiaoZijieNetInterface;
import com.hetun.datacenter.repository.*;
import com.hetun.datacenter.tools.DateUtils;
import com.hetun.datacenter.tripartite.bean.LeagueBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class DataService {
    final Config config;
    final NetService netService;
    private final LiveBeanRepository liveBeanRepository;
    private final PoXiaoFootBallTeamRepository poXiaoFootBallTeamRepository;
    private final PoXiaoBasketBallTeamRepository poXiaoBasketBallTeamRepository;
    private final PoXiaoLiveInfoRepository poXiaoLiveInfoRepository;
    private final PoXiaoZijieNetInterface poXiaoZijieNetInterface;
    private final IndexService indexService;
    private final RateOddsService rateOddsService;
    private final BallTeamService ballTeamService;
    private final PoXiaoLeagueRepository poXiaoLeagueRepository;
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    public DataService(LiveBeanRepository liveBeanRepository,
                       BallTeamService ballTeamService,
                       IndexService indexService,
                       RateOddsService rateOddsService,
                       PoXiaoFootBallTeamRepository poXiaoFootBallTeamRepository,
                       PoXiaoBasketBallTeamRepository poXiaoBasketBallTeamRepository,
                       PoXiaoLiveInfoRepository poXiaoLiveInfoRepository,
                       Config config, NetService netService,
                        PoXiaoLeagueRepository PoXiaoLeagueRepository) {
        this.poXiaoFootBallTeamRepository = poXiaoFootBallTeamRepository;
        this.poXiaoBasketBallTeamRepository = poXiaoBasketBallTeamRepository;
        this.ballTeamService = ballTeamService;
        this.poXiaoLiveInfoRepository = poXiaoLiveInfoRepository;
        this.indexService = indexService;
        this.liveBeanRepository = liveBeanRepository;
        this.config = config;
        this.netService = netService;
        this.rateOddsService = rateOddsService;
        poXiaoLeagueRepository = PoXiaoLeagueRepository;
        poXiaoZijieNetInterface = netService.getRetrofit().create(PoXiaoZijieNetInterface.class);
    }

    private void updateItem(LiveItem item) {
        Optional<LiveItem> byId = liveBeanRepository.findById(item.getId());

        if (byId.isPresent()) {
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

        liveItem.setTop(false);
        liveItem.setLiveType(1);
        liveItem.setLiveSource("poxiaozijie");
        liveItem.setLiveStatus(pxzjBean.getStatus());
        liveItem.setMatchStartTime(new Date(pxzjBean.getMatchStartTime()));
        liveItem.setLiveing(indexService.getLiveing(pxzjBean.getId()));
        liveItem.setHasOdds(pxzjBean.getHasOdds());

        PoXiaoZiJieFootBallBean.ResultDTO.TeamDTO leftTeam = pxzjBean.getTeam().get(0);
        PoXiaoZiJieFootBallBean.ResultDTO.TeamDTO rightTeam = pxzjBean.getTeam().get(1);

        PoXiaoZiJieFootBallTeamBean.Result leftTeamDetail;
        PoXiaoZiJieFootBallTeamBean.Result rightTeamDetail;
        liveItem.setId(pxzjBean.getId());
        try {
            Optional<PoXiaoZiJieFootBallTeamBean.Result> leftTeamOptional = poXiaoFootBallTeamRepository.findById(leftTeam.getTeamId());
            leftTeamDetail = leftTeamOptional.orElseGet(() -> ballTeamService.getFootBallTeam(leftTeam.getTeamId()));
            Optional<PoXiaoZiJieFootBallTeamBean.Result> rightTeamOptional = poXiaoFootBallTeamRepository.findById(rightTeam.getTeamId());
            rightTeamDetail = rightTeamOptional.orElseGet(() -> ballTeamService.getFootBallTeam(rightTeam.getTeamId()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

//        liveItem.setLeagueId(pxzjBean.getLeagueId());

        Optional<LeagueBean.LeagueResult> byId = poXiaoLeagueRepository.findById(pxzjBean.getId());
        liveItem.setLeagueResult(byId.get());
        liveItem.setLeftImg(leftTeamDetail.getPic());
        liveItem.setRightImg(rightTeamDetail.getPic());
        liveItem.setLeftName(leftTeamDetail.getName_zh());
        liveItem.setRightName(rightTeamDetail.getName_zh());
        liveItem.setLiveId(String.valueOf(leftTeam.getTeamId()) + rightTeam.getTeamId() + pxzjBean.getMatchStartTime());
        return liveItem;
    }

    LiveItem tripartiteLiveBeanToLiveBean(PoXiaoZiJieBasketBallBean.Result pxzjBean) {
        LiveItem liveItem = new LiveItem();
        PoXiaoZiJieBasketBallBean.Result.Team leftTeam = pxzjBean.getTeam().get(0);
        PoXiaoZiJieBasketBallBean.Result.Team rightTeam = pxzjBean.getTeam().get(1);
        PoXiaoZiJieBasketBallTeamBean.Result leftTeamDetail;
        PoXiaoZiJieBasketBallTeamBean.Result rightTeamDetail;

        liveItem.setId(pxzjBean.getId());

        liveItem.setMainScore(leftTeam.getScore());
        liveItem.setVisitingScore(rightTeam.getScore());
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
//        liveItem.setLeagueId(pxzjBean.getLeagueId());
        Optional<LeagueBean.LeagueResult> byId = poXiaoLeagueRepository.findById(pxzjBean.getId());
        liveItem.setLeagueResult(byId.get());
        liveItem.setTop(false);
        liveItem.setLiveType(2);
        liveItem.setLiveSource("poxiaozijie");
        liveItem.setLiveId(String.valueOf(leftTeam.getTeamId()) + rightTeam.getTeamId() + pxzjBean.getMatchStartTime());
        liveItem.setLiveStatus(pxzjBean.getStatus());
        liveItem.setMatchStartTime(new Date(pxzjBean.getMatchStartTime()));
        liveItem.setLiveing(indexService.getLiveing(pxzjBean.getId()));
        liveItem.setHasOdds(pxzjBean.getHasOdds());

        // 比分
        liveItem.setLeftTeamScore(leftTeam.getScore());
        liveItem.setRightTeamScore(rightTeam.getScore());

        return liveItem;
    }

    public void requestData() {
        log.info("requestData: " + "liveBeanRepository.findAll().size():" + liveBeanRepository.findAll().size());
        if (liveBeanRepository.findAll().size() != 0) {
            Integer count = liveBeanRepository.setAllItemIsOld();
            log.info("mark Old item:" + count);
            log.info("requestData: " + "mark Old item:" + count);
        }

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
            e.printStackTrace();
        }

        requestPoXiaoZiJieFootBall(start_time_after, start_time_before);
        requestPoXiaoZiJieBasketBall(start_time_after, start_time_before);
        Integer count = deleteOldItem();
        log.info("delete old item: " + count);
        log.info("requestData: delete old item: " + count);
    }

    private void requestPoXiaoZiJieBasketBall(long start_time_after, long start_time_before) {
        int startId = 0;

        ArrayList<Integer> needUpdateRateIds = new ArrayList<>();
        while (true) {
            PoXiaoZiJieBasketBallBean poXiaoZiJieBasketBallBean;
            Call<PoXiaoZiJieBasketBallBean> basketBallMatch = poXiaoZijieNetInterface.getBasketBallMatch(startId, start_time_after, start_time_before);

            try {
                poXiaoZiJieBasketBallBean = basketBallMatch.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (poXiaoZiJieBasketBallBean == null) {
                continue;
            }
            if (poXiaoZiJieBasketBallBean.getCode().equals(10004)) {
                log.info("requestPoXiaoZiJieBasketBall:" + "重试");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            for (PoXiaoZiJieBasketBallBean.Result result : poXiaoZiJieBasketBallBean.getResult()) {
                LiveItem netItem = tripartiteLiveBeanToLiveBean(result);

                if (netItem != null) {
                    updateItem(netItem);
                    // 只获取当天比赛的指数
                    if (DateUtils.isToday(netItem.getMatchStartTime().getTime())) {
//                        rateOddsService.saveBasketballRateOdds(netItem.getId());
                        needUpdateRateIds.add(netItem.getId());
                    }
                }
            }

            if (poXiaoZiJieBasketBallBean.getResult().size() < 100) {
                break;
            }

            startId = poXiaoZiJieBasketBallBean.getResult().get(poXiaoZiJieBasketBallBean.getResult().size() - 1).getId() + 1;
        }
        executorService.submit(() -> needUpdateRateIds.forEach(rateOddsService::saveBasketballRateOdds));
    }

    private void requestPoXiaoZiJieFootBall(long start_time_after, long start_time_before) {
        int startId = 0;
        ArrayList<Integer> needUpdateRateIds = new ArrayList<>();
        while (true) {
            PoXiaoZiJieFootBallBean poXiaoZiJieFootBallBean;
            try {
                Call<PoXiaoZiJieFootBallBean> footBallMatch = poXiaoZijieNetInterface.getFootBallMatch(startId, start_time_after, start_time_before);
                poXiaoZiJieFootBallBean = footBallMatch.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            assert poXiaoZiJieFootBallBean != null;
            if (poXiaoZiJieFootBallBean.getCode().equals(10004)) {
                log.info("requestPoXiaoZiJieFootBall:" + "重试");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            if (poXiaoZiJieFootBallBean.getResult() == null) {
                log.info(poXiaoZiJieFootBallBean.getCode() + "===" + poXiaoZiJieFootBallBean.getMessage());
                log.info("requestPoXiaoZiJieFootBall: " + poXiaoZiJieFootBallBean.getCode() + "===" + poXiaoZiJieFootBallBean.getMessage());
            }

            for (PoXiaoZiJieFootBallBean.ResultDTO resultDTO : poXiaoZiJieFootBallBean.getResult()) {
                LiveItem netItem = tripartiteLiveBeanToLiveBean(resultDTO);

                if (netItem != null) {
                    updateItem(netItem);
                    if (DateUtils.isToday(netItem.getMatchStartTime().getTime())) {
//                        rateOddsService.saveFootballRateOdds(netItem.getId());
                        needUpdateRateIds.add(netItem.getId());
                    }
                }
            }

            if (poXiaoZiJieFootBallBean.getResult().size() < 100) {
                break;
            }

            startId = poXiaoZiJieFootBallBean.getResult().get(poXiaoZiJieFootBallBean.getResult().size() - 1).getId() + 1;
        }
        executorService.submit(() -> needUpdateRateIds.forEach(rateOddsService::saveFootballRateOdds));
    }

    public void requestVideoData() {

        poXiaoLiveInfoRepository.setAllItemIsOld();

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
