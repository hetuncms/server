package com.hetun.datacenter.tripartite.net;

import com.hetun.datacenter.tripartite.bean.FootballLeagueBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PoXiaoFootballNetInterface {
    @GET("/soccer/api/league")
    Call<FootballLeagueBean> getLeague(@Query("begin_id")int beginId);
}
