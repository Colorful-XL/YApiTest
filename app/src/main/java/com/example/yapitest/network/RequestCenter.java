package com.example.yapitest.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.yapitest.bean.Course;
import com.example.yapitest.bean.CourseModel;
import com.example.yapitest.listeners.DisposeDataHandle;
import com.example.yapitest.listeners.DisposeDataListener;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestCenter {

    private Context mContext;
    private DisposeDataListener mListener;
    private Handler mHandler;

    private static volatile RequestCenter mInstance = null;

    private RequestCenter(Context mContext , DisposeDataHandle handle){
        this.mContext = mContext;
        this.mListener = handle.mListener;
        mHandler = new Handler(Looper.getMainLooper());
    }
    public static RequestCenter getInstance(Context mContext , DisposeDataHandle handle){
        if (mInstance == null){
            synchronized (RequestCenter.class){
                if (mInstance == null){
                    return new RequestCenter(mContext , handle);
                }
            }
        }
        return mInstance;
    }

    public void postJson(String url , String body){
        OkHttpClient okHttpClient  = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody JsonBody = RequestBody.create(JSON , body);
        Request request = new Request.Builder()
                .url(url)
                .post(JsonBody)
                .build();
       Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure( Call call,  IOException e) {
                mHandler.post(() -> {
                    mListener.onFailure(e);
                    Toast.makeText(mContext , "网络好像不太好^^",Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse( Call call,  Response response) throws IOException {
                String data = Objects.requireNonNull(response.body()).string();
                mHandler.post(() -> handleResponse(data));
            }
        });

    }

    public void getJson(String url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure( Call call,  IOException e) {
                mHandler.post(() -> {
                    mListener.onFailure(e);
                    Toast.makeText(mContext , "网络好像不太好^^",Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse( Call call,  Response response) throws IOException {
                String data = Objects.requireNonNull(response.body()).string();
                mHandler.post(() -> handleResponse(data));
            }
        });
    }

    private void handleResponse(Object response){
        if (response == null || response.toString().trim().equals("")){
            return;
        }
        CourseModel data = new Gson().fromJson((String) response, CourseModel.class);
        mListener.onSuccess(data);
    }

}
