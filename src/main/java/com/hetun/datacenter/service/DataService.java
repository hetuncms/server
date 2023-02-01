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
    private final MainDataInterface netInterface;
    private final PoXiaoZijieNetInterface poXiaoZijieNetInterface;
    Config config;
    NetService netService;
    @Value("${app.teamupdate}")
    Boolean teamUpdate;

    @Autowired
    public DataService(LiveBeanRepository liveBeanRepository, PoXiaoFootBallTeamRepository poXiaoFootBallTeamRepository, PoXiaoBasketBallTeamRepository poXiaoBasketBallTeamRepository, Config config, NetService netService) {
        this.poXiaoFootBallTeamRepository = poXiaoFootBallTeamRepository;
        this.poXiaoBasketBallTeamRepository = poXiaoBasketBallTeamRepository;
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
        PoXiaoZiJieFootBallTeamBean.Result leftTeamDetail = poXiaoFootBallTeamRepository.findById(leftTeam.getTeamId()).get();
        PoXiaoZiJieFootBallTeamBean.Result rightTeamDetail = poXiaoFootBallTeamRepository.findById(rightTeam.getTeamId()).get();

        liveItem.setLeftImg(leftTeamDetail.getPic());
        liveItem.setRightImg(rightTeamDetail.getPic());


        liveItem.setLeftName(leftTeamDetail.getName_zh());
        liveItem.setRightName(rightTeamDetail.getName_zh());
        liveItem.setIsTop(false);
        liveItem.setLiveType(1);
        liveItem.setLiveSource("poxiaozijie");
        liveItem.setLiveId(String.valueOf(leftTeam.getTeamId()) + rightTeam.getTeamId() + pxzjBean.getMatchStartTime());
        liveItem.setLiveStatus(pxzjBean.getHasStream() == 2);

        liveItem.setLongTime(Long.valueOf(pxzjBean.getMatchStartTime()));
        return liveItem;
    }

    LiveItem tripartiteLiveBeanToLiveBean(PoXiaoZiJieBasketBallBean.ResultDTO pxzjBean) {
        LiveItem liveItem = new LiveItem();
        PoXiaoZiJieBasketBallBean.ResultDTO.TeamDTO leftTeam = pxzjBean.getTeam().get(0);
        PoXiaoZiJieBasketBallBean.ResultDTO.TeamDTO rightTeam = pxzjBean.getTeam().get(1);
        PoXiaoZiJieBasketBallTeamBean.Result leftTeamDetail = poXiaoBasketBallTeamRepository.findById(leftTeam.getTeamId()).get();
        PoXiaoZiJieBasketBallTeamBean.Result rightTeamDetail = poXiaoBasketBallTeamRepository.findById(rightTeam.getTeamId()).get();
        liveItem.setLeftImg(leftTeamDetail.getPic());
        liveItem.setRightImg(rightTeamDetail.getPic());
        liveItem.setLeftName(leftTeamDetail.getName_zh());
        liveItem.setRightName(rightTeamDetail.getName_zh());
        liveItem.setIsTop(false);
        liveItem.setLiveType(2);
        liveItem.setLiveSource("poxiaozijie");
        liveItem.setLiveId(String.valueOf(leftTeam.getTeamId()) + rightTeam.getTeamId() + pxzjBean.getMatchStartTime());
        liveItem.setLiveStatus(pxzjBean.getHasStream() == 2);

        liveItem.setLongTime(Long.valueOf(pxzjBean.getMatchStartTime()));
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
        item.setLiveStatus(abcData.getIsVisible() == 1);
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

        requestPoXiaoZiJie();
        deleteOldItem();

    }

    private void requestPoXiaoZiJie() {
        PoXiaoZiJieFootBallBean poXiaoZiJieFootBallBean;
        PoXiaoZiJieBasketBallBean poXiaoZiJieBasketBallBean;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(new Date(System.currentTimeMillis()));
            long begin_time = simpleDateFormat.parse(date).getTime() / 1000;
            Call<PoXiaoZiJieFootBallBean> footBallMatch = poXiaoZijieNetInterface.getFootBallMatch(System.currentTimeMillis() / 1000, begin_time);
            Call<PoXiaoZiJieBasketBallBean> basketBallMatch = poXiaoZijieNetInterface.getBasketBallMatch(System.currentTimeMillis() / 1000, begin_time);
            poXiaoZiJieFootBallBean = footBallMatch.execute().body();
            poXiaoZiJieBasketBallBean = basketBallMatch.execute().body();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        for (PoXiaoZiJieFootBallBean.ResultDTO resultDTO : poXiaoZiJieFootBallBean.getResult()) {
            LiveItem netItem = tripartiteLiveBeanToLiveBean(resultDTO);
            if (netItem != null) {
                updateItem(netItem);
            }
        }

        for (PoXiaoZiJieBasketBallBean.ResultDTO resultDTO : poXiaoZiJieBasketBallBean.getResult()) {
            LiveItem netItem = tripartiteLiveBeanToLiveBean(resultDTO);
            if (netItem != null) {
                updateItem(netItem);
            }
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

}
