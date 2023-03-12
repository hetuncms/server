package com.hetun.datacenter.service;

import com.hetun.datacenter.bean.*;
import com.hetun.datacenter.repository.LiveBeanRepository;
import com.hetun.datacenter.repository.PoXiaoLiveInfoRepository;
import com.hetun.datacenter.repository.RateOddsRepository;
import com.hetun.datacenter.tripartite.bean.RateOddsCompanyBean;
import com.hetun.datacenter.tripartite.repository.LeagueRepository;
import com.hetun.datacenter.tripartite.repository.RateOddsCompanyRepository;
import okhttp3.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IndexService {

    public static final int PAGE_SIZE = 10;
    private static final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded;");
    private static final String TEAM_IMG_NAME = "team_img";
    private final LiveBeanRepository liveBeanRepository;
    private final PoXiaoLiveInfoRepository poXiaoLiveInfoRepository;
    private final RateOddsRepository rateOddsRepository;
    private final RateOddsService rateOddsService;
    private final LeagueRepository leagueRepository;
    private final RateOddsCompanyRepository rateOddsCompanyRepository;

    @Autowired
    public IndexService(
            LeagueRepository leagueRepository,
            RateOddsService rateOddsService,
            RateOddsCompanyRepository rateOddsCompanyRepository,
            RateOddsRepository rateOddsRepository,
            PoXiaoLiveInfoRepository poXiaoLiveInfoRepository,
            LiveBeanRepository liveBeanRepository) {
        this.poXiaoLiveInfoRepository = poXiaoLiveInfoRepository;
        this.rateOddsRepository = rateOddsRepository;
        this.rateOddsCompanyRepository = rateOddsCompanyRepository;
        this.liveBeanRepository = liveBeanRepository;
        this.leagueRepository = leagueRepository;
        this.rateOddsService = rateOddsService;

    }

    boolean getLiveing(Integer matchId) {
        // 更新直播状态
        return poXiaoLiveInfoRepository.findByMatchId(matchId) != null;
    }

    public List<Date> getAllDate(Integer type){
        if (type.equals(0)) {
            return liveBeanRepository.getAllDate();
        }else{
            return liveBeanRepository.getAllDate(type);
        }
    }

    public BaseListBean<List<LiveItem>> getIndex(Integer liveType,String date, Integer pager, Integer limit) {
        if (limit == null) {
            limit = 10;
        }else if(limit.equals(-1)) {
            limit = Integer.MAX_VALUE;
        }
        PageRequest of = PageRequest.of(pager, limit);
        Page<LiveItem> all;
        Date currentShowDate = null;
        if (StringUtils.hasText(date)) {
            try {
                currentShowDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        if (liveType == 0) {
            if (currentShowDate != null) {
                all = liveBeanRepository.findAllUp(of,currentShowDate);
            }else{
                all = liveBeanRepository.findAllUp(of);
            }
        } else {
            if (currentShowDate != null) {
                all = liveBeanRepository.findAllBySportByDatePager(of,currentShowDate, liveType);
            }else{
                all = liveBeanRepository.findAllBySportPager(of,liveType);
            }
        }
        int totalPages = all.getTotalPages();
        if (pager > totalPages) {
            return new BaseListBean.Builder().buildEmptyError(totalPages);
        }
        List<LiveItem> content = all.getContent();

        for (LiveItem liveItem : content) {
            liveItem.setLiveing(getLiveing(liveItem.getId()));
        }

        LiveBean liveBean = new LiveBean();
        liveBean.setLive_item(content);
        BaseListBean<List<LiveItem>> build = new BaseListBean.Builder().build(liveBean.getLive_item(), totalPages);
        return build;
    }

    public FootballPlayInfoBean getPlayInfo(Integer matchId) {
        FootballPlayInfoBean playInfoBean = new FootballPlayInfoBean();
        playInfoBean.setCode(20000);

        LiveItem liveItem = liveBeanRepository.findByMatchId(matchId);

//        Optional<LeagueBean.LeagueResult> leagueOp = leagueRepository.findById(liveItem.getLeagueId());
//        if (leagueOp.isPresent()) {
//            LeagueBean.LeagueResult league = leagueOp.get();
//            playInfoBean.setFootballLeague(league);
//        }

        List<RateOddsBean.Result> rateOdds = rateOddsService.getRateOdds(matchId);

        if (rateOdds != null && !rateOdds.isEmpty()) {
            for (RateOddsBean.Result rateOdd : rateOdds) {
                for (RateOddsBean.Result.OddsItem oddsItem : rateOdd.getList()) {
                    Optional<RateOddsCompanyBean.Result> rateOddsCompanyOptional = rateOddsCompanyRepository.findById(oddsItem.getCompany_id());
                    rateOddsCompanyOptional.ifPresent(result -> oddsItem.setCompanyName(result.getName_zh()));
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


//    public String findCmsLiveById(Integer id) {
//        QueryWrapper<LocalLiveBean> queryWrapper = new QueryWrapper<LocalLiveBean>().eq("id", id);
//        LocalLiveBean localLiveBean = localLiveMapper.selectOne(queryWrapper);
//        return localLiveBean.getLiveLink();
//    }

    public LiveItem getLiveItem(Long id) {
        return liveBeanRepository.findAllById(id);
    }

    public BaseBean<Integer> updateLiveItem(LiveItem liveItem) {
        liveBeanRepository.save(liveItem);
        return new BaseBean.Builder().build();
    }

}
