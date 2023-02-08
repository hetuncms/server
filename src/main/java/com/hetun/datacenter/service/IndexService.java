package com.hetun.datacenter.service;

import com.hetun.datacenter.Config;
import com.hetun.datacenter.bean.*;
import com.hetun.datacenter.net.NetInterface;
import com.hetun.datacenter.net.NetService;
import com.hetun.datacenter.net.PoXiaoZijieNetInterface;
import com.hetun.datacenter.repository.LiveBeanRepository;
import com.hetun.datacenter.repository.PoXiaoLiveInfoRepository;
import com.hetun.datacenter.repository.RateOddsRepository;
import com.hetun.datacenter.repository.TripartiteLiveBeanRepository;
import com.hetun.datacenter.tripartite.bean.FootballLeagueBean;
import com.hetun.datacenter.tripartite.bean.RateOddsCompanyBean;
import com.hetun.datacenter.tripartite.repository.LeagueRepository;
import com.hetun.datacenter.tripartite.repository.RateOddsCompanyRepository;
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
import java.util.Optional;

@Service
public class IndexService {

    public static final int PAGE_SIZE = 10;
    private static final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded;");
    private static final String TEAM_IMG_NAME = "team_img";
    private final TripartiteLiveBeanRepository tripartiteLiveBeanRepository;
    private final LiveBeanRepository liveBeanRepository;
    private final PoXiaoLiveInfoRepository poXiaoLiveInfoRepository;
    private final PoXiaoZijieNetInterface poXiaoZijieNetInterface;
    private final NetInterface netInterface;
    private final RateOddsRepository rateOddsRepository;
    private final RateOddsService rateOddsService;
    private final LeagueRepository leagueRepository;
    private final RateOddsCompanyRepository rateOddsCompanyRepository;
    Config config;
    NetService netService;
    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    public IndexService(Config config, NetService netService, LeagueRepository leagueRepository, RateOddsService rateOddsService,
                        RateOddsCompanyRepository rateOddsCompanyRepository,
                        RateOddsRepository rateOddsRepository, TripartiteLiveBeanRepository tripartiteLiveBeanRepository, PoXiaoLiveInfoRepository poXiaoLiveInfoRepository, LiveBeanRepository liveBeanRepository) {
        this.config = config;
        this.netService = netService;
        this.tripartiteLiveBeanRepository = tripartiteLiveBeanRepository;
        this.poXiaoLiveInfoRepository = poXiaoLiveInfoRepository;
        this.rateOddsRepository = rateOddsRepository;
        this.rateOddsCompanyRepository = rateOddsCompanyRepository;
        this.liveBeanRepository = liveBeanRepository;
        this.leagueRepository = leagueRepository;
        this.rateOddsService = rateOddsService;
        netInterface = netService.getRetrofit().create(NetInterface.class);
        this.poXiaoZijieNetInterface = netService.getRetrofit().create(PoXiaoZijieNetInterface.class);
    }

    private String toIframe(Integer id) {
        String iframeAddress = config.getLocalAddress() + "live_cms/" + id;
        return iframeAddress;
    }

    boolean getLiveing(Integer matchId) {
        // 更新直播状态
        return poXiaoLiveInfoRepository.findByMatchId(matchId) != null;
    }

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

        for (LiveItem liveItem : content) {
            liveItem.setLiveing(getLiveing(liveItem.getId()));
        }
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

    public FootballPlayInfoBean getPlayInfo(Integer matchId) {
        FootballPlayInfoBean playInfoBean = new FootballPlayInfoBean();
        playInfoBean.setCode(20000);

        LiveItem liveItem = liveBeanRepository.findByMatchId(matchId);

        Optional<FootballLeagueBean.Result> leagueOp = leagueRepository.findById(liveItem.getLeagueId());
        if (leagueOp.isPresent()) {
            FootballLeagueBean.Result footballLeague = leagueOp.get();
            playInfoBean.setFootballLeague(footballLeague);
        }

        List<RateOddsBean.Result> rateOdds = rateOddsService.getRateOdds(matchId);

        if (rateOdds != null && !rateOdds.isEmpty()) {

            for (RateOddsBean.Result rateOdd : rateOdds) {
                for (RateOddsBean.Result.OddsItem oddsItem : rateOdd.getList()) {
                    Optional<RateOddsCompanyBean.Result> byId = rateOddsCompanyRepository.findById(oddsItem.getCompany_id());
                    oddsItem.setCompanyName(byId.get().getName_zh());
                }
            }
            playInfoBean.setOddsItem(rateOdds);
        }



        Optional<PoXiaoZiJieLiveInfoBean.Result> byId1 = poXiaoLiveInfoRepository.findById(matchId);

        if (!byId1.isEmpty()) {
            PoXiaoZiJieLiveInfoBean.Result result = byId1.get();
            playInfoBean.setLiveInfoBean(result);
        }

        playInfoBean.setData(liveItem);
        return playInfoBean;
    }

    public BaseBean<List<LiveItem>> getAllIndex() {
        List<LiveItem> all = liveBeanRepository.findAll();
        return new BaseBean.Builder().build(all);
    }

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
