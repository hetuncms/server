package com.hetun.datacenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hetun.datacenter.Config;
import com.hetun.datacenter.bean.BaseBean;
import com.hetun.datacenter.bean.LiveItem;
import com.hetun.datacenter.bean.LocalLiveBean;
import com.hetun.datacenter.mapper.LocalLiveMapper;
import com.hetun.datacenter.tools.ServerConfig;
import com.hetun.datacenter.tools.chrome.M3u8Sniff;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexService {
    ServerConfig serverConfig;

    private static final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded;");
    OkHttpClient okHttpClient = new OkHttpClient();
    LocalLiveMapper localLiveMapper;
    Config config;

    @Autowired
    public IndexService(LocalLiveMapper localLiveMapper, ServerConfig serverConfig, Config config) {
        this.localLiveMapper = localLiveMapper;
        this.serverConfig = serverConfig;
        this.config = config;

        System.setProperty("webdriver.chrome.driver", config.getChromeDriverPath());

    }

    @org.springframework.web.bind.annotation.ResponseBody
    public LiveItem requestData(Map<String, String> header, String requstbody) {
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, requstbody);
        Headers headers = Headers.of(header);
        System.out.println(requstbody);
        Request build = new Request.Builder().url("http://www.515.tv").headers(headers).post(body).build();

        Response execute;
        String netData;
        try {
            execute = okHttpClient.newCall(build).execute();
            netData = execute.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LiveItem liveset = com.alibaba.fastjson2.JSON.parseObject(netData, LiveItem.class);
        if (liveset == null) {
            return null;
        }
        List<LiveItem.Item> live_item = liveset.getLive_item();
        if (live_item == null || liveset.getInfo() != null) {
            if (liveset.getInfo() == null || liveset.getInfo().isEmpty()) {
                liveset.setInfo("未知错误");
            }
            liveset.setStatus(-1);
            return liveset;
        }

        live_item.forEach(item -> {
            item.setIframeLink("http://www.515.tv/live/" + item.getPlayid() + "?rel=0&amp&autoplay=1");
            LiveItem.RelationT relationT = liveset.getT().get(item.getId());

//            if (relationT != null) {
//                item.setMatchId(relationT.getI());
//                item.setLid(relationT.getL());
//            }

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

    private String toIframe(Integer id) {
        String iframeAddress = serverConfig.getUrl() + "/" + id;
        return iframeAddress;
    }

    public String findLiveById(Integer id) {
        LocalLiveBean localLiveBean = localLiveMapper.selectById(id);
        if (localLiveBean != null) {
            return localLiveBean.getLiveLink();
        }
        return getVideoUrl(String.valueOf(id));
    }

    public BaseBean<Integer> insertLiveStream(LocalLiveBean localLiveBean) {
        String date = localLiveBean.getDate();
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return new BaseBean.Builder().buildError("date error");
        }
        int insert = localLiveMapper.insert(localLiveBean);
        return new BaseBean.Builder().build(insert);
    }

    public BaseBean<Integer> deleteLiveStream(LocalLiveBean localLiveBean) {
        int delete = localLiveMapper.deleteById(localLiveBean);
        return new BaseBean.Builder().build(delete);
    }

    public List<LocalLiveBean> getLocalStream() {
        return localLiveMapper.selectList(new QueryWrapper<>());
    }

    public LiveItem getIndex(Map<String, String> header, String requstbody) {
        LiveItem liveItem = requestData(header, requstbody);
        Integer pager = Integer.valueOf(requstbody.substring(requstbody.indexOf("g=") + 2));
        Integer gameType = Integer.valueOf(requstbody.substring(requstbody.indexOf("a=") + 2,requstbody.indexOf("&g=")));

        if (liveItem != null && liveItem.getLive_item() != null) {
            liveItem.getLive_item().forEach(item -> item.setIframeLink(config.getLocalAddress() + item.getId()));
        }

        List<LocalLiveBean> localLiveBeans = localLiveMapper.selectList(new QueryWrapper<>());
        if (localLiveBeans != null && pager == 1 && liveItem != null && liveItem.getLive_item() != null) {
            localLiveBeans.stream().filter(localLiveBean -> localLiveBean.getGameType()!=null)
            .filter(localLiveBean -> localLiveBean.getGameType().equals(gameType))
                    .forEach(localLiveBean -> {
                        LiveItem.Item item = new LiveItem.Item();
                        item.setGameType(localLiveBean.getGameType());
                        item.setGameName(localLiveBean.getGameName());
                        item.setLeftImg(localLiveBean.getLeftImg());
                        item.setLeftName(localLiveBean.getLeftName());
                        item.setRightImg(localLiveBean.getRightImg());
                        item.setRightName(localLiveBean.getRightName());
                        try {
                            item.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(localLiveBean.getDate()));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        item.setId(localLiveBean.getId());
                        item.setIsTop(localLiveBean.getIsTop());
                        item.setTitle(localLiveBean.getGameName());
                        item.setIframeLink(toIframe(localLiveBean.getId()));
                        liveItem.getLive_item().add(item);

                        // 筛选置顶
                        List<LiveItem.Item> top = liveItem.getLive_item().stream().
                                filter(item1 -> item1.getIsTop().equals("1")).collect(Collectors.toList());
                        List<LiveItem.Item> noral = liveItem.getLive_item().stream().
                                filter(item1 -> !item1.getIsTop().equals("1")).toList();
                        top.addAll(noral);
                        liveItem.setLive_item(top);
                    });
        }


        return liveItem;
    }

    @Autowired
    M3u8Sniff m3u8Sniff;

    public String getVideoUrl(String id) {
//        Future<String> submit = executorService.submit(() -> getM3u8Url("http://www.515.tv/live/" + id));
//        try {
//            String o = submit.get(5, TimeUnit.SECONDS);
//            return o;
//        } catch (InterruptedException | ExecutionException | TimeoutException e) {
//            return "";
//        } finally {
//            submit.cancel(true);
//        }
        return m3u8Sniff.getM3u8Url("http://www.515.tv/live/" + id);
    }


    public String getIframeLinkById(String id) {
        return config.getLocalAddress() + "/bofang/" + id;
    }
}
