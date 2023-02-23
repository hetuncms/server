package com.hetun.datacenter.tripartite.net;

import com.hetun.datacenter.tools.DateUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ArgsInfoInterceptor implements Interceptor {


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newRequestBuilder = request.newBuilder();

        //为Header设置公共参数
        newRequestBuilder.addHeader("token", "s08cEJ61gYE4VZqolo3S02c6v1Ne9F7ZKv1eR5mqn6Rhw5VLfo");

        //为Url设置公共参数
        HttpUrl url = request.url().newBuilder().addQueryParameter("time_stamp", String.valueOf(DateUtils.now())).build();
        newRequestBuilder.url(url);

//        RequestBody body = request.body();
//        为Post表单请求设置公共参数
//        if (body instanceof FormBody) {
//            FormBody formBody = (FormBody) body;
//            FormBody.Builder formBodyBuilder = new FormBody.Builder();
//            for (int i = 0; i < formBody.size(); i++) {
//                formBodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
//            }
//            //公共参数设置处
//            formBodyBuilder.add("token", "123456");
//            newRequestBuilder.post(formBodyBuilder.build());
//        } else if (body instanceof MultipartBody) {
//            MultipartBody multipartBody = (MultipartBody) body;
//            MultipartBody.Builder newMultipartBodyBuilder = new MultipartBody.Builder();
//            //公共参数设置处
//            newMultipartBodyBuilder.addPart(Headers.of("Content-Disposition: form-data; name=\"token\""), RequestBody.create("123456", null));
//            //或者
//            newMultipartBodyBuilder.addFormDataPart("token", "123456");
//            for (int i = 0; i < multipartBody.size(); i++) {
//                MultipartBody.Part part = multipartBody.part(i);
//                newMultipartBodyBuilder.addPart(part);
//            }
//            newRequestBuilder.post(multipartBody);
//        }
        return chain.proceed(newRequestBuilder.build());
    }
}
