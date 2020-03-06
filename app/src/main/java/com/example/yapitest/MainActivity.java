package com.example.yapitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yapitest.adapter.RecyclerAdapter;
import com.example.yapitest.bean.Course;
import com.example.yapitest.bean.CourseModel;
import com.example.yapitest.bean.PostBean;
import com.example.yapitest.listeners.DisposeDataHandle;
import com.example.yapitest.listeners.DisposeDataListener;
import com.example.yapitest.network.RequestCenter;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    Button mButtonNormal;
    Button mButtonSpecial;
    Button mButtonPost;
    RecyclerAdapter adapter;
    CourseModel data;
    private static final String API = "http://yapi.demo.qunar.com/mock/85192/super-api/test/homepage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButtonNormal = findViewById(R.id.button_normal);
        mButtonSpecial = findViewById(R.id.button_special);
        mButtonPost = findViewById(R.id.button_post);
        String jsonbody = "{\"type\":1}";
        mButtonPost.setOnClickListener(v->{requestPost(API , jsonbody);});
        mButtonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGet(API);
            }
        });
        mButtonSpecial.setOnClickListener(v -> requestGet(API+"?special=1"));
        mRecyclerView = findViewById(R.id.recycler_view);
        requestGet(API);
    }

    private void requestGet(String url){
        RequestCenter.getInstance(getApplicationContext(), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                data = (CourseModel) responseObj;
                adapter = new RecyclerAdapter(data.courses);
                mRecyclerView.setAdapter(adapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        })).getJson(url);
    }

    private void requestPost(String url , String body){
        RequestCenter.getInstance(getApplicationContext(), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                data = (CourseModel) responseObj;
                adapter = new RecyclerAdapter(data.courses);
                mRecyclerView.setAdapter(adapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        })).postJson(url,body);    }
}
