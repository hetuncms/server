package com.hetun.datacenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hetun.datacenter.bean.BaseBean;
import com.hetun.datacenter.bean.LiveItem;
import com.hetun.datacenter.bean.LocalLiveBean;
import com.hetun.datacenter.mapper.LocalLiveMapper;
import com.hetun.datacenter.tools.ServerConfig;
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
    @Autowired
    public IndexService(LocalLiveMapper localLiveMapper,ServerConfig serverConfig) {
        this.localLiveMapper = localLiveMapper;
        this.serverConfig = serverConfig;
    }

    @org.springframework.web.bind.annotation.ResponseBody
    public LiveItem requestData(Map<String, String> header,String requstbody) {
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
            return null;
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
        String iframeAddress = serverConfig.getUrl()+"/"+id;
        return iframeAddress;
    }

    public LocalLiveBean findLiveById(Integer id){
        return localLiveMapper.selectById(id);
    }
    public BaseBean<Integer> insertLiveStream(LocalLiveBean localLiveBean){
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
        List<LocalLiveBean> localLiveBeans = localLiveMapper.selectList(new QueryWrapper<>());
        if (localLiveBeans != null && pager==1 && liveItem!=null && liveItem.getLive_item()!=null) {
            localLiveBeans.forEach(localLiveBean -> {
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
                        filter(item1 -> !item1.getIsTop().equals("1")).collect(Collectors.toList());
                top.addAll(noral);
                liveItem.setLive_item(top);

            });
        }

        return liveItem;
    }
}
