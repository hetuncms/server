package com.hetun.datacenter.net;

import com.hetun.datacenter.bean.LiveItem;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface NetInterface {
    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8",
            "origin: http://www.515.tv",
            "host: www.515.tv",
            "X-Requested-With: XMLHttpRequest"})
    @POST("/")
    Call<LiveItem> index(@Body String requestBody);

    @GET
    Call<ResponseBody> downloadImg(@Url String url);

    @GET
    Call<ResponseBody> verifyVideoUrl(@Url String url);
}
