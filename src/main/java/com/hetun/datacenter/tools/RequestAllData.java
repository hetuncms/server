package com.hetun.datacenter.tools;

import com.hetun.datacenter.tripartite.bean.BaseNetBean;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class RequestAllData<T extends BaseNetBean> {
    CallBack<T> callBack;
    public void getAll(CallBack<T> callBack){

        int startId = 0;
        while (true) {

            Call<T> call = callBack.requestNet(startId);
            Response<T> execute = null;
            try {
                execute = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            T body = execute.body();
            if (body == null) {
                break;
            }

            callBack.handler(body);
            List<BaseNetBean.GetIdabble> result = body.getResult();
            startId = result.get(result.size()-1).getId();
        }
    }

    interface CallBack<T>{
        void handler(BaseNetBean baseNetBean);
        Call<T> requestNet(int startId);
    }
}
