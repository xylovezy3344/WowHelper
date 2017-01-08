package com.xiaoyu.wowhelpers.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoyu.wowhelpers.MyApplication;
import com.xiaoyu.wowhelpers.R;
import com.xiaoyu.wowhelpers.entity.Mount;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MountListAdapter extends RecyclerView.Adapter<MountListAdapter.MountViewHolder> {

    private List<Mount> mMountList;
    private Context mContext;

    public MountListAdapter(Context context, List<Mount> mountList) {
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
        holder.mIvMountIcon.setImageResource(getImageResourceId(getImageName(mount.getId())));
        holder.mTvMountName.setText(mount.getName());
        holder.mTvMountCategory.setText("分类：" + mount.getCategory());

        holder.mRlMountItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mount.getName())
                        .setMessage(mount.getSource())
                        .setIcon(getImageResourceId(getImageName(mount.getId())))
                        .show();
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

    /**
     * 根据文件名获取资源ID
     */
    private int getImageResourceId(String resourceName) {

        Context context = MyApplication.getInstance().getApplicationContext();
        int resId = context.getResources().getIdentifier(resourceName, "drawable",
                "com.xiaoyu.wowhelpers");
        return resId;
    }

    /**
     * 根据id组装成文件名
     */
    private String getImageName(long id) {

        String imageName = null;

        if (id > 99) {
            imageName = "mount_" + id;
        } else if (id > 9) {
            imageName = "mount_0" + id;
        } else {
            imageName = "mount_00" + id;
        }

        return imageName;
    }
}
