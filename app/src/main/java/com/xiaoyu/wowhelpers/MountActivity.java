package com.xiaoyu.wowhelpers;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyu.wowhelpers.adapter.MountListAdapter;
import com.xiaoyu.wowhelpers.base.BaseActivity;
import com.xiaoyu.wowhelpers.entity.Mount;
import com.xiaoyu.wowhelpers.entity.MountDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xiaoyu.wowhelpers.R.id.loot;

public class MountActivity extends BaseActivity {

    private static final String TAG = "MountActivity";

    @BindView(R.id.rv_mount_list)
    RecyclerView mRvMountList;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_category)
    TextView mTitleCategory;

    private MountDao mMountDao;
    private long mExitTime;
    private PopupWindow mPop;
    private int mScrollX;
    private int mScrollY;
    private MountListAdapter mAdapter;
    private List<Mount> mMountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);

        mMountDao = MyApplication.getInstance().getDaoSession().getMountDao();
        mMountList = mMountDao.queryBuilder().list();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvMountList.setLayoutManager(layoutManager);
        mAdapter = new MountListAdapter(this, mMountList);
        mRvMountList.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_mount;
    }

    @OnClick(R.id.title_category)
    public void onClick() {
        showPopWindow();
    }

    private void showPopWindow() {

        View view = LayoutInflater.from(MountActivity.this).inflate(R.layout.title_category, null);
        view.measure(0, 0);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）

        mPop = new PopupWindow(view, view.getMeasuredWidth(), view.getMeasuredHeight() / 3, true);
        mPop.setContentView(view);
        mPop.setOutsideTouchable(true);
        mPop.setFocusable(true);
        //让pop可以点击外面消失掉
        mPop.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        mPop.showAsDropDown(mToolbar, (width - mPop.getWidth()) / 2, 0);

        final ViewHolder holder = new ViewHolder(view);
        setOnPopupWindowClick(holder);
        holder.mScrollView.post(new Runnable() {
            @Override
            public void run() {
                holder.mScrollView.scrollTo(mScrollX, mScrollY);
            }
        });
    }

    private void setOnPopupWindowClick(final ViewHolder holder) {

        View.OnClickListener titleCategoryLisener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textView = (TextView) v;
                String title = textView.getText().toString();

                mTitleCategory.setText(title);

                mScrollX = holder.mScrollView.getScrollX();
                mScrollY = holder.mScrollView.getScrollY();

                updateRecyclerView(title);

                mPop.dismiss();

            }
        };

        holder.mAll.setOnClickListener(titleCategoryLisener);
        holder.mLoot.setOnClickListener(titleCategoryLisener);
        holder.mPvp.setOnClickListener(titleCategoryLisener);
        holder.mSell.setOnClickListener(titleCategoryLisener);
        holder.mPrestige.setOnClickListener(titleCategoryLisener);
        holder.mTask.setOnClickListener(titleCategoryLisener);
        holder.mAchievement.setOnClickListener(titleCategoryLisener);
        holder.mWorldEvent.setOnClickListener(titleCategoryLisener);
        holder.mSpecialty.setOnClickListener(titleCategoryLisener);
        holder.mUnion.setOnClickListener(titleCategoryLisener);
        holder.mRace.setOnClickListener(titleCategoryLisener);
        holder.mProfession.setOnClickListener(titleCategoryLisener);
        holder.mFortress.setOnClickListener(titleCategoryLisener);
        holder.mOther.setOnClickListener(titleCategoryLisener);
        holder.mBlackMarket.setOnClickListener(titleCategoryLisener);
        holder.mOutOfPrint.setOnClickListener(titleCategoryLisener);
        holder.mOutside.setOnClickListener(titleCategoryLisener);
    }

    private void updateRecyclerView(String title) {
        mMountList.clear();
        List<Mount> newMountList;
        if (title.equals("全部")) {
            newMountList = mMountDao.queryBuilder().list();
        } else {
            newMountList = mMountDao.queryBuilder().where(MountDao.Properties.Category
                    .eq(title)).list();
        }

        for (Mount newMount : newMountList) {
            mMountList.add(newMount);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MountActivity.this, "再点一次，退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    static class ViewHolder {
        @BindView(R.id.popup_window)
        ScrollView mScrollView;
        @BindView(R.id.all)
        TextView mAll;
        @BindView(loot)
        TextView mLoot;
        @BindView(R.id.pvp)
        TextView mPvp;
        @BindView(R.id.sell)
        TextView mSell;
        @BindView(R.id.prestige)
        TextView mPrestige;
        @BindView(R.id.task)
        TextView mTask;
        @BindView(R.id.achievement)
        TextView mAchievement;
        @BindView(R.id.world_event)
        TextView mWorldEvent;
        @BindView(R.id.specialty)
        TextView mSpecialty;
        @BindView(R.id.union)
        TextView mUnion;
        @BindView(R.id.race)
        TextView mRace;
        @BindView(R.id.profession)
        TextView mProfession;
        @BindView(R.id.fortress)
        TextView mFortress;
        @BindView(R.id.other)
        TextView mOther;
        @BindView(R.id.black_market)
        TextView mBlackMarket;
        @BindView(R.id.out_of_print)
        TextView mOutOfPrint;
        @BindView(R.id.outside)
        TextView mOutside;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
