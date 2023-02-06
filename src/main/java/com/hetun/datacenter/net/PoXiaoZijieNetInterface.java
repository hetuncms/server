package com.hetun.datacenter.net;

import com.hetun.datacenter.bean.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PoXiaoZijieNetInterface {
    @GET("https://sports.dawnbyte.com/soccer/api/match")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieFootBallBean> getFootBallMatch(@Query("time_stamp") Integer time_stamp,@Query("begin_id") Integer begin_id,@Query("start_time_after") long start_time_after);

    @GET("https://sports.dawnbyte.com/basketball/api/match")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieBasketBallBean> getBasketBallMatch(@Query("time_stamp") Integer time_stamp,@Query("begin_id") Integer begin_id,@Query("start_time_after") long begin_time);

    @GET("https://sports.dawnbyte.com/soccer/api/team")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieFootBallTeamBean> getFootBallTeams(@Query("time_stamp") Integer time_stamp, @Query("begin_id") Integer beginId);


    @GET("https://sports.dawnbyte.com/basketball/api/team")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieBasketBallTeamBean> getBasketBallTeams(@Query("time_stamp") Integer time_stamp, @Query("begin_id") Integer beginId);

    @GET("https://sports.dawnbyte.com/soccer/api/live/video")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeFootballVideoOne(@Query("time_stamp") Integer time_stamp,@Query("begin_id") Integer begin_id,@Query("limit") Integer limit);

    @GET("https://sports.dawnbyte.com/basketball/api/live/video")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeBasketballVideoOne(@Query("time_stamp") Integer time_stamp,@Query("begin_id") Integer begin_id,@Query("limit") Integer limit);

    @GET("https://sports.dawnbyte.com/soccer/api/live/video")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeVideo(@Query("time_stamp") Integer time_stamp,@Query("is_streaming") Integer is_streaming,@Query("begin_id") Integer begin_id,@Query("limit") int limit);

    @GET("https://sports.dawnbyte.com/basketball/api/live/video")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieLiveInfoBean> getRealTimeBasketballVideo(@Query("time_stamp") Integer time_stamp,@Query("is_streaming") Integer is_streaming,@Query("begin_id") Integer begin_id,@Query("limit") int limit);

    @GET("https://sports.dawnbyte.com/rate/api/odds/details")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<RateOddsBean> getOddsDetails(@Query("time_stamp") Integer time_stamp,@Query("sport_id") Integer sportId,@Query("match_id") Integer matchId);
}
