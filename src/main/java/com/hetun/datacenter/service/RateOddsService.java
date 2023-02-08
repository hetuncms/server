package com.hetun.datacenter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.bean.RateOddsBean;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.net.PoXiaoZijieNetInterface;
import com.hetun.datacenter.repository.RateOddsRepository;
import com.hetun.datacenter.tripartite.bean.RateOddsCompanyBean;
import com.hetun.datacenter.tripartite.net.PoXiaoRateOddsNetInterface;
import com.hetun.datacenter.tripartite.repository.RateOddsCompanyRepository;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RateOddsService {
    private final PoXiaoZijieNetInterface poXiaoZijieNetInterface;
    private final RateOddsRepository rateOddsRepository;
    private final PoXiaoRateOddsNetInterface PoXiaoRateOddsNetInterface;
    private final RateOddsCompanyRepository rateOddsCompanyRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    public RateOddsService(RateOddsRepository rateOddsRepository, NetService netService, RateOddsCompanyRepository rateOddsCompanyRepository) {
        this.rateOddsRepository = rateOddsRepository;
        this.rateOddsCompanyRepository = rateOddsCompanyRepository;

        this.poXiaoZijieNetInterface = netService.getRetrofit().create(PoXiaoZijieNetInterface.class);
        this.PoXiaoRateOddsNetInterface = netService.getRetrofit().create(PoXiaoRateOddsNetInterface.class);
    }


    public List<RateOddsBean.Result> saveFootballRateOdds(Integer matchId) {

        List<RateOddsBean.Result> resultSql = rateOddsRepository.findAllByMatchId(matchId);
        if (resultSql != null && !resultSql.isEmpty()) {
            return resultSql;
        }
        poXiaoZijieNetInterface.getOddsDetails(101, matchId).enqueue(new Callback<RateOddsBean>() {
            @Override
            public void onResponse(Call<RateOddsBean> call, Response<RateOddsBean> response) {
                RateOddsBean body = response.body();
                if (body != null && body.getResult() != null) {
                    for (RateOddsBean.Result result : body.getResult()) {
                        rateOddsRepository.save(result);
                    }
                }
            }

            @Override
            public void onFailure(Call<RateOddsBean> call, Throwable t) {
                System.out.println("RateOddsService.saveFootballRateOdds.onFailure");
                t.printStackTrace();
            }
        });
        return null;

    }

    public RateOddsBean saveBasketballRateOdds(Integer matchId) {
        RateOddsBean rateOddsBean = null;
        try {
            rateOddsBean = poXiaoZijieNetInterface.getOddsDetails(102, matchId).execute().body();
            if (rateOddsBean != null && rateOddsBean.getResult() != null) {
                for (RateOddsBean.Result result : rateOddsBean.getResult()) {
                    rateOddsRepository.save(result);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rateOddsBean;
    }


    public void handlerOuYa(String payload) {
        try {
            RateOddsBean.Result.OddsItem wssRateOddsBean = objectMapper.readValue(payload, RateOddsBean.Result.OddsItem.class);
            Optional<RateOddsBean.Result> byId = rateOddsRepository.findById(wssRateOddsBean.getId());
            boolean empty = byId.isEmpty();

            if (!empty) {
                for (RateOddsBean.Result.OddsItem oddsItem : byId.get().getList()) {
                    if (oddsItem.getCompany_id() == wssRateOddsBean.getCompany_id()) {
                        oddsItem.setOption(wssRateOddsBean.getOption());
                        oddsItem.setScore(wssRateOddsBean.getScore());
                        oddsItem.setUpdate_time(wssRateOddsBean.getUpdate_time());
                        oddsItem.setMarket_status(wssRateOddsBean.getMarket_status());
                    }
                }
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<RateOddsBean.Result> getRateOdds(int matchId) {
        List<RateOddsBean.Result> allByMatchId = rateOddsRepository.findAllByMatchId(matchId);
        return allByMatchId;
    }

    public void getRateCompanyInfo() {
        try {
            RateOddsCompanyBean body = PoXiaoRateOddsNetInterface.getRateCompany(101).execute().body();
            List<RateOddsCompanyBean.Result> result = body.getResult();
            for (RateOddsCompanyBean.Result item : result) {
                rateOddsCompanyRepository.save(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
