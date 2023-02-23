package com.hetun.datacenter.net;

import com.hetun.datacenter.tripartite.bean.BaseNetBean;
import org.springframework.lang.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public abstract class RetryCallback<T extends BaseNetBean> implements Callback<T> {

    private static final String TAG = RetryCallback.class.getSimpleName();

    private final int mRetryCount;
    private final long mRetryInterval;

    private int mCurrentRetryCount;

    private boolean isExecuting;

    private Call<T> mCall;

    private final Timer timer = new Timer();

    public RetryCallback(Call<T> call) {
        isExecuting = true;
        mCall = call;
        mRetryCount = 10;
        mRetryInterval = 10000;
    }

    @Override
    public final void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        isExecuting = false;
        if (mCurrentRetryCount < mRetryCount || Objects.requireNonNull(response.body()).getCode()==1004) {
            mCurrentRetryCount++;
            retryRequest(call);
        } else {
            onRequestResponse(call, response);
        }
    }

    @Override
    public final void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        isExecuting = false;
        if (mCurrentRetryCount < mRetryCount) {
            mCurrentRetryCount++;
            retryRequest(call);
        } else {
            onRequestFail(call, t);
        }
    }

    private void retryRequest(final Call<T> call) {
        onStartRetry();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                synchronized (RetryCallback.this){
                    mCall = call.clone();
                    mCall.enqueue(RetryCallback.this);
                    isExecuting = true;
                }
            }
        };
        timer.schedule(timerTask, mRetryInterval);

    }

    public abstract void onRequestResponse(Call<T> call, Response<T> response);

    public abstract void onRequestFail(Call<T> call, Throwable t);

    public void onStartRetry(){
    }
}
