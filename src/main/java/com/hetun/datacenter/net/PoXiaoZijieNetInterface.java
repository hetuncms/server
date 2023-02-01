package com.hetun.datacenter.net;

import com.hetun.datacenter.bean.PoXiaoZiJieBasketBallTeamBean;
import com.hetun.datacenter.bean.PoXiaoZiJieBasketBallBean;
import com.hetun.datacenter.bean.PoXiaoZiJieFootBallBean;
import com.hetun.datacenter.bean.PoXiaoZiJieFootBallTeamBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PoXiaoZijieNetInterface {
    @GET("https://sports.dawnbyte.com/soccer/api/match")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieFootBallBean> getFootBallMatch(@Query("time_stamp") long time_stamp,@Query("begin_time") long begin_time);

    @GET("https://sports.dawnbyte.com/basketball/api/match")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieBasketBallBean> getBasketBallMatch(@Query("time_stamp") long time_stamp,@Query("start_time_after") long begin_time);

    @GET("https://sports.dawnbyte.com/soccer/api/team")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieFootBallTeamBean> getFootBallTeams(@Query("time_stamp") long time_stamp, @Query("begin_id") Integer beginId);


    @GET("https://sports.dawnbyte.com/basketball/api/team")
    @Headers("token:s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo")
    Call<PoXiaoZiJieBasketBallTeamBean> getBasketBallTeams(@Query("time_stamp") long time_stamp, @Query("begin_id") Integer beginId);
}
