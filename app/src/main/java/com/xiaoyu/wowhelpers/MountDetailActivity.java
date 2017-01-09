package com.xiaoyu.wowhelpers;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaoyu.wowhelpers.db.Mount;
import com.xiaoyu.wowhelpers.utils.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MountDetailActivity extends AppCompatActivity {

    @BindView(R.id.mount_image)
    ImageView mountImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.activity_mount_detail)
    CoordinatorLayout activityMountDetail;
    private Mount selectedMount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mount_detail);
        ButterKnife.bind(this);

        selectedMount = getIntent().getParcelableExtra("selected_mount");

        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorIcons));
        toolbar.setTitle(selectedMount.getName());

        Glide.with(this).load(ImageUtil.mountIdToResourceId(selectedMount.getId()))
                .into(mountImage);


    }
}
