package com.hetun.datacenter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetun.datacenter.bean.OuYaRateOddsBean;
import com.hetun.datacenter.bean.RateOddsBean;
import com.hetun.datacenter.repository.RateOddsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateOddsService {
    ObjectMapper objectMapper = new ObjectMapper();

    private final RateOddsRepository rateOddsRepository;
    public RateOddsService(RateOddsRepository rateOddsRepository) {
        this.rateOddsRepository = rateOddsRepository;
    }

    public void handlerOuYa(String payload) {
        try {
            OuYaRateOddsBean ouYaRateOddsBean = objectMapper.readValue(payload, OuYaRateOddsBean.class);
            Optional<RateOddsBean.Result> byId = rateOddsRepository.findById(ouYaRateOddsBean.getId());
            boolean empty = byId.isEmpty();

//            if (!empty) {
//                for (RateOddsBean.Result.OddsItem oddsItem : byId.get().getList()) {
//                    if (oddsItem.getCompany_id() == ouYaRateOddsBean.getCompany_id()) {
//                        oddsItem.setOption(byId.get());
//                    }
//                }
//            }

        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
    }
}
