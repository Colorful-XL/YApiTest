package com.example.yapitest.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yapitest.R;
import com.example.yapitest.bean.Course;

import java.util.List;

public class RecyclerAdapter extends BaseQuickAdapter<Course, BaseViewHolder> {


    public RecyclerAdapter(List<Course> data) {
        super(R.layout.item_layout , data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Course item) {
        helper.setText(R.id.text_name , "课程名称："+item.getCourseName());
        helper.setText(R.id.text_price , "课程价格："+item.getCoursePrice());
        helper.setText(R.id.text_date ,"发布日期："+item.getDate());
        helper.setText(R.id.text_id , "课程ID："+item.getCourseId());
        ImageView imageView = helper.getView(R.id.image_view);
        RequestOptions options = new RequestOptions();
        options = options.override(300 , 300);
        Glide.with(mContext).load(item.getCourseImage()).apply(options).into(imageView);
    }
}
