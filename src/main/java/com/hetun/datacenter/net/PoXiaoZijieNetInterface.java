package com.hetun.datacenter.net;

import com.hetun.datacenter.bean.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PoXiaoZijieNetInterface {
    @GET("https://sports.dawnbyte.com/soccer/api/match")
    Call<PoXiaoZiJieFootBallBean> getFootBallMatch(@Query("begin_id") Integer begin_id, @Query("start_time_after") long start_time_after, @Query("start_time_before") long start_time_before);

    @GET("https://sports.dawnbyte.com/basketball/api/match")
    Call<PoXiaoZiJieBasketBallBean> getBasketBallMatch(@Query("begin_id") Integer begin_id, @Query("start_time_after") long start_time_after, @Query("start_time_before") long start_time_before);

    @GET("https://sports.dawnbyte.com/soccer/api/team")
    Call<PoXiaoZiJieFootBallTeamBean> getFootBallTeams(@Query("begin_id") Integer beginId);


    @GET("https://sports.dawnbyte.com/basketball/api/team")
    Call<PoXiaoZiJieBasketBallTeamBean> getBasketBallTeams(@Query("begin_id") Integer beginId);

    @GET("https://sports.dawnbyte.com/soccer/api/live/video")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeFootballVideoOne(@Query("begin_id") Integer begin_id, @Query("limit") Integer limit);

    @GET("https://sports.dawnbyte.com/basketball/api/live/video")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeBasketballVideoOne(@Query("begin_id") Integer begin_id, @Query("limit") Integer limit);

    @GET("https://sports.dawnbyte.com/soccer/api/live/video")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeVideo(@Query("is_streaming") Integer is_streaming, @Query("begin_id") Integer begin_id, @Query("limit") int limit);

    @GET("https://sports.dawnbyte.com/basketball/api/live/video")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeBasketballVideo(@Query("is_streaming") Integer is_streaming, @Query("begin_id") Integer begin_id, @Query("limit") int limit);

    @GET("https://sports.dawnbyte.com/rate/api/odds/details")
    Call<RateOddsBean> getOddsDetails(@Query("sport_id") Integer sportId, @Query("match_id") Integer matchId);
}
