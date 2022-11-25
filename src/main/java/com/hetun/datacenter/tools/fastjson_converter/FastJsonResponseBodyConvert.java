package com.hetun.datacenter.tools.fastjson_converter;

import com.alibaba.fastjson2.JSON;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;
import java.lang.reflect.Type;
final class FastJsonResponseBodyConvert<T> implements Converter<ResponseBody, T> {

    private final Type type;

    public FastJsonResponseBodyConvert(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        return JSON.parseObject(value.string(), type);
    }
}
