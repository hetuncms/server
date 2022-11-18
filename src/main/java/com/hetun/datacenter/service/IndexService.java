package com.hetun.datacenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hetun.datacenter.Config;
import com.hetun.datacenter.bean.BaseBean;
import com.hetun.datacenter.bean.LiveItem;
import com.hetun.datacenter.bean.LocalLiveBean;
import com.hetun.datacenter.mapper.LocalLiveMapper;
import com.hetun.datacenter.net.NetInterface;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.tools.chrome.M3u8Sniff;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexService {

    private static final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded;");
    OkHttpClient okHttpClient = new OkHttpClient();
    LocalLiveMapper localLiveMapper;
    Config config;
    NetService netService;
    private NetInterface netInterface;

    @Autowired
    public IndexService(LocalLiveMapper localLiveMapper, Config config, NetService netService) {
        this.localLiveMapper = localLiveMapper;
        this.config = config;
        this.netService = netService;
        System.setProperty("webdriver.chrome.driver", config.getChromeDriverPath());

        netInterface = netService.getRetrofit().create(NetInterface.class);
    }

    @org.springframework.web.bind.annotation.ResponseBody
    public LiveItem requestData(Map<String, String> header, String requstbody) {
        LiveItem liveset;

        try {
            liveset = netInterface.index(requstbody).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
//            LiveItem.RelationT relationT = liveset.getT().get(item.getId());

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
        String iframeAddress = config.getLocalAddress() + "live_cms/" + id;
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
        Integer gameType = Integer.valueOf(requstbody.substring(requstbody.indexOf("a=") + 2, requstbody.indexOf("&g=")));

        if (liveItem != null && liveItem.getLive_item() != null) {
            liveItem.getLive_item().forEach(item -> {
                item.setIframeLink("");

                String leftimg = downloadTeamImg(item.getLeftImg());
                String rightImg = downloadTeamImg(item.getRightImg());
                item.setLeftImg(leftimg);
                item.setRightImg(rightImg);
            });


        }

        List<LocalLiveBean> localLiveBeans = localLiveMapper.selectList(new QueryWrapper<>());

        if (localLiveBeans != null && pager == 1 && liveItem != null) {
            localLiveBeans.stream().filter(localLiveBean -> localLiveBean.getLiveType() != null)
                    .filter(localLiveBean -> localLiveBean.getLiveType().equals(gameType))
                    .forEach(localLiveBean -> {
                        LiveItem.Item item = new LiveItem.Item();
                        item.setLiveType(localLiveBean.getLiveType());
                        item.setGameName(localLiveBean.getGameName());
                        item.setLeftImg(localLiveBean.getLeftImg());
                        item.setPlayid(localLiveBean.getId().toString());
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
                        if (liveItem.getLive_item() == null) {
                            ArrayList<LiveItem.Item> items = new ArrayList<>();
                            items.add(item);
                            liveItem.setLive_item(items);
                        } else {
                            liveItem.getLive_item().add(item);
                        }

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
    ResourceLoader resourceLoader;

    private static String TEAM_IMG_NAME = "team_img";

    private String downloadTeamImg(String img) {
        try {
            String fileName = img.substring(img.lastIndexOf("/") + 1);
            File staticDir = null;

            try {
                staticDir = new File(getClass().getResource("/static/"+TEAM_IMG_NAME).toURI());
            } catch (NullPointerException e) {
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            if (staticDir == null) {
                staticDir = new File(config.getStaticPath()+TEAM_IMG_NAME);
            }

            File file = new File(staticDir+"/"+fileName);

            ClassPathResource aStatic = new ClassPathResource("/");
            System.out.println("==="+aStatic.getFile().getAbsolutePath());

            if(!file.exists()){
                file.createNewFile();
                ResponseBody body = netInterface.downloadImg(img).execute().body();
                BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
                bufferedSink.writeAll(body.source());
                bufferedSink.close();
                System.out.println("=============net");
            }

            return config.getLocalAddress()+TEAM_IMG_NAME+"/"+fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        return config.getLocalAddress() + "live/" + id;
    }

    public String findCmsLiveById(Integer id) {
        QueryWrapper<LocalLiveBean> queryWrapper = new QueryWrapper<LocalLiveBean>().eq("id", id);
        LocalLiveBean localLiveBean = localLiveMapper.selectOne(queryWrapper);
        return localLiveBean.getLiveLink();
    }
}
