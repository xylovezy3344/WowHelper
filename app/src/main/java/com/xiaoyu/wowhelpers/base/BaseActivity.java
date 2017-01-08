package com.xiaoyu.wowhelpers.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xiaoyu.wowhelpers.utils.ActivityUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        ActivityUtils.getAppManager().addActivity(this);
    }

    public abstract int getContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.getAppManager().removeActivity(this);
    }
}
