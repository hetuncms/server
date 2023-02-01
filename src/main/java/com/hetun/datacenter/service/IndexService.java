package com.hetun.datacenter.service;

import com.hetun.datacenter.Config;
import com.hetun.datacenter.bean.BaseBean;
import com.hetun.datacenter.bean.LiveBean;
import com.hetun.datacenter.bean.LiveItem;
import com.hetun.datacenter.net.NetInterface;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.repository.LiveBeanRepository;
import com.hetun.datacenter.repository.TripartiteLiveBeanRepository;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class IndexService {

    private static final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded;");
    private final TripartiteLiveBeanRepository tripartiteLiveBeanRepository;
    private final LiveBeanRepository liveBeanRepository;
    Config config;
    NetService netService;
    private NetInterface netInterface;

    @Autowired
    public IndexService(Config config, NetService netService,
                        TripartiteLiveBeanRepository tripartiteLiveBeanRepository,
                        LiveBeanRepository liveBeanRepository) {
        this.config = config;
        this.netService = netService;
        this.tripartiteLiveBeanRepository = tripartiteLiveBeanRepository;
        this.liveBeanRepository = liveBeanRepository;
        netInterface = netService.getRetrofit().create(NetInterface.class);
    }


    private String toIframe(Integer id) {
        String iframeAddress = config.getLocalAddress() + "live_cms/" + id;
        return iframeAddress;
    }

//    public String findLiveById(Integer id) {
//        LocalLiveBean localLiveBean = localLiveMapper.selectById(id);
//        if (localLiveBean != null) {
//            return localLiveBean.getLiveLink();
//        }
//
//
//        return "test";
//    }
//
//    public BaseBean<Integer> insertLiveStream(LocalLiveBean localLiveBean) {
//        String date = localLiveBean.getDate();
//        try {
//            new SimpleDateFormat("yyyy-MM-dd").parse(date);
//        } catch (ParseException e) {
//            return new BaseBean.Builder().buildError("date error");
//        }
//        int insert = localLiveMapper.insert(localLiveBean);
//        return new BaseBean.Builder().build(insert);
//    }
//
//    public BaseBean<Integer> deleteLiveStream(LocalLiveBean localLiveBean) {
//        int delete = localLiveMapper.deleteById(localLiveBean);
//        return new BaseBean.Builder().build(delete);
//    }
//
//    public List<LocalLiveBean> getLocalStream() {
//        return localLiveMapper.selectList(new QueryWrapper<>());
//    }

    public static final int PAGE_SIZE = 10;

    public LiveBean getIndex(String requstbody) {
        Integer liveType = Integer.valueOf(requstbody.substring(requstbody.indexOf("a=") + 2, requstbody.indexOf("&g=")));

        Integer pager = Integer.valueOf(requstbody.substring(requstbody.indexOf("g=") + 2));
        PageRequest of = PageRequest.of(pager, 10);
        Page<LiveItem> all;
        if (liveType == 0) {
            all = liveBeanRepository.findAllUp(of);
        } else {
            all = liveBeanRepository.findAllBySportUp(of, liveType);
        }
        List<LiveItem> content = all.getContent();
        LiveBean liveBean = new LiveBean();
        liveBean.setLive_item(content);

//        if (content != null) {
//            content.forEach(item -> {
//                String leftimg = downloadTeamImg(item.getLeftImg());
//                String rightImg = downloadTeamImg(item.getRightImg());
//                item.setLeftImg(leftimg);
//                item.setRightImg(rightImg);
//            });
//        }
        return liveBean;
    }

    public BaseBean<List<LiveItem>> getAllIndex() {
        List<LiveItem> all = liveBeanRepository.findAll();
        return new BaseBean.Builder().build(all);
    }

    @Autowired
    ResourceLoader resourceLoader;

    private static String TEAM_IMG_NAME = "team_img";

    private String downloadTeamImg(String img) {
        try {
            if (img == null) {
                return null;
            }
            String fileName = img.substring(img.lastIndexOf("/") + 1);
            File staticDir = null;

            try {
                staticDir = new File(getClass().getResource("/static/" + TEAM_IMG_NAME).toURI());
            } catch (NullPointerException e) {
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            if (staticDir == null) {
                staticDir = new File(config.getStaticPath() + TEAM_IMG_NAME);
            }

            File file = new File(staticDir + "/" + fileName);

            ClassPathResource aStatic = new ClassPathResource("/");

            if (!file.exists()) {
                file.createNewFile();
                ResponseBody body = netInterface.downloadImg(img).execute().body();
                if (body == null) {
                    return "";
                }
                BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
                bufferedSink.writeAll(body.source());
                bufferedSink.close();
            }

            return config.getLocalAddress() + TEAM_IMG_NAME + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getIframeLinkById(String id) {
        return config.getLocalAddress() + "live/" + id;
    }

//    public String findCmsLiveById(Integer id) {
//        QueryWrapper<LocalLiveBean> queryWrapper = new QueryWrapper<LocalLiveBean>().eq("id", id);
//        LocalLiveBean localLiveBean = localLiveMapper.selectOne(queryWrapper);
//        return localLiveBean.getLiveLink();
//    }

    public LiveItem getLiveItem(Long id) {
        return liveBeanRepository.findAllById(id);
    }

    public BaseBean<Integer> updateLiveItem(LiveItem liveItem) {
        LiveItem byLiveId = liveBeanRepository.findByLiveId(liveItem.getLiveId());
        liveItem.setId(byLiveId.getId());
        liveBeanRepository.save(liveItem);
        return new BaseBean.Builder().build();
    }
}
