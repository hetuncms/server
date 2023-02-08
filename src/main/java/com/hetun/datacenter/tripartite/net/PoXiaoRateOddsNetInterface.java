package com.hetun.datacenter.tripartite.net;

import com.hetun.datacenter.tripartite.bean.RateOddsCompanyBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PoXiaoRateOddsNetInterface {
    @GET("/rate/api/odds/company")
    Call<RateOddsCompanyBean> getRateCompany(@Query("sport_id")int sportId);
}
