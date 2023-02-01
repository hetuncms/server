package com.hetun.datacenter.net;

import com.hetun.datacenter.bean.AbcBean;
import com.hetun.datacenter.bean.MainLiveBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface MainDataInterface {

    @GET("http://5481fb3b9cac46e1.api.sstream365.net?type=json")
    Call<List<MainLiveBean>> requestMainData();
    @GET("https://api.acb.bet/sport/match/getMatchList")
    Call<AbcBean> requestList(@Query("date") String date,@Query("sportId") String  type);
}
