package com.hetun.datacenter.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MatchInterface {
    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8",
            "origin: api.515.tv",
            "host: www.515.tv",
            "X-Requested-With: XMLHttpRequest"})
    @POST("http://api.515.tv/match.php")
    Call<ResponseBody> match(@Body String requestBody);
}
