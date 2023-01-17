package com.hetun.datacenter.service;

import com.hetun.datacenter.Config;
import com.hetun.datacenter.bean.LiveItem;
import com.hetun.datacenter.bean.MainLiveBean;
import com.hetun.datacenter.net.MainDataInterface;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.repository.LiveBeanRepository;
import com.hetun.datacenter.repository.TripartiteLiveBeanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class DataService {
    private final TripartiteLiveBeanRepository tripartiteLiveBeanRepository;
    private final LiveBeanRepository liveBeanRepository;
    private final MainDataInterface netInterface;
    Config config;
    NetService netService;

    @Autowired
    public DataService(TripartiteLiveBeanRepository tripartiteLiveBeanRepository, LiveBeanRepository liveBeanRepository, Config config, NetService netService) {
        this.tripartiteLiveBeanRepository = tripartiteLiveBeanRepository;
        this.liveBeanRepository = liveBeanRepository;
        this.config = config;
        this.netService = netService;
        netInterface = netService.getRetrofit().create(MainDataInterface.class);
    }



    public void requestMainData() {

        List<MainLiveBean> mainLiveList;
        try {
            mainLiveList = netInterface.requestMainData().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (MainLiveBean mainLiveBean : mainLiveList) {
            LiveItem netItem = tripartiteLiveBeanToLiveBean(mainLiveBean);
            updateItem(netItem);
        }
    }

    private void updateItem(LiveItem item) {
        LiveItem sqlItem = liveBeanRepository.findAllByLiveId(item.getLiveId());
        if (sqlItem != null) {
            item.setId(sqlItem.getId());
        }
        item.setUpDataCount(sqlItem == null ? 1 : sqlItem.getUpDataCount() + 1);
        item.setUpDataTime(System.currentTimeMillis());
        liveBeanRepository.save(item);
    }

    private void deleteOldItem() {

        if (liveBeanRepository.findAll().size() != 0) {
            liveBeanRepository.deleteAllByOld();
        }
    }

    LiveItem tripartiteLiveBeanToLiveBean(MainLiveBean mainLiveBean) {
        LiveItem item = new LiveItem();
        item.setLeftName(mainLiveBean.getOpp1());
        item.setRightName(mainLiveBean.getOpp2());
        item.setIsTop(false);
        item.setLiveType(mainLiveBean.getSportId() == 1 ? 1 : mainLiveBean.getSportId() == 3 ? 2 : mainLiveBean.getSportId());
        item.setLiveSource("live_main");
        item.setLiveId(mainLiveBean.getOpp1ID() + mainLiveBean.getOpp2ID() + mainLiveBean.getLongTime()+item.getLiveSource());
        if (mainLiveBean.getStreamInfo() != null) {
            item.setLiveStatus(mainLiveBean.getStreamInfo().getState());
        }

        item.setTitle(mainLiveBean.getLiga());
        try {
            item.setLongTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(Calendar.getInstance().get(1) + "-" + mainLiveBean.getTime()).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

//    public List<LiveItem.Item> requestLocalData(Integer gameType) {
//
//
//        List<LocalLiveBean> localLiveBeans = localLiveMapper.selectList(new QueryWrapper<>());
//
//        if (localLiveBeans != null) {
//            return localLiveBeans.stream().filter(localLiveBean -> localLiveBean.getLiveType() != null)
//                    .filter(localLiveBean -> localLiveBean.getLiveType().equals(gameType))
//                    .map(localLiveBean -> {
//                        LiveItem.Item item = new LiveItem.Item();
//                        item.setLiveType(localLiveBean.getLiveType());
//                        item.setGameName(localLiveBean.getGameName());
//                        item.setLeftImg(localLiveBean.getLeftImg());
//                        item.setPlayid(localLiveBean.getId().toString());
//                        item.setLeftName(localLiveBean.getLeftName());
//                        item.setRightImg(localLiveBean.getRightImg());
//                        item.setRightName(localLiveBean.getRightName());
//                        try {
//                            item.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(localLiveBean.getDate()));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        item.setId(localLiveBean.getId());
//                        item.setIsTop(localLiveBean.getIsTop().equals("1"));
//                        item.setTitle(localLiveBean.getGameName());
//                        item.setIframeLink(toIframe(localLiveBean.getId()));
//                        return item;
//                    }).toList();
//        }
//        return null;
//    }

    public void requestData() {
        if (liveBeanRepository.findAll().size() != 0) {
            liveBeanRepository.setAllItemIsOld();
        }
        requestMainData();
        deleteOldItem();

    }

}
