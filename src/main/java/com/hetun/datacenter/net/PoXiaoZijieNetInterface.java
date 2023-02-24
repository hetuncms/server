package com.hetun.datacenter.net;

import com.hetun.datacenter.bean.*;
import com.hetun.datacenter.tripartite.bean.BaseNetBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PoXiaoZijieNetInterface {
    @GET("/soccer/api/match")
    Call<PoXiaoZiJieFootBallBean> getFootBallMatch(@Query("begin_id") Integer begin_id, @Query("start_time_after") long start_time_after, @Query("start_time_before") long start_time_before);

    @GET("/basketball/api/match")
    Call<PoXiaoZiJieBasketBallBean> getBasketBallMatch(@Query("begin_id") Integer begin_id, @Query("start_time_after") long start_time_after, @Query("start_time_before") long start_time_before);

    @GET("/soccer/api/team")
    Call<PoXiaoZiJieFootBallTeamBean> getFootBallTeams(@Query("begin_id") Integer beginId);


    @GET("/basketball/api/team")
    Call<PoXiaoZiJieBasketBallTeamBean> getBasketBallTeams(@Query("begin_id") Integer beginId);

    @GET("/soccer/api/live/video")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeFootballVideoOne(@Query("begin_id") Integer begin_id, @Query("limit") Integer limit);

    @GET("/basketball/api/live/video")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeBasketballVideoOne(@Query("begin_id") Integer begin_id, @Query("limit") Integer limit);

    @GET("/soccer/api/live/video")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeVideo(@Query("is_streaming") Integer is_streaming, @Query("begin_id") Integer begin_id, @Query("limit") int limit);

    @GET("/basketball/api/live/video")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeBasketballVideo(@Query("is_streaming") Integer is_streaming, @Query("begin_id") Integer begin_id, @Query("limit") int limit);

    @GET("/rate/api/odds/details")
    Call<BaseNetBean<RateOddsBean.Result>> getOddsDetails(@Query("sport_id") Integer sportId, @Query("match_id") Integer matchId);

    @GET("/soccer/api/team")
    Call<PoXiaoZiJieFootBallTeamBean> getFootBallTeam(@Query("begin_id") Integer beginId,@Query("limit") int limit);

    @GET("/basketball/api/team")
    Call<PoXiaoZiJieBasketBallTeamBean> getBasketBallTeam(@Query("begin_id") Integer beginId,@Query("limit") int limit);
}
