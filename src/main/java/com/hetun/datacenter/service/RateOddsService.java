package com.hetun.datacenter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.bean.BaseBean;
import com.hetun.datacenter.bean.RateOddsBean;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.net.PoXiaoZijieNetInterface;
import com.hetun.datacenter.repository.RateOddsRepository;
import com.hetun.datacenter.tripartite.bean.BaseNetBean;
import com.hetun.datacenter.tripartite.bean.RateOddsCompanyBean;
import com.hetun.datacenter.tripartite.net.PoXiaoRateOddsNetInterface;
import com.hetun.datacenter.tripartite.repository.RateOddsCompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class RateOddsService {
    private final PoXiaoZijieNetInterface poXiaoZijieNetInterface;
    private final RateOddsRepository rateOddsRepository;
    private final PoXiaoRateOddsNetInterface PoXiaoRateOddsNetInterface;
    private final RateOddsCompanyRepository rateOddsCompanyRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RateOddsService(RateOddsRepository rateOddsRepository, NetService netService, SimpMessagingTemplate simpMessagingTemplate, RateOddsCompanyRepository rateOddsCompanyRepository) {
        this.rateOddsRepository = rateOddsRepository;
        this.rateOddsCompanyRepository = rateOddsCompanyRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.poXiaoZijieNetInterface = netService.getRetrofit().create(PoXiaoZijieNetInterface.class);
        this.PoXiaoRateOddsNetInterface = netService.getRetrofit().create(PoXiaoRateOddsNetInterface.class);
    }

    private void requestFootballNetRateOdds(int matchId) {
        Call<BaseNetBean<RateOddsBean.Result>> oddsDetails = poXiaoZijieNetInterface.getOddsDetails(101, matchId);
        BaseNetBean<RateOddsBean.Result> body = null;
        try {
            body = oddsDetails.execute().body();
        } catch (IOException e) {
           e.printStackTrace();
        }
        assert body!=null;
        if (body.getResult() != null) {
            rateOddsRepository.saveAll(body.getResult());
        } else if (body.getCode() == 10004) {
            log.info("requestFootballNetRateOdds: body:"+body.getMessage());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requestFootballNetRateOdds(matchId);
        }
    }

    public BaseBean saveFootballRateOdds(Integer matchId) {

        List<RateOddsBean.Result> resultSql = getRateOdds(matchId);
        if (resultSql != null && !resultSql.isEmpty()) {
            return new BaseBean.Builder().buildSucces();
        } else {
            requestFootballNetRateOdds(matchId);
        }
        return new BaseBean.Builder().buildSucces();
    }

    private void requestBasketballRateOdds(int matchId) {
        Call<BaseNetBean<RateOddsBean.Result>> oddsDetails = poXiaoZijieNetInterface.getOddsDetails(102, matchId);
        BaseNetBean<RateOddsBean.Result> rateOddsBean = null;
        try {
            rateOddsBean = oddsDetails.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (rateOddsBean == null){
            log.info("RateOddsService.requestBasketballRateOdds npe");
            return;
        }
        if (rateOddsBean.getCode() == 10004) {
            log.info("requestBasketballRateOdds: 重试");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requestBasketballRateOdds(matchId);
        } else if (rateOddsBean.getResult() != null) {
            rateOddsRepository.saveAll(rateOddsBean.getResult());
        }
    }

    public BaseBean saveBasketballRateOdds(final Integer matchId) {
        List<RateOddsBean.Result> resultSql = getRateOdds(matchId);
        if (resultSql != null && !resultSql.isEmpty()) {
            return  new BaseBean.Builder().buildSucces();
        } else {
            requestBasketballRateOdds(matchId);
        }
        return new BaseBean.Builder().buildFailure("保存篮球指数失败");
    }


    public void handlerOuYa(String payload) {
        try {
            RateOddsBean.Result.OddsItem wssRateOddsBean = objectMapper.readValue(payload, RateOddsBean.Result.OddsItem.class);
            Optional<RateOddsBean.Result> rateOddsBean = rateOddsRepository.findById(wssRateOddsBean.getId());
            boolean empty = rateOddsBean.isEmpty();

            if (!empty) {
                RateOddsBean.Result result = rateOddsBean.get();
                for (RateOddsBean.Result.OddsItem oddsItem : result.getList()) {

                    if (oddsItem.getCompany_id() == wssRateOddsBean.getCompany_id()) {
                        oddsItem.setOption(wssRateOddsBean.getOption());
                        oddsItem.setScore(wssRateOddsBean.getScore());
                        oddsItem.setUpdate_time(wssRateOddsBean.getUpdate_time());
                        oddsItem.setMarket_status(wssRateOddsBean.getMarket_status());
                    }
                }
                rateOddsRepository.save(result);

                final RateOddsBean.Result finalSendResult = result;
                Optional<RateOddsBean.Result.OddsItem> first = result.getList().stream().filter(oddsItem -> oddsItem.getCompany_id() == 1).findFirst();
                if (first.isEmpty()) {
                    return;
                }

                RateOddsBean.Result.OddsItem oddsItem1 = first.get();
                ArrayList<RateOddsBean.Result.OddsItem> oddsItemArrays = new ArrayList<>();
                oddsItemArrays.add(oddsItem1);
                finalSendResult.setList(oddsItemArrays);
                // todo
                simpMessagingTemplate.convertAndSend("/topic/rate_odds", finalSendResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RateOddsBean.Result> getRateOdds(int matchId) {
        return rateOddsRepository.findAllByMatchId(matchId);
    }

    public void getRateCompanyInfo() {
        try {
            Response<RateOddsCompanyBean> execute = PoXiaoRateOddsNetInterface.getRateCompany(101).execute();
            RateOddsCompanyBean soccer = execute.body();
            assert soccer != null;
            List<RateOddsCompanyBean.Result> result = soccer.getResult();
            if (result != null && !result.isEmpty()) {
                rateOddsCompanyRepository.saveAll(result);
            }
            RateOddsCompanyBean basketBall = PoXiaoRateOddsNetInterface.getRateCompany(102).execute().body();
            assert basketBall != null;
            List<RateOddsCompanyBean.Result> basketBallList = basketBall.getResult();
            if (basketBallList != null && !basketBallList.isEmpty()) {
                rateOddsCompanyRepository.saveAll(basketBallList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handlerOuYaFootball(String payload) {
        handlerOuYa(payload);
    }

    public void handlerOuYaBasketball(String payload) {
        handlerOuYa(payload);
    }
}
