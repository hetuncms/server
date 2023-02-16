package com.hetun.datacenter.tripartite.net;

import com.hetun.datacenter.tripartite.bean.LeagueBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PoXiaoNetInterface {
    @GET("/soccer/api/league")
    Call<LeagueBean> getFootballLeague(@Query("begin_id")int beginId);

    @GET("/basketball/api/league")
    Call<LeagueBean> getBasketballLeague(@Query("begin_id")int beginId);
}
