package com.hetun.datacenter.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hetun.datacenter.Config;
import com.hetun.datacenter.bean.LiveBean;
import com.hetun.datacenter.bean.LiveItem;
import com.hetun.datacenter.bean.MainLiveBean;
import com.hetun.datacenter.net.NetInterface;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.repository.LiveBeanRepository;
import com.hetun.datacenter.repository.TripartiteLiveBeanRepository;
import okio.Okio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class DataService {
    private final TripartiteLiveBeanRepository tripartiteLiveBeanRepository;
    private final LiveBeanRepository liveBeanRepository;
    private final NetInterface netInterface;
    Config config;
    NetService netService;

    @Autowired
    public DataService(TripartiteLiveBeanRepository tripartiteLiveBeanRepository, LiveBeanRepository liveBeanRepository, Config config, NetService netService) {
        this.tripartiteLiveBeanRepository = tripartiteLiveBeanRepository;
        this.liveBeanRepository = liveBeanRepository;
        this.config = config;
        this.netService = netService;
        netInterface = netService.getRetrofit().create(NetInterface.class);
    }

    public String download515Data() {
        for (int i = 0; i < 3; i++) {
            String requestBody = "s=0&t=1&a=" + i;
            int page = 1;

            System.out.println("下载" + i + "==============");

            while (true) {
                LiveItem liveItem = request515Data(requestBody + "&g=" + page);

                if (liveItem == null) {
                    break;
                }

                List<LiveItem.Item> live_item = liveItem.getLive_item();

                if (live_item == null) {
                    break;
                }

                int finalI = i;
                live_item.stream().map(item -> {
                    LiveBean.Item liveBeanItem = new LiveBean.Item();
                    liveBeanItem.setLongTime(item.getDate().getTime());
                    if (finalI == 0) {
                        liveBeanItem.setHot(true);
                    }
                    liveBeanItem.setLiveType(item.getLiveType());
                    liveBeanItem.setTitle(item.getTitle());

                    liveBeanItem.setTop(item.getIsTop());
//                    liveBeanItem.setPlayid(item.getPlayid());

                    liveBeanItem.setLeftImg(item.getLeftImg());
                    liveBeanItem.setRightImg(item.getRightImg());

                    liveBeanItem.setRightName(item.getRightName());
                    liveBeanItem.setLeftName(item.getLeftName());

                    liveBeanItem.setMatchId(item.getMatchId());
                    liveBeanItem.setLiveSource("515");
                    liveBeanItem.setOld(false);

                    // 查找此赛程是否存在本地sql
                    liveBeanItem.setLiveId(item.getLeftName() + item.getRightName() + item.getDate().getTime() + liveBeanItem.getLiveSource());
                    updateItem(liveBeanItem);

                    return liveBeanItem;
                }).toList();

                System.out.println("下载" + i + "===g:" + page);
                page++;
            }
        }
        return "";
    }

    public LiveItem request515Data(String requstbody) {
        LiveItem liveset;
        try {
            liveset = netInterface.index(requstbody).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (liveset == null || liveset.getLive_item() == null || liveset.getInfo() != null) {
            return null;
        }

        List<LiveItem.Item> live_item = liveset.getLive_item();
        live_item.forEach(item -> {
            LiveItem.RelationT relationT = liveset.getT().get(String.valueOf(item.getId()));

            if (relationT != null) {
                item.setMatchId(relationT.getI());
            }


            LiveItem.RelationA leftRelationA = liveset.getA().get(item.getI());

            if (leftRelationA != null) {
                item.setLeftImg(leftRelationA.getN());
                item.setLeftName(leftRelationA.getI());
            }

            LiveItem.RelationA rightRelationA = liveset.getA().get(item.getC());

            if (rightRelationA != null) {
                item.setRightName(rightRelationA.getI());
                item.setRightImg(rightRelationA.getN());
            }

            LiveItem.RelationO relationO = liveset.getO().get(item.getH());

            if (relationO != null) {
                item.setGameName(relationO.getI());
            }
        });

        return liveset;
    }

    public List<LiveItem.Item> requestMainData() {
        JSONArray platformArray;
        try {
            platformArray = com.alibaba.fastjson2.JSON.parseArray(Okio.buffer(Okio.source(new File("E:\\project\\DataCenter\\src\\main\\resources\\test.json"))).readUtf8());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Object jsonObject : platformArray) {
            MainLiveBean mainLiveBean = JSONObject.parseObject(jsonObject.toString(), MainLiveBean.class);
            LiveBean.Item netItem = tripartiteLiveBeanToLiveBean(mainLiveBean);
            updateItem(netItem);
        }
        return null;
    }

    private void updateItem(LiveBean.Item item) {
        LiveBean.Item sqlItem = liveBeanRepository.findAllByLiveId(item.getLiveId());
        if (sqlItem != null) {
            item.setId(sqlItem.getId());
        } else {
            item.setUpDataCount(1L);
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

    LiveBean.Item tripartiteLiveBeanToLiveBean(MainLiveBean mainLiveBean) {
        LiveBean.Item item = new LiveBean.Item();
        item.setLeftName(mainLiveBean.getOpp1());
        item.setRightName(mainLiveBean.getOpp2());
        item.setTop(false);
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
//        liveBeanRepository.deleteAll();
        download515Data();
//        Integer pager = Integer.valueOf(requstbody.substring(requstbody.indexOf("g=") + 2));
//        Integer gameType =
//                Integer.valueOf(requstbody.substring(requstbody.indexOf("a=") + 2, requstbody.indexOf("&g=")));
        int pager = 1;
        int gameType = 1;
        if (pager == 1) {
//            List<LiveItem.Item> items = requestLocalData(gameType);
//            liveItemList.addAll(items);
        }

        List<LiveItem.Item> items = requestMainData();
        deleteOldItem();

//        liveItemList.addAll(items);


//        List<LiveItem.Item> top = liveItemList.stream().filter(LiveItem.Item::getIsTop).toList();
//        List<LiveItem.Item> noTop = liveItemList.stream().filter(item -> !item.getIsTop()).toList();
//
//        List<LiveItem.Item> topSorted = new ArrayList<>(top.stream().sorted(Comparator.comparing(LiveItem.Item::getDate)).toList());
//        List<LiveItem.Item> noTopsorted = noTop.stream().sorted(Comparator.comparing(LiveItem.Item::getDate)).toList();
//
//        topSorted.addAll(noTopsorted);

    }

}
