package com.leo.example.ui.activity;

import android.content.Context;
import android.support.v4.view.CustomViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.leo.example.R;
import com.leo.example.callback.DataCallBack;
import com.leo.example.info.SubjectsInfo;
import com.leo.example.ui.adapter.page.GalleryPageAdapter;
import com.leo.example.ui.dialog.LoadingDialog;
import com.leo.example.util.DouBanApiUtil;
import com.leo.example.util.ToastUtil;
import com.leo.example.util.Zoom3DOutPageTransformer;
import com.leolibrary.ui.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 16/5/18.
 */
public class Gallery3DActivity extends BaseActivity {
    private CustomViewPager viewPager;
    private GalleryPageAdapter adapter;
    private ArrayList<SubjectsInfo> list = new ArrayList<>();
    private LinearLayout ll_layout;

    @Override
    public void beforInitView() {
        setContentView(R.layout.activity_3d_gallery);
    }

    @Override
    public void initView() {
        //初始化ViewPager
        ll_layout= (LinearLayout) findViewById(R.id.ll_layout);
        viewPager = (CustomViewPager) findViewById(R.id.view_pager);
        adapter = new GalleryPageAdapter(list, Gallery3DActivity.this, R.layout.item_3d_gallery_view);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageTransformer(true, new Zoom3DOutPageTransformer(viewPager));
    }

    @Override
    public void initData() {
        LoadData(this);
    }

    @Override
    public void initListener() {
        ll_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
    }

    /**
     * 获取豆瓣Api数据
     */
    private void LoadData(final Context context) {
        DouBanApiUtil.LoadRepoData(this, new DataCallBack<List<SubjectsInfo>>() {
            @Override
            public void onSuccess(List<SubjectsInfo> subjectsInfos) {
                list.addAll(subjectsInfos);
                viewPager.setAdapter(adapter);
                LoadingDialog.hideLoadding();
            }

            @Override
            public void onFailure(Throwable t) {
                ToastUtil.showMessage(context, t.getMessage());
            }
        });
    }
}