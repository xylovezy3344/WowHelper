package com.xiaoyu.wowhelpers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.xiaoyu.wowhelpers.adapter.MountListAdapter;
import com.xiaoyu.wowhelpers.base.BaseActivity;
import com.xiaoyu.wowhelpers.db.Mount;
import com.xiaoyu.wowhelpers.db.MountDao;

import java.util.List;

import butterknife.BindView;

public class MountActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MountActivity";

    @BindView(R.id.rv_mount_list)
    RecyclerView mRvMountList;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.search_mount)
    SearchView searchMount;

    private MountDao mMountDao;
    private long mExitTime;
    private MountListAdapter mAdapter;
    private List<Mount> mMountList;
    private String currentCategory = "全部";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mMountDao = MyApplication.getInstance().getDaoSession().getMountDao();
        mMountList = mMountDao.queryBuilder().list();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvMountList.setLayoutManager(layoutManager);
        mAdapter = new MountListAdapter(this, mMountList);
        mRvMountList.setAdapter(mAdapter);

        searchMount.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMountWithName(newText);
                return true;
            }
        });
    }

    private void searchMountWithName(String mountName) {
        if (!"".equals(mountName)) {
            List<Mount> mounts = mMountDao.queryBuilder().where(
                    MountDao.Properties.Name.like("%" + mountName + "%")).list();
            mMountList.clear();
            for (Mount mount : mounts) {
                mMountList.add(mount);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        String title = item.getTitle().toString();

        mToolbar.setTitle(title);

        updateRecyclerView(title);

        drawerLayout.closeDrawer(GravityCompat.START);

        currentCategory = title;

        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_mount;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.query_mount:
                mToolbar.setVisibility(View.INVISIBLE);
                searchMount.setVisibility(View.VISIBLE);
                searchMount.setQuery("", false);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                mMountList.clear();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.setting:
                break;
        }
        return true;
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

    public void restoreMountList() {
        searchMount.setVisibility(View.INVISIBLE);
        mToolbar.setVisibility(View.VISIBLE);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        updateRecyclerView(currentCategory);
    }

    @Override
    public void onBackPressed() {

        if (searchMount.getVisibility() == View.VISIBLE) {
            restoreMountList();
        } else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MountActivity.this, "再点一次，退出", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }
}
