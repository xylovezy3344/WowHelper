package com.xiaoyu.wowhelpers.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoyu.wowhelpers.MountActivity;
import com.xiaoyu.wowhelpers.MountDetailActivity;
import com.xiaoyu.wowhelpers.MyApplication;
import com.xiaoyu.wowhelpers.R;
import com.xiaoyu.wowhelpers.db.Mount;
import com.xiaoyu.wowhelpers.utils.ImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MountListAdapter extends RecyclerView.Adapter<MountListAdapter.MountViewHolder> {

    private List<Mount> mMountList;
    private Activity mContext;

    public MountListAdapter(Activity context, List<Mount> mountList) {
        mContext = context;
        mMountList = mountList;
    }

    @Override
    public MountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mount_list,
                parent, false);
        MountViewHolder holder = new MountViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MountViewHolder holder, int position) {
        final Mount mount = mMountList.get(position);
        Glide.with(mContext).load(ImageUtil.mountIdToResourceId(mount.getId()))
                .asBitmap().into(holder.mIvMountIcon);
        holder.mTvMountName.setText(mount.getName());
        holder.mTvMountCategory.setText("分类：" + mount.getCategory());

        holder.mRlMountItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                builder.setTitle(mount.getName())
//                        .setMessage(mount.getSource())
//                        .setIcon(getImageResourceId(getImageName(mount.getId())))
//                        .show();
                Intent intent = new Intent(mContext, MountDetailActivity.class);
                intent.putExtra("selected_mount", mount);
                mContext.startActivity(intent);
                MountActivity activity = (MountActivity) mContext;
                activity.restoreMountList();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMountList.size();
    }

    static class MountViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_item_mount)
        RelativeLayout mRlMountItem;
        @BindView(R.id.iv_mount_icon)
        ImageView mIvMountIcon;
        @BindView(R.id.tv_mount_name)
        TextView mTvMountName;
        @BindView(R.id.tv_mount_category)
        TextView mTvMountCategory;

        MountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
